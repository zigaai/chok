// package com.zigaai.infra.service;
//
// import com.zigaai.constants.RedisConstant;
// import com.google.common.collect.ImmutableMap;
// import com.zigaai.exception.BizIllegalArgumentException;
// import com.zigaai.model.security.PayloadDTO;
// import com.zigaai.model.security.UPMSToken;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.redis.connection.RedisConnection;
// import org.springframework.data.redis.core.RedisCallback;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;
// import org.springframework.stereotype.Component;
// import org.springframework.util.CollectionUtils;
//
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;
//
// @Getter
// @Component
// @RequiredArgsConstructor
// public class RedisService {
//
//     private final RedisTemplate<String, Object> redisTemplate;
//
//     public Map<byte[], byte[]> getRawKeys(Map<String, Object> origin) {
//         ImmutableMap.Builder<byte[], byte[]> builder = ImmutableMap.builder();
//         StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
//         JdkSerializationRedisSerializer valueSerializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
//         for (Map.Entry<String, Object> entry : origin.entrySet()) {
//             byte[] key = keySerializer.serialize(entry.getKey());
//             if (key == null) {
//                 throw new BizIllegalArgumentException("key could not be null");
//             }
//             byte[] val = valueSerializer.serialize(entry.getValue());
//             if (val == null) {
//                 throw new BizIllegalArgumentException("val could not be null");
//             }
//             builder.put(key, val);
//         }
//         return builder.build();
//     }
//
//     public void multiSet(Map<String, Object> map, long expire) {
//         redisTemplate.execute((RedisCallback<Integer>) connection -> multiSet(connection, map, expire));
//     }
//
//     public byte[] serializeKey(String key) {
//         StringRedisSerializer keySerializer = (StringRedisSerializer) redisTemplate.getKeySerializer();
//         return keySerializer.serialize(key);
//     }
//
//     public byte[] serializeVal(Object val) {
//         JdkSerializationRedisSerializer valueSerializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
//         return valueSerializer.serialize(val);
//     }
//
//     @SuppressWarnings("unchecked")
//     public <T> T deserializeVal(byte[] data) {
//         if (data == null) {
//             return null;
//         }
//         JdkSerializationRedisSerializer valueSerializer = (JdkSerializationRedisSerializer) redisTemplate.getValueSerializer();
//         return (T) valueSerializer.deserialize(data);
//     }
//
//     public int multiSet(RedisConnection connection, Map<String, Object> map, long expire) {
//         Map<byte[], byte[]> rawKeys = getRawKeys(map);
//         connection.stringCommands().mSet(rawKeys);
//         Set<byte[]> keySet = rawKeys.keySet();
//         for (byte[] key : keySet) {
//             connection.keyCommands().expire(key, expire);
//         }
//         return keySet.size();
//     }
//
//     public void cacheRefreshToken(UPMSToken upmsToken, PayloadDTO payload) {
//         long refreshTimeToLive = upmsToken.getRefreshExpiresIn();
//         String refreshToken = upmsToken.getRefreshToken();
//         String refreshTokenInfoKey = RedisConstant.REFRESH_TOKEN_INFO(refreshToken);
//         String userRefreshTokensKey = RedisConstant.USER_REFRESH_TOKEN(payload.getUserType(), payload.getUsername());
//         redisTemplate.execute((RedisCallback<Integer>) connection -> {
//             HashSet<String> refreshTokens = deserializeVal(connection.stringCommands().get(serializeKey(userRefreshTokensKey)));
//             if (CollectionUtils.isEmpty(refreshTokens)) {
//                 refreshTokens = new HashSet<>();
//             }
//             refreshTokens.add(refreshToken);
//             ImmutableMap<String, Object> map = ImmutableMap
//                     .<String, Object>builder()
//                     .put(refreshTokenInfoKey, payload)
//                     .put(userRefreshTokensKey, refreshTokens)
//                     .build();
//             return multiSet(connection, map, refreshTimeToLive);
//         });
//     }
//
// }
