package com.gestaoensino.gestao_ensino.domain.wrappers.redis;

import org.springframework.data.redis.core.convert.RedisConverter;
import org.springframework.data.redis.core.convert.RedisData;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.lang.NonNull;

import java.util.Map;

public class PipelinedHashMapper implements HashMapper<Object, byte[], byte[]> {

    private final RedisConverter converter;

    public PipelinedHashMapper(RedisConverter converter) {
        this.converter = converter;
    }

    @NonNull
    @Override
    public Map<byte[], byte[]> toHash(@NonNull Object source) {
        RedisData sink = new RedisData();
        converter.write(source, sink);
        return sink.getBucket().rawMap();
    }

    @NonNull
    @Override
    public Object fromHash(@NonNull Map<byte[], byte[]> hash) {
        return fromHash(hash, Object.class);
    }

    @NonNull
    public <T> T fromHash(@NonNull Map<byte[], byte[]> hash, Class<T> clazz) {
        return converter.read(clazz, new RedisData(hash));
    }
}