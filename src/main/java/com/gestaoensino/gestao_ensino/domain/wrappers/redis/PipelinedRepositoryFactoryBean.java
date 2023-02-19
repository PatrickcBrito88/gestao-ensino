package com.gestaoensino.gestao_ensino.domain.wrappers.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.repository.support.QuerydslKeyValueRepository;
import org.springframework.data.keyvalue.repository.support.SimpleKeyValueRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.QuerydslUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.support.RedisRepositoryFactory;
import org.springframework.data.redis.repository.support.RedisRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Map;

public class PipelinedRepositoryFactoryBean<R extends Repository<T, I>, T, I extends Serializable> extends RedisRepositoryFactoryBean<R, T, I> implements ApplicationContextAware {
    private RedisTemplate<String, T> redisTemplate;
    private KeyValueAdapter adapter;

    public PipelinedRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
     * Obtém os Beans extras do RedisPipelinedRepository
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.redisTemplate = findBean(applicationContext, RedisTemplate.class);
        this.adapter = findBean(applicationContext, KeyValueAdapter.class);
    }

    private <B> B findBean(ApplicationContext applicationContext, Class<B> clazz) {
        Map<String, B> beans = applicationContext.getBeansOfType(clazz);
        if (beans.isEmpty()) {
            throw new NoSuchBeanDefinitionException("Could not find any bean that matches [" + clazz.getName() + "]");
        } else {
            Map.Entry<String, B> bean = beans.entrySet().iterator().next();
            return bean.getValue();
        }
    }

    @NonNull
    @Override
    protected RedisRepositoryFactory createRepositoryFactory(@NonNull KeyValueOperations operations,
                                                             @NonNull Class<? extends AbstractQueryCreator<?, ?>> queryCreator,
                                                             @NonNull Class<? extends RepositoryQuery> repositoryQueryType) {
        return new PipelinedRepositoryFactory<T>(operations, queryCreator, repositoryQueryType, this.redisTemplate, this.adapter);
    }

    private static class PipelinedRepositoryFactory<T> extends RedisRepositoryFactory {
        private final KeyValueAdapter adapter;
        private final RedisTemplate<String, T> redisTemplate;
        private final KeyValueOperations keyValueOperations;

        public PipelinedRepositoryFactory(KeyValueOperations keyValueOperations,
                                          Class<? extends AbstractQueryCreator<?, ?>> queryCreator,
                                          Class<? extends RepositoryQuery> repositoryQueryType,
                                          RedisTemplate<String, T> redisTemplate,
                                          KeyValueAdapter adapter) {
            super(keyValueOperations, queryCreator, repositoryQueryType);
            this.keyValueOperations = keyValueOperations;
            this.redisTemplate = redisTemplate;
            this.adapter = adapter;
        }

        @NonNull
        @Override
        protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
            EntityInformation<?, ?> entityInformation = this.getEntityInformation(repositoryInformation.getDomainType());
            return isPipelinedRedisRepository(repositoryInformation.getRepositoryInterface())
                    ? super.getTargetRepositoryViaReflection(repositoryInformation, entityInformation, this.keyValueOperations, this.redisTemplate, this.adapter)
                    : super.getTargetRepositoryViaReflection(repositoryInformation, entityInformation, this.keyValueOperations);
        }

        @NonNull
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            if (isQueryDslRepository(metadata.getRepositoryInterface())) {
                return QuerydslKeyValueRepository.class;
            } else if (isPipelinedRedisRepository(metadata.getRepositoryInterface())) {
                return PipelinedRedisRepositoryImpl.class;
            }
            return SimpleKeyValueRepository.class;
        }

        private static boolean isPipelinedRedisRepository(Class<?> repositoryInterface) {
            return PipelinedRedisRepository.class.isAssignableFrom(repositoryInterface.getInterfaces()[0]);
        }

        private static boolean isQueryDslRepository(Class<?> repositoryInterface) {
            return QuerydslUtils.QUERY_DSL_PRESENT && QuerydslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }
    }
}
