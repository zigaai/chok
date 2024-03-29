package com.zigaai.mapper;

import com.zigaai.model.security.AuthenticationModel;
import org.apache.ibatis.annotations.Param;

public interface AuthenticationMapper<T extends AuthenticationModel> {

    T getByUsername(String username);

    int updateSalt(@Param("username") String username, @Param("salt") String salt);

    String getSaltByUsername(@Param("username") String username);

}
