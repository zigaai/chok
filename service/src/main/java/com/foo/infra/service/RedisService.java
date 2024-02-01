package com.foo.infra.service;

import com.foo.infra.exception.BizIllegalArgumentException;
import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public Map<byte[], byte[]> getRawKeys(Map<String, Object> origin) {
        ImmutableMap.Builder<byte[], byte[]> builder = ImmutableMap.builder();
        StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
        JdkSerializationRedisSerializer valueSerializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
        for (Map.Entry<String, Object> entry : origin.entrySet()) {
            byte[] key = keySerializer.serialize(entry.getKey());
            if (key == null) {
                throw new BizIllegalArgumentException("key could not be null");
            }
            byte[] val = valueSerializer.serialize(entry.getValue());
            if (val == null) {
                throw new BizIllegalArgumentException("val could not be null");
            }
            builder.put(key, val);
        }
        return builder.build();
    }

    public void multiSet(Map<String, Object> map, long expire) {
        Map<byte[], byte[]> rawKeys = getRawKeys(map);
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.stringCommands().mSet(rawKeys);
            Set<byte[]> keySet = rawKeys.keySet();
            for (byte[] key : keySet) {
                connection.keyCommands().expire(key, expire);
            }
            return keySet.size();
        });
    }
}
