package com.zigaai.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.zigaai.model.dto.command.RoleDTO;
import com.zigaai.model.entity.Role;
import com.zigaai.model.vo.RoleVO;

@Mapper
public interface RoleConvertor {

    RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

    RoleDTO toDTO(Role entity);

    RoleVO toVO(Role entity);

    Role toEntity(RoleDTO dto);

    Role toEntity(RoleVO vo);

}