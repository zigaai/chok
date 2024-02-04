package com.zigaai.model.convertor;

import com.zigaai.model.dto.command.UserDTO;
import com.zigaai.model.entity.User;
import com.zigaai.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvertor {

    UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

    UserDTO toDTO(User entity);

    UserVO toVO(User entity);

    User toEntity(UserDTO dto);

    User toEntity(UserVO vo);

}