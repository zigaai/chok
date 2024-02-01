package com.foo.mapper;

import com.foo.model.entity.RelUserRole;
import com.foo.infrastructure.mp.MapperSupport;
import com.foo.model.vo.RelUserRoleVO;
import com.foo.model.dto.query.RelUserRoleQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface RelUserRoleMapper extends MapperSupport<RelUserRole> {

    List<RelUserRoleVO> listByCondition(IPage<RelUserRoleVO> page, @Param("params") RelUserRoleQuery params, @Param("columns") String... columns);

}
