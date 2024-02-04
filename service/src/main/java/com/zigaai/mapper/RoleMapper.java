package com.zigaai.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zigaai.db.MapperSupport;
import com.zigaai.model.dto.query.RoleQuery;
import com.zigaai.model.entity.Role;
import com.zigaai.model.vo.RoleVO;
import com.zigaai.security.properties.CustomSecurityProperties;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface RoleMapper extends MapperSupport<Role> {

    List<RoleVO> listByCondition(IPage<RoleVO> page, @Param("params") RoleQuery params, @Param("columns") String... columns);

    List<Role> listBySysUserId(@Param("userId") Long userId, @Param("userType") CustomSecurityProperties.Context userType);

}
