package com.zigaai.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.db.MapperSupport;
import com.zigaai.model.dto.command.Oauth2RegisteredClientDTO;
import com.zigaai.model.dto.query.Oauth2RegisteredClientQuery;
import com.zigaai.model.entity.Oauth2RegisteredClient;
import com.zigaai.oauth2.repo.Oauth2RegisteredClientMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-07-02
 */
public interface DaoOauth2RegisteredClientMapper extends MapperSupport<Oauth2RegisteredClient>, Oauth2RegisteredClientMapper {

    @Override
    Oauth2RegisteredClient getByClientId(String clientId);

    List<Oauth2RegisteredClient> page(IPage<Oauth2RegisteredClient> page,
                                                 @Param("params") Oauth2RegisteredClientQuery params,
                                                 @Param("columns") String... columns);

    int updateStateBatch(@Param("data") Oauth2RegisteredClientDTO data);

}
