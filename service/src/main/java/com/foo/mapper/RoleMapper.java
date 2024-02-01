package com.foo.mapper;

import com.foo.model.entity.Role;
import com.foo.infrastructure.mp.MapperSupport;
import com.foo.model.vo.RoleVO;
import com.foo.model.dto.query.RoleQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

}
