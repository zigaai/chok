package com.zigaai.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.zigaai.model.dto.command.MenuDTO;
import com.zigaai.model.entity.Menu;
import com.zigaai.model.vo.MenuVO;

@Mapper
public interface MenuConvertor {

    MenuConvertor INSTANCE = Mappers.getMapper(MenuConvertor.class);

    MenuDTO toDTO(Menu entity);

    MenuVO toVO(Menu entity);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    Menu toEntity(MenuDTO dto);

    Menu toEntity(MenuVO vo);

}