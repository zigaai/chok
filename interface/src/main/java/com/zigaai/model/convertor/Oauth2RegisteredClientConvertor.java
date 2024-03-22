package com.zigaai.model.convertor;

import com.zigaai.model.dto.command.Oauth2RegisteredClientDTO;
import com.zigaai.model.entity.Oauth2RegisteredClient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Oauth2RegisteredClientConvertor {

    Oauth2RegisteredClientConvertor INSTANCE = Mappers.getMapper(Oauth2RegisteredClientConvertor.class);

    Oauth2RegisteredClient toEntity(Oauth2RegisteredClientDTO dto);

}
