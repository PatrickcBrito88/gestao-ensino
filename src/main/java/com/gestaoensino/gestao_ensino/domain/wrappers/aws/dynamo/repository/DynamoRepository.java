package com.gestaoensino.gestao_ensino.domain.wrappers.aws.dynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DynamoRepository<T> {

    String getHashKeyFieldName();

    List<T> findAllByHashKey(Object hash);

    List<T> queryPage(Object hashKeyValue, DynamoDBQueryExpression<T> dynamoDBQueryExpression);

    List<T> query(Object hashKeyValue, DynamoDBQueryExpression<T> dynamoDBQueryExpression);

    T save(T entity);

    void saveAll(Iterable<T> entities);

    void delete(T entity);

    void deleteAll(Iterable<T> entities);

    void executeTransactionWrite(TransactionWriteRequest transactionWriteRequest);

    Page<T> findAllPageableWithQuery(DynamoDBQueryExpression<T> queryExpression, Pageable pageable, Class<T> clazz);
}
