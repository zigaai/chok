package com.foo.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.foo.model.dto.command.RoleDTO;
import com.foo.model.entity.Role;
import com.foo.model.vo.RoleVO;

@Mapper
public interface RoleConvertor {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    RoleDTO toDTO(Role entity);

    RoleVO toVO(Role entity);

    Role toEntity(RoleDTO DTO);

    Role toEntity(RoleVO VO);

}