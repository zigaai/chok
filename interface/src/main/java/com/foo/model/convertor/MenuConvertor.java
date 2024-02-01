package com.foo.model.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.foo.model.dto.command.MenuDTO;
import com.foo.model.entity.Menu;
import com.foo.model.vo.MenuVO;

@Mapper
public interface MenuConvertor {

    MenuConvertor INSTANCE = Mappers.getMapper(MenuConvertor.class);

    MenuDTO toDTO(Menu entity);

    MenuVO toVO(Menu entity);

    Menu toEntity(MenuDTO dto);

    Menu toEntity(MenuVO vo);

}