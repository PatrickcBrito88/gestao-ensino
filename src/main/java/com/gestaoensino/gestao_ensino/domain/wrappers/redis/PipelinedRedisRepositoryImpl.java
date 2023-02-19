package com.gestaoensino.gestao_ensino.domain.wrappers.redis;

import com.gestaoensino.gestao_ensino.api.exceptions.PipelineException;
import com.gestaoensino.gestao_ensino.domain.wrappers.utils.SimpleByteArrayRedisSerializer;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.repository.support.SimpleKeyValueRepository;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PipelinedRedisRepositoryImpl<T> extends SimpleKeyValueRepository<T, String> implements PipelinedRedisRepository<T> {

    private static final long TTL_DEF = -1L;

    private final RedisTemplate<String, T> redisTemplate;
    private final PipelinedHashMapper hashMapper;
    private final Class<T> clazz;

    private final List<Pair<Field, Method>> indexedFieldPairs = new ArrayList<>();
    private Pair<Method, TimeUnit> ttlGetterPair;
    private long globalTimeToLive = TTL_DEF;
    private Method idSetterMethod;
    private Method idGetterMethod;
    private String keyPrefix;

    /**
     * Choose chunkSize carefully depending on the Size of the data and the number of Indexes
     */
    public PipelinedRedisRepositoryImpl(EntityInformation<T, String> entityInformation,
                                        KeyValueOperations operations,
                                        RedisTemplate<String, T> redisTemplate,
                                        KeyValueAdapter adapter) {
        super(entityInformation, operations);
        Assert.notNull(entityInformation, "Entity Information can not be null");
        Assert.notNull(redisTemplate, "Redis Template can not be null");
        Assert.notNull(adapter, "KeyValueAdapter can not be null");
        this.redisTemplate = redisTemplate;
        this.clazz = entityInformation.getJavaType();
        this.hashMapper = new PipelinedHashMapper(((RedisKeyValueAdapter) adapter).getConverter());
        configureSerializersAndMappers();
        initializeTargetClassMetadata();
    }

    @Override
    public List<T> bulkFindAll() {
        Collection<String> allHashIds = bulkFindAllIds();
        return doBulkFindAll(allHashIds);
    }

    @Override
    public List<T> bulkFindAll(Map<String, Object> indexMap) {
        Set<String> allKeys = new HashSet<>();
        indexMap.forEach((k, v) -> {
            Collection<String> keys = doBulkFindAllIds(String.join(":", keyPrefix, k, v.toString()));
            if (allKeys.isEmpty()) allKeys.addAll(keys);
            else allKeys.retainAll(keys);
        });
        return doBulkFindAll(allKeys);
    }

    private List<T> doBulkFindAll(Collection<String> allHashIds) {
        // Executes the operation Partitioning
        List<T> objects = new ArrayList<>();
        execPartitioning(allHashIds, this::doBulkFindAllPerPartition, objects::addAll);

        return objects.parallelStream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<T> doBulkFindAllPerPartition(Iterable<String> hashIds) {
        // Find  all Hashes with the keys
        List<?> resultsPipelineList = redisTemplate.executePipelined((RedisCallback<T>) connection -> {
            connection.multi();
            hashIds.forEach(hashId -> connection.hGetAll((keyPrefix + ":" + hashId).getBytes()));
            connection.exec();
            return null;
        });
        return ((List<?>) resultsPipelineList.get(0)).parallelStream()
                .map(object -> hashMapper.fromHash((Map<byte[], byte[]>) object, clazz))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> bulkFindAllKeys() {
        return doBulkFindAllKeys(keyPrefix);
    }

    @SuppressWarnings("unchecked")
    private List<String> doBulkFindAllKeys(String key) {
        List<?> keysPipelineList = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            connection.multi();
            connection.keys(key.getBytes());
            connection.keys((key + ":*").getBytes());
            connection.exec();
            return null;
        });
        return ((List<Set<String>>) keysPipelineList.get(0)).parallelStream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> bulkFindAllIds() {
        return doBulkFindAllIds(keyPrefix);
    }

    @SuppressWarnings("unchecked")
    private Collection<String> doBulkFindAllIds(String setKey) {
        final List<?> keysPipelineList = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            connection.multi();
            connection.sMembers(setKey.getBytes());
            connection.exec();
            return null;
        });
        return (Collection<String>) ((List<?>) keysPipelineList.get(0)).get(0);
    }

    @Override
    public Long bulkDeleteAll() {
        return doBulkDeleteAll(keyPrefix);
    }

    @Override
    public Long bulkDeleteAll(String keyPrefix) {
        return doBulkDeleteAll(keyPrefix);
    }

    private Long doBulkDeleteAll(String keyPrefix) {
        List<byte[]> keys = doBulkFindAllKeys(keyPrefix)
                .parallelStream()
                .map(String::getBytes)
                .collect(Collectors.toList());

        // Executes the operation Partitioning
        AtomicLong keysDeleted = new AtomicLong(0L);
        execPartitioning(keys, this::doBulkDeleteAllWithKeyPrefix, keysDeleted::addAndGet);

        return keysDeleted.get();
    }

    private Long doBulkDeleteAllWithKeyPrefix(Collection<byte[]> keysAndIndexes) {
        List<?> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.multi();
            connection.del(keysAndIndexes.toArray(new byte[keysAndIndexes.size()][]));
            connection.exec();
            return null;
        });
        return processObjectResponse(objects);
    }

    @Override
    public void deleteDataAndPhantomById(String id) {
        redisTemplate.delete(keyPrefix + ":" + id + ":phantom");
        deleteById(id);
    }

    @Override
    public Long bulkRenameTargetClassKeys(String newKeyPrefix, boolean ignoreSetValuesRename) {
        return generateNewKeysNameAndRename(keyPrefix, newKeyPrefix, ignoreSetValuesRename);
    }

    @Override
    public Long bulkRestoreTargetClassKeys(String fromKeyPrefix, boolean ignoreSetValuesRename) {
        return generateNewKeysNameAndRename(fromKeyPrefix, keyPrefix, ignoreSetValuesRename);
    }

    @Override
    public Long bulkRenameKeys(String currentPrefix, String newKeyPrefix, boolean ignoreSetValuesRename) {
        return generateNewKeysNameAndRename(currentPrefix, newKeyPrefix, ignoreSetValuesRename);
    }

    private Long generateNewKeysNameAndRename(String currentPrefix, String newKeyPrefix, boolean ignoreSetValuesRename) {
        List<Pair<String, String>> oldNewKeysPair = doBulkFindAllKeys(currentPrefix).parallelStream()
                .map(oldKey -> Pair.create(oldKey, oldKey.replace(currentPrefix, newKeyPrefix)))
                .collect(Collectors.toList());

        // Executes the operation Partitioning
        List<Object> operationsResponse = new ArrayList<>();
        execPartitioning(oldNewKeysPair,
                collection -> this.doBulkRenameKeys(collection, currentPrefix, newKeyPrefix, ignoreSetValuesRename),
                operationsResponse::addAll);

        return processObjectResponse(operationsResponse);
    }

    @Override
    public Long bulkRenameIndexSetValues(String setKeyPrefix, String currentPrefix, String newKeyPrefix) {
        List<String> indexKeys = doBulkFindAllKeys(setKeyPrefix).parallelStream()
                .filter(isIndexKey())
                .collect(Collectors.toList());

        // Executes the operation Partitioning
        List<Object> operationsResponse = new ArrayList<>();
        execPartitioning(indexKeys,
                collection -> this.doBulkRenameSets(collection, currentPrefix, newKeyPrefix),
                operationsResponse::addAll);

        return operationsResponse.size() / 2L;
    }

    private List<?> doBulkRenameSets(Collection<String> setKeys,
                                     String currentPrefix,
                                     String newKeyPrefix) {
        return redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                    setKeys.forEach(
                            setKey -> {
                                Collection<String> keysInSet = doBulkFindAllIds(setKey);
                                Collection<byte[]> keysToAdd = keysInSet.stream()
                                        .map(key -> key.replace(currentPrefix, newKeyPrefix).getBytes())
                                        .collect(Collectors.toSet());
                                if (!keysToAdd.isEmpty()) {
                                    connection.del(setKey.getBytes());
                                    connection.sAdd(setKey.getBytes(), keysToAdd.toArray(new byte[keysInSet.size()][]));
                                }
                            }
                    );
                    return null;
                }
        );
    }

    private List<?> doBulkRenameKeys(Collection<Pair<String, String>> keysPair,
                                     String currentPrefix,
                                     String newKeyPrefix,
                                     boolean ignoreSetValuesRename) {
        List<String> setKeys = new ArrayList<>();
        List<?> objectsResponse = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.multi();
            for (Pair<String, String> keys : keysPair) {
                String oldKey = keys.getFirst();
                String newKey = keys.getSecond();
                if (!ignoreSetValuesRename && isIndexKey().test(newKey)) setKeys.add(newKey);
                connection.renameNX(oldKey.getBytes(), newKey.getBytes());
            }
            connection.exec();
            return null;
        });

        // Renames the Sets
        if (!ignoreSetValuesRename && !setKeys.isEmpty()) doBulkRenameSets(setKeys, currentPrefix, newKeyPrefix);

        return objectsResponse;
    }

    @Override
    public <S extends T> Long bulkSaveAll(Iterable<S> objectList) {
        return doBulkSave(objectList, keyPrefix);
    }

    @Override
    public <S extends T> Long bulkSaveAll(Iterable<S> objectList, String newKeyPrefix) {
        return doBulkSave(objectList, newKeyPrefix);
    }

    private <S extends T> Long doBulkSave(Iterable<S> objectList, String newKeyPrefix) {
        Long keysInserted = 0L;
        Iterable<List<S>> objetsToSavePartitions = Iterables.partition(objectList, CHUNK_SIZE);
        for (List<S> objList : objetsToSavePartitions) {
            keysInserted += doBulkSaveWithKeyPrefix(objList, newKeyPrefix);
        }
        return keysInserted / ((2L * indexedFieldPairs.size()) + 1);
    }

    private <S extends T> Long doBulkSaveWithKeyPrefix(Iterable<S> objectList, String keyPrefix) {
        final AtomicLong atomicCounter = new AtomicLong();
        Iterator<S> iterator = objectList.iterator();
        String prefixWithDots = keyPrefix + ":";

        List<?> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.multi();
            while (iterator.hasNext()) {
                try {
                    Object data = iterator.next();
                    String idStr = genBulkSaveId(data, atomicCounter);

                    // Add Main Data with Hash
                    String hashMapKey = prefixWithDots + idStr;
                    connection.hMSet(hashMapKey.getBytes(), hashMapper.toHash(data));

                    // Add Key to the ID with Set
                    connection.sAdd(keyPrefix.getBytes(), idStr.getBytes());

                    // Gets the Expiration Time
                    long timeToLive = genBulkSaveTtl(data);
                    // Expire the Main Keys if TTL is Set
                    if (timeToLive != TTL_DEF) {
                        connection.expire(hashMapKey.getBytes(), timeToLive);
                        connection.expire(keyPrefix.getBytes(), timeToLive);
                    }

                    // Add Index Keys with Set
                    for (Pair<Field, Method> fieldPair : indexedFieldPairs) {
                        Pair<String, String> idxPair = genBulkSaveIndexFieldKeyValPair(data,
                                idStr,
                                prefixWithDots,
                                fieldPair);
                        connection.sAdd(idxPair.getFirst().getBytes(), idxPair.getSecond().getBytes());
                        connection.sAdd(idxPair.getSecond().getBytes(), idStr.getBytes());

                        // Expire the Index Keys if TTL is Set
                        if (timeToLive != TTL_DEF) {
                            connection.expire(idxPair.getFirst().getBytes(), timeToLive);
                            connection.expire(idxPair.getSecond().getBytes(), timeToLive);
                        }
                    }
                } catch (Exception e) {
                    throw new PipelineException("An error ocurred while processing the pipeline. Cause: " + e.getLocalizedMessage(), e);
                }
            }
            connection.exec();
            return null;
        });
        return processObjectResponse(objects);
    }

    private String genBulkSaveId(Object data, AtomicLong atomicCounter) throws InvocationTargetException, IllegalAccessException {
        Object id = idGetterMethod.invoke(data);
        String idStr;
        // If ID is null creates a random one and sets
        if (id == null) {
            idStr = genSafeId(atomicCounter);
            idSetterMethod.invoke(data, idStr);
        } else {
            idStr = id.toString();
        }
        return idStr;
    }

    private Pair<String, String> genBulkSaveIndexFieldKeyValPair(Object data,
                                                                 String idStr,
                                                                 String prefixWithDots,
                                                                 Pair<Field, Method> fieldPair)
            throws InvocationTargetException, IllegalAccessException {
        Field indexField = fieldPair.getFirst();
        Method indexGetterMethod = fieldPair.getSecond();
        String setIdxKey = prefixWithDots + idStr + ":idx";
        String setIdxValue = prefixWithDots + (indexField.getName()) + ":" + (indexGetterMethod.invoke(data));
        return Pair.create(setIdxKey, setIdxValue);
    }

    private long genBulkSaveTtl(Object data) throws InvocationTargetException, IllegalAccessException {
        if (ttlGetterPair != null) {
            Object ttl = ttlGetterPair.getFirst().invoke(data);
            if (ttl != null) {
                long longTtl = Long.parseLong(ttl.toString());
                if (longTtl > 0) return longTtl;
            }
        }
        return globalTimeToLive;
    }

    @Override
    public Long bulkExpireKeys(Duration expireTime) {
        return bulkExpireKeysWithPrefix(keyPrefix, expireTime);
    }

    @Override
    public Long bulkExpireKeysWithPrefix(String keyPrefix, Duration expireTime) {
        List<byte[]> keys = doBulkFindAllKeys(keyPrefix).parallelStream()
                .map(String::getBytes)
                .collect(Collectors.toList());

        // Executes the operation Partitioning
        AtomicLong keysDeleted = new AtomicLong(0L);
        execPartitioning(keys,
                collection -> this.doBulkExpireKeysWithPrefix(collection, expireTime.getSeconds()),
                keysDeleted::addAndGet);

        return keysDeleted.get();
    }

    private Long doBulkExpireKeysWithPrefix(Collection<byte[]> keysToExpire, long seconds) {
        List<?> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.multi();
            keysToExpire.forEach(key -> connection.expire(key, seconds));
            connection.exec();
            return null;
        });
        if (!objects.isEmpty())
            return ((List<?>) objects.get(0)).parallelStream()
                    .filter(Boolean.class::cast)
                    .count();
        else return 0L;
    }

    @Override
    public String getTargetClassKeyPrefix() {
        return keyPrefix;
    }

    /*  ----------- INITIALIZATION ----------- */
    private static final String GETTER = "Getter";
    private static final String GETTER_AND_SETTER = "Getter and Setter";

    private void initializeTargetClassMetadata() {
        String msgPrefix = "The target class [";

        // Trata a anotação Redis Hash
        handleRedisHashAnnotation(msgPrefix);

        // Inicializa campos da classe base
        initializeTargetClassFields(clazz.getDeclaredFields());
        if (!clazz.getSuperclass().isAssignableFrom(Object.class)) {
            // Inicializa campos da super classe
            initializeTargetClassFields(clazz.getSuperclass().getDeclaredFields());
        }

        // Valida se existe campo com anotação @ID
        Assert.notNull(idSetterMethod, msgPrefix + clazz.getName() + "] must have one field annotated with @ID");
        Assert.notNull(idGetterMethod, msgPrefix + clazz.getName() + "] must have one field annotated with @ID");
    }

    private void handleRedisHashAnnotation(String msgPrefix) {
        RedisHash redisHashAnnotation = clazz.getDeclaredAnnotation(RedisHash.class);
        Assert.notNull(redisHashAnnotation, msgPrefix + clazz.getName() + "] must be annotated with RedisHash");
        keyPrefix = redisHashAnnotation.value();
        globalTimeToLive = redisHashAnnotation.timeToLive();
    }

    private void handleIdAnnotatedFields(@NonNull Field field) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());
        if (propertyDescriptor == null) {
            throw throwMissingAccessors("@ID", GETTER_AND_SETTER);
        }

        if (!ClassUtils.isAssignable(field.getType(), String.class, true)) {
            throw new PipelineException("The field annotated with @ID in the target class [" + clazz.getName() + "] must be of type [" + String.class.getName() + "]");
        }

        idSetterMethod = propertyDescriptor.getWriteMethod();
        idGetterMethod = propertyDescriptor.getReadMethod();

        if (idSetterMethod == null || idGetterMethod == null) {
            throw throwMissingAccessors("@ID", GETTER_AND_SETTER);
        }
    }

    private void handleIndexedAnnotatedField(@NonNull Field field) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());
        if (propertyDescriptor == null) {
            throw throwMissingAccessors("@Indexed", GETTER);
        }

        Method indexGetterMethod = propertyDescriptor.getReadMethod();
        if (indexGetterMethod == null) {
            throw throwMissingAccessors("@Indexed", GETTER);
        }
        indexedFieldPairs.add(Pair.create(field, indexGetterMethod));
    }

    private void handleTimeToliveAnnotatedField(@NonNull Field field, TimeUnit timeUnit) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, field.getName());

        if (ttlGetterPair != null) {
            throw new PipelineException("Can only be one field annotated with @TimeToLive in the target class [" + clazz.getName() + "]");
        }

        if (!ClassUtils.isAssignable(field.getType(), Number.class, true)) {
            throw new PipelineException("The field annotated with @TimeToLive in the target class [" + clazz.getName() + "] must be of type [" + Number.class.getName() + "] or a number primitive.");
        }

        if (propertyDescriptor == null) {
            throw throwMissingAccessors("@TimeToLive", GETTER);
        }

        Method timeToLiveGetterMethod = propertyDescriptor.getReadMethod();
        if (timeToLiveGetterMethod == null) {
            throw throwMissingAccessors("@TimeToLive", GETTER);
        }
        ttlGetterPair = Pair.create(timeToLiveGetterMethod, timeUnit);
    }

    private void initializeTargetClassFields(Field[] declaredFields) {
        for (Field field : declaredFields) {

            Id idAnnotation = field.getDeclaredAnnotation(Id.class);
            if (idAnnotation != null) {
                if (idSetterMethod != null || idGetterMethod != null) {
                    throw new PipelineException("Only one field can be annotated with @ID in the target class [" + clazz.getName() + "]");
                }
                handleIdAnnotatedFields(field);
            }

            Indexed indexedAnnotation = field.getDeclaredAnnotation(Indexed.class);
            if (indexedAnnotation != null) {
                handleIndexedAnnotatedField(field);
            }

            TimeToLive timeToLiveAnnotation = field.getDeclaredAnnotation(TimeToLive.class);
            if (timeToLiveAnnotation != null) {
                handleTimeToliveAnnotatedField(field, timeToLiveAnnotation.unit());
            }
        }
    }

    private void configureSerializersAndMappers() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new SimpleByteArrayRedisSerializer());
        redisTemplate.setHashValueSerializer(new SimpleByteArrayRedisSerializer());
    }

    /* ------------------ */
    // Executes the operation partitioning if the size is greater
    private <S, R> void execPartitioning(Collection<S> collection,
                                         Function<Collection<S>, R> action,
                                         Consumer<R> resCons) {
        if (collection.isEmpty()) return;
        if (collection.size() > CHUNK_SIZE) {
            final Iterable<List<S>> collectionPartitioned = Iterables.partition(collection, CHUNK_SIZE);
            for (List<S> partition : collectionPartitioned) {
                resCons.accept(action.apply(partition));
            }
        } else {
            resCons.accept(action.apply(collection));
        }
    }

    private String genSafeId(AtomicLong atomicCounter) {
        return atomicCounter.getAndIncrement() + "-" + UUID.randomUUID();
    }

    private Predicate<String> isIndexKey() {
        return key -> key.endsWith("idx");
    }

    private Long mapToLongOrZero(Object obj) {
        if (ClassUtils.isAssignable(obj.getClass(), Number.class, true))
            return Long.parseLong(obj.toString());
        return 0L;
    }

    private Long processObjectResponse(List<?> objects) {
        if (!objects.isEmpty()) {
            Object firstObj = objects.get(0);
            if (Collection.class.isAssignableFrom(firstObj.getClass())) {
                return ((Collection<?>) firstObj).parallelStream()
                        .filter(Objects::nonNull)
                        .map(this::mapToLongOrZero)
                        .reduce(0L, Long::sum);
            }
        }
        return 0L;
    }

    private PipelineException throwMissingAccessors(String annotation, String accessorsMissing) {
        return new PipelineException(
                String.format("The field annotated with %s in the target class [%s] must have a %s method",
                        annotation, clazz.getName(), accessorsMissing)
        );
    }
}
