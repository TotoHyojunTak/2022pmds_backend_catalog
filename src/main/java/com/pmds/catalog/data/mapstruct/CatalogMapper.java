package com.pmds.catalog.data.mapstruct;

import com.pmds.catalog.data.dto.request.CatalogReqDTO;
import com.pmds.catalog.data.dto.response.CatalogDTO;
import com.pmds.catalog.data.entity.CatalogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    CatalogDTO toDto(CatalogEntity entity);


    CatalogEntity toEntity(CatalogReqDTO dto);


    List<CatalogDTO> toDtoList(List<CatalogEntity> entityList);

	List<CatalogEntity> toEntityList(List<CatalogReqDTO> dtoList);


}
