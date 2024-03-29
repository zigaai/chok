package com.zigaai.mapper;

import com.zigaai.model.entity.Admin;
import com.zigaai.db.MapperSupport;
import com.zigaai.model.vo.AdminVO;
import com.zigaai.model.dto.query.AdminQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface AdminMapper extends MapperSupport<Admin>, AuthenticationMapper<Admin> {

    List<AdminVO> listByCondition(IPage<AdminVO> page, @Param("params") AdminQuery params, @Param("columns") String... columns);

    @Override
    Admin getByUsername(String username);

    @Override
    String getSaltByUsername(@Param("username") String username);

    @Override
    int updateSalt(@Param("username") String username, @Param("salt") String salt);
}
