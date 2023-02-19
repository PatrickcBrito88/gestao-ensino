package com.gestaoensino.gestao_ensino.domain.wrappers.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Currenty ChunkSize of the repository is 5_000
 *
 * @param <T>
 */
@NoRepositoryBean
public interface PipelinedRedisRepository<T> extends CrudRepository<T, String> {

    /**
     * Representes the amount of data (chunk) that will be sent as commands to Redis at a single connection
     */
    int CHUNK_SIZE = 2_500;

    /**
     * Find all Registers of the target class
     *
     * @return {@link List<T>}
     */
    List<T> bulkFindAll();

    /**
     * Find all with an Index Map
     *
     * @param indexMap the Index Map
     * @return {@link List<T>}
     */
    List<T> bulkFindAll(Map<String, Object> indexMap);

    /**
     * Find all Keys of the target class
     *
     * @return {@link Set<String>}
     */
    List<String> bulkFindAllKeys();

    /**
     * Find all Ids of the target class
     *
     * @return {@link Set<String>}
     */
    Collection<String> bulkFindAllIds();

    /**
     * Delete all Keys and Values (Registers) of the target class
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @return {@link Long} the number of keys deleted
     * Note: the number of keys deleted will always be equals or higher the number of registers, depending on the number of indexes of the targer class
     */
    Long bulkDeleteAll();

    /**
     * Delete all Keys and Values (Registers) of the target class with a custom keyPrefix
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @return {@link Long} the number of keys deleted
     * Note: the number of keys deleted will always be equals or higher the number of registers, depending on the number of indexes of the targer class
     */
    Long bulkDeleteAll(String keyPrefix);

    /**
     * Delete the register with @id and its phantom key if it exists
     * This method should be used when you want to delete a register with expiration
     *
     * @param id the id
     */
    void deleteDataAndPhantomById(String id);

    /**
     * Rename all keys prefix of the target class at {@link org.springframework.data.redis.core.RedisHash} with the new prefix "{@param newKeyPrefix}"
     * Returns the number of keys found and successfully renamed
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param ignoreSetValuesRename if it should ignore the set rename
     *                              The set rename is a really expensive operation
     * @return {@link Long} the number of keys deleted
     */
    Long bulkRenameTargetClassKeys(String newKeyPrefix, boolean ignoreSetValuesRename);

    /**
     * Rename all keys prefix which matches "{@param newKeyPrefix}" and the patterm "{@param newKeyPrefix}:*", to the target class prefix at {@link org.springframework.data.redis.core.RedisHash}
     * Returns the number of keys found and successfully renamed
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param ignoreSetValuesRename if it should ignore the set rename
     *                              The set rename is a really expensive operation
     * @return {@link Long} the number of keys deleted
     */
    Long bulkRestoreTargetClassKeys(String fromKeyPrefix, boolean ignoreSetValuesRename);

    /**
     * Rename all keys prefix which matches "{@param currentPrefix}" and the patterm "{@param currentPrefix}:*", to the new prefix "{@param newKeyPrefix}"
     * Returns the number of keys found and successfully renamed
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param ignoreSetValuesRename if it should ignore the set rename
     *                              The set rename is a really expensive operation
     * @return {@link Long} the number of keys deleted
     */
    Long bulkRenameKeys(String currentPrefix, String newKeyPrefix, boolean ignoreSetValuesRename);

    /**
     * Rename all the set values of the index sets with the "{@param setKeyPrefix}", replacing the "{@param currentPrefix}:*" for the "{@param newKeyPrefix}".
     * Returns the number of sets found with the members successfully renamed
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param setKeyPrefix  the index set key prefix
     * @param currentPrefix the current prefix
     * @param newKeyPrefix  the new prefix
     * @return {@link Long} the number of keys deleted
     */
    Long bulkRenameIndexSetValues(String setKeyPrefix, String currentPrefix, String newKeyPrefix);

    /**
     * Save all Keys and Values (Registers) from a a target class list
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @return {@link Long} the number of keys saved. If The Keys already exists returns 0.
     */
    <S extends T> Long bulkSaveAll(Iterable<S> objectList);

    /**
     * Save all Keys and Values (Registers) from a a target class list with a custom keyPrefix
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @return {@link Long} the number of keys saved. If The Keys already exists returns 0.
     */
    <S extends T> Long bulkSaveAll(Iterable<S> objectList, String newKeyPrefix);

    /**
     * Set the TTL (Time to Live) of all keys of the target class with prefix at {@link org.springframework.data.redis.core.RedisHash}
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param expireTime the expiration time
     * @return {@link Long} the number of keys affected
     */
    Long bulkExpireKeys(Duration expireTime);

    /**
     * Set the TTL (Time to Live) of all keys prefix which matches "{@param keyPrefix}" and the patterm "{@param keyPrefix}:*" with the Duration time
     * <p>This method executes the operations in chunk sizes of {@value PipelinedRedisRepository#CHUNK_SIZE} registers per connection<p/>
     *
     * @param keyPrefix  the key prefix
     * @param expireTime the expiration time
     * @return {@link Long} the number of keys affected
     */
    Long bulkExpireKeysWithPrefix(String keyPrefix, Duration expireTime);

    /**
     * Get the key prefix of the current target class
     *
     * @return {@link String}
     */
    String getTargetClassKeyPrefix();
}
