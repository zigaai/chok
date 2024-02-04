package com.zigaai.model.convertor;

import com.zigaai.model.dto.command.AdminDTO;
import com.zigaai.model.entity.Admin;
import com.zigaai.model.vo.AdminVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminConvertor {

    AdminConvertor INSTANCE = Mappers.getMapper(AdminConvertor.class);

    AdminDTO toDTO(Admin entity);

    AdminVO toVO(Admin entity);

    Admin toEntity(AdminDTO dto);

    Admin toEntity(AdminVO vo);

}