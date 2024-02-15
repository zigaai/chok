package com.zigaai.mapper;


import com.zigaai.db.MapperSupport;
import com.zigaai.infra.security.Oauth2RegisteredClient;
import com.zigaai.oauth2.repo.Oauth2RegisteredClientMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-02
 */
public interface DaoOauth2RegisteredClientMapper extends MapperSupport<Oauth2RegisteredClient>, Oauth2RegisteredClientMapper {

    Oauth2RegisteredClient getByClientId(String clientId);

    String getClientIdById(String id);

}
