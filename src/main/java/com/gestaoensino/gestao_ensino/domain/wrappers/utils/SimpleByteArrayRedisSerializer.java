package com.gestaoensino.gestao_ensino.domain.wrappers.utils;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

public class SimpleByteArrayRedisSerializer implements RedisSerializer<byte[]> {

    @Override
    public byte[] serialize(byte[] bytes) throws SerializationException {
        return bytes;
    }

    @Override
    public byte[] deserialize(@Nullable byte[] bytes) {
        return bytes;
    }
}