package com.foo.mapper;

import com.foo.model.entity.Menu;
import com.foo.infrastructure.mp.MapperSupport;
import com.foo.model.vo.MenuVO;
import com.foo.model.dto.query.MenuQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 页面权限表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface MenuMapper extends MapperSupport<Menu> {

    List<MenuVO> listByCondition(IPage<MenuVO> page, @Param("params") MenuQuery params, @Param("columns") String... columns);

    List<Menu> listByRoleIds(@Param("roleIds") Collection<Long> roleIds);

}
