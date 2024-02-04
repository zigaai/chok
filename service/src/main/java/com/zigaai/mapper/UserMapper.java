package com.zigaai.mapper;

import com.zigaai.model.entity.User;
import com.zigaai.db.MapperSupport;
import com.zigaai.model.vo.UserVO;
import com.zigaai.model.dto.query.UserQuery;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zigaai
 * @since 2023-11-06
 */
public interface UserMapper extends MapperSupport<User>, AuthenticationMapper<User> {

    List<UserVO> listByCondition(IPage<UserVO> page, @Param("params") UserQuery params, @Param("columns") String... columns);

    @Override
    User getByUsername(String username);

    @Override
    int updateSalt(@Param("username") String username, @Param("salt") String salt);

    @Override
    String getSaltByUsername(@Param("username") String username);

}
