package com.foo.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.foo.model.dto.command.UserDTO;
import com.foo.model.entity.User;
import com.foo.model.vo.UserVO;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    UserDTO toDTO(User entity);

    UserVO toVO(User entity);

    User toEntity(UserDTO DTO);

    User toEntity(UserVO VO);

}