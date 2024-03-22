package com.zigaai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zigaai.exception.BizException;
import com.zigaai.mapper.DaoOauth2RegisteredClientMapper;
import com.zigaai.model.convertor.Oauth2RegisteredClientConvertor;
import com.zigaai.model.dto.command.Oauth2RegisteredClientDTO;
import com.zigaai.model.dto.query.Oauth2RegisteredClientQuery;
import com.zigaai.model.entity.Oauth2RegisteredClient;
import com.zigaai.oauth2.constants.OAuth2RedisKeys;
import com.zigaai.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zigaai
 * @since 2024-03-18 10:58:09
 */
@Service
@RequiredArgsConstructor
public class Oauth2RegisteredClientServiceImpl {

    private final DaoOauth2RegisteredClientMapper mapper;

    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, Object> redisTemplate;

    public Page<Oauth2RegisteredClient> page(Oauth2RegisteredClientQuery params) {
        Page<Oauth2RegisteredClient> page = new Page<>(params.getCurrent(), params.getSize());
        List<Oauth2RegisteredClient> list = mapper.page(page, params);
        page.setRecords(list);
        return page;
    }

    public int save(Oauth2RegisteredClientDTO data) {
        String clientSecret = RandomStringUtils.randomAlphanumeric(20);
        String encryptedPassword = passwordEncoder.encode(clientSecret);
        data.setPlainClientSecret(clientSecret);
        this.defaultSetting(data);
        Oauth2RegisteredClient entity = Oauth2RegisteredClientConvertor.INSTANCE.toEntity(data);
        entity.setClientSecret(encryptedPassword);
        return mapper.insert(entity);
    }

    public int update(Oauth2RegisteredClientDTO data) throws JsonProcessingException {
        Oauth2RegisteredClient model = mapper.getByClientId(data.getClientId());
        if (model == null) {
            throw new BizException("暂无客户端信息");
        }
        this.updateTokenSettings(model, data);
        BeanUtils.copyProperties(data, model, "clientId", "clientSecret");
        int count = mapper.updateById(model);
        String key = OAuth2RedisKeys.OAUTH2_REGISTERED_CLIENT(data.getClientId());
        redisTemplate.delete(key);
        return count;
    }

    public int deleteById(Long id) {
        return mapper.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateStateBatch(Oauth2RegisteredClientDTO data) {
        List<String> keys = new ArrayList<>(data.getClientIds().size());
        for (String item : data.getClientIds()) {
            keys.add(OAuth2RedisKeys.OAUTH2_REGISTERED_CLIENT(item));
        }
        int count = mapper.updateStateBatch(data);
        redisTemplate.delete(keys);
        return count;
    }

    private void defaultSetting(Oauth2RegisteredClientDTO data) {
        if (StringUtils.isBlank(data.getClientAuthenticationMethods())) {
            data.setClientAuthenticationMethods("client_secret_basic");
        }
        if (StringUtils.isBlank(data.getAuthorizationGrantTypes())) {
            data.setAuthorizationGrantTypes("refresh_token,client_credentials,authorization_code");
        }
        if (StringUtils.isBlank(data.getClientSettings())) {
            data.setClientSettings("{\"settings.client.require-proof-key\": false, \"settings.client.require-authorization-consent\": true}");
        }
        if (StringUtils.isBlank(data.getTokenSettings())) {
            // 默认 授权码5分钟过期, access_token 1小时过期, refresh_token 7天过期
            Long accessTokenTtl = data.getAccessTokenTimeToLive();
            if (accessTokenTtl == null) {
                accessTokenTtl = 3600L;
            }
            Long refreshTokenTtl = data.getRefreshTokenTimeToLive();
            if (refreshTokenTtl == null) {
                refreshTokenTtl = 604800L;
            }
            Long authorizationCodeTtl = data.getAuthorizationCodeTimeToLive();
            if (authorizationCodeTtl == null) {
                authorizationCodeTtl = 300L;
            }
            data.setTokenSettings("{\"settings.token.access-token-format\": null, \"settings.token.reuse-refresh-tokens\": true, \"settings.token.access-token-time-to-live\": "
                    + accessTokenTtl
                    + ", \"settings.token.refresh-token-time-to-live\": "
                    + refreshTokenTtl
                    + ", \"settings.token.id-token-signature-algorithm\": \"RS256\", \"settings.token.authorization-code-time-to-live\": "
                    + authorizationCodeTtl
                    + "}");
        }
    }

    public void updateTokenSettings(Oauth2RegisteredClient originModel, Oauth2RegisteredClientDTO data) throws JsonProcessingException {
        JsonNode jsonNode = JsonUtil.getInstance().readTree(originModel.getTokenSettings());
        if (!jsonNode.isObject()) {
            return;
        }
        Long accessTokenTtl = data.getAccessTokenTimeToLive();
        boolean isUpdated = false;
        if (accessTokenTtl != null) {
            ((ObjectNode) jsonNode).put(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, accessTokenTtl);
            isUpdated = true;
        }
        Long refreshTokenTtl = data.getRefreshTokenTimeToLive();
        if (refreshTokenTtl != null) {
            ((ObjectNode) jsonNode).put(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE, refreshTokenTtl);
            isUpdated = true;
        }
        Long authorizationCodeTtl = data.getAuthorizationCodeTimeToLive();
        if (authorizationCodeTtl != null) {
            ((ObjectNode) jsonNode).put(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE, authorizationCodeTtl);
            isUpdated = true;
        }
        if (isUpdated) {
            data.setTokenSettings(jsonNode.toString());
        }
    }

}
