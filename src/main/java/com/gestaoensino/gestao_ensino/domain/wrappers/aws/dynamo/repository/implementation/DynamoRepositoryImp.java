package com.gestaoensino.gestao_ensino.domain.wrappers.aws.dynamo.repository.implementation;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.gestaoensino.gestao_ensino.api.exceptions.BusinessException;
import com.gestaoensino.gestao_ensino.api.exceptions.DynamoException;
import com.gestaoensino.gestao_ensino.domain.wrappers.aws.dynamo.repository.DynamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DynamoRepositoryImp<T> implements DynamoRepository<T> {

    @Autowired
    protected DynamoDBMapper dynamoDBMapper;

    protected final Class<T> typeParameterClass;
    protected String hashKeyFieldName;

    public DynamoRepositoryImp() {
        this.typeParameterClass = returnedClass();

        Field[] fields = typeParameterClass.getDeclaredFields();
        searchAndSetHashKeyFieldName(fields);
        if (!typeParameterClass.getSuperclass().isAssignableFrom(Object.class) && hashKeyFieldName == null) {
            searchAndSetHashKeyFieldName(typeParameterClass.getSuperclass().getDeclaredFields());
        }
        if (hashKeyFieldName == null) {
            throw new IllegalArgumentException(
                    "Não foi encontrado nenhum campo com a anotação @DynamoDBHashKey na entidade "
                            + typeParameterClass.getName() + ".");
        }
    }

    private void searchAndSetHashKeyFieldName(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(DynamoDBHashKey.class)) {
                DynamoDBHashKey hashKeyAnnotation = field.getAnnotation(DynamoDBHashKey.class);
                if (!hashKeyAnnotation.attributeName().equals("")) {
                    this.hashKeyFieldName = hashKeyAnnotation.attributeName();
                } else {
                    this.hashKeyFieldName = field.getName();
                }
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<T> returnedClass() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public String getHashKeyFieldName() {
        return hashKeyFieldName;
    }

    @Override
    public List<T> findAllByHashKey(Object hash) {
        Map<String, AttributeValue> eav = new HashMap<>();
        if (hash instanceof String) {
            eav.put(":v1", new AttributeValue().withS(String.valueOf(hash)));
        } else if (hash instanceof Integer) {
            eav.put(":v1", new AttributeValue().withN(String.valueOf(hash)));
        } else {
            throw new BusinessException("Argumento 'hash' só é permitido String ou Integer.");
        }
        String expression = hashKeyFieldName + " = :v1";
        return dynamoDBMapper.query(typeParameterClass, buildQueryExpression(expression, eav));
    }

    @Override
    public List<T> queryPage(Object hashKeyValue, DynamoDBQueryExpression<T> dynamoDBQueryExpression) {
        return dynamoDBMapper.queryPage(typeParameterClass, queryGenerics(hashKeyValue, dynamoDBQueryExpression)).getResults();
    }

    @Override
    public List<T> query(Object hashKeyValue, DynamoDBQueryExpression<T> dynamoDBQueryExpression) {
        return dynamoDBMapper.query(typeParameterClass, queryGenerics(hashKeyValue, dynamoDBQueryExpression));
    }

    private DynamoDBQueryExpression<T> queryGenerics(Object hashKeyValue, DynamoDBQueryExpression<T> dynamoDBQueryExpression) {
        Map<String, AttributeValue> eav = new HashMap<>();

        if (hashKeyValue instanceof String) {
            eav.put(":hashKey", new AttributeValue().withS(String.valueOf(hashKeyValue)));
        } else if (hashKeyValue instanceof Integer) {
            eav.put(":hashKey", new AttributeValue().withN(String.valueOf(hashKeyValue)));
        } else {
            throw new BusinessException("Argumento 'hash' só é permitido String ou Integer.");
        }

        String expression = hashKeyFieldName + " = :hashKey";
        dynamoDBQueryExpression
                .withKeyConditionExpression(expression)
                .withExpressionAttributeValues(eav);
        return dynamoDBQueryExpression;
    }

    @Override
    public T save(T entity) {
        dynamoDBMapper.save(entity);
        return entity;
    }

    @Override
    public void saveAll(Iterable<T> entities) {
        dynamoDBMapper.batchSave(entities);
    }

    @Override
    public void delete(T entity) {
        dynamoDBMapper.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<T> entities) {
        dynamoDBMapper.batchDelete(entities);
    }

    protected DynamoDBQueryExpression<T> buildQueryExpression(String expression,
                                                              Map<String, AttributeValue> stringAttributeValueMap) {
        return new DynamoDBQueryExpression<T>().withKeyConditionExpression(expression)
                .withExpressionAttributeValues(stringAttributeValueMap);
    }

    protected DynamoDBQueryExpression<T> buildQueryExpression(String expression,
                                                              Map<String, AttributeValue> stringAttributeValueMap,
                                                              Boolean scanIndexForward,
                                                              Integer setLimit,
                                                              Integer withLimit) {

        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withKeyConditionExpression(expression)
                .withExpressionAttributeValues(stringAttributeValueMap);
        queryExpression.setScanIndexForward(scanIndexForward);
        queryExpression.withLimit(withLimit);
        queryExpression.setLimit(setLimit);

        return queryExpression;
    }

    @Override
    public void executeTransactionWrite(TransactionWriteRequest transactionWriteRequest) {
        try {
            dynamoDBMapper.transactionWrite(transactionWriteRequest);
        } catch (Exception e) {
            throw new DynamoException("Houve um erro ao executar a transação. " + e.getMessage());
        }
    }



    /**
     * Busca através de uma {@link DynamoDBQueryExpression} e Paginado
     */
    @Override
    public Page<T> findAllPageableWithQuery(DynamoDBQueryExpression<T> queryExpression, Pageable pageable, Class<T> clazz) {
        long scanTo = pageable.getOffset() + (2L * pageable.getPageSize());
        queryExpression.setLimit((int) Math.min(scanTo, Integer.MAX_VALUE));
        PaginatedQueryList<T> paginatedQueryList = dynamoDBMapper.query(clazz, queryExpression);
        Iterator<T> iterator = paginatedQueryList.iterator();
        if (pageable.getOffset() > 0) {
            long processedCount = scanThroughResults(iterator, pageable.getOffset());
            if (processedCount < pageable.getOffset())
                return new PageImpl<>(Collections.emptyList());
        }
        List<T> results = readPageOfResults(iterator, pageable.getPageSize());
        if (queryExpression.getProjectionExpression() != null) {
            queryExpression.setProjectionExpression(null);
        }
        int totalCount = dynamoDBMapper.count(clazz, queryExpression);
        return new PageImpl<>(results, pageable, totalCount);
    }

    private long scanThroughResults(Iterator<T> paginatedScanListIterator, long resultsToScan) {
        long processed = 0;
        while (paginatedScanListIterator.hasNext() && processed < resultsToScan) {
            paginatedScanListIterator.next();
            processed++;
        }
        return processed;
    }

    private List<T> readPageOfResults(Iterator<T> paginatedScanListIterator, int pageSize) {
        int processed = 0;
        List<T> resultsPage = new ArrayList<>();
        while (paginatedScanListIterator.hasNext() && processed < pageSize) {
            resultsPage.add(paginatedScanListIterator.next());
            processed++;
        }
        return resultsPage;
    }
}
