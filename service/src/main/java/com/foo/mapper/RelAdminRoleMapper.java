package com.foo.mapper;

import com.foo.model.entity.RelAdminRole;
import com.foo.infrastructure.mp.MapperSupport;
import com.foo.model.vo.RelAdminRoleVO;
import com.foo.model.dto.query.RelAdminRoleQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * <p>
 * 管理员角色关联表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface RelAdminRoleMapper extends MapperSupport<RelAdminRole> {

    List<RelAdminRoleVO> listByCondition(IPage<RelAdminRoleVO> page, @Param("params") RelAdminRoleQuery params, @Param("columns") String... columns);

}
