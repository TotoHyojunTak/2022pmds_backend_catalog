package com.backend.order.order.data.mapstruct;

import com.backend.order.order.data.dto.request.OrderReqDTO;
import com.backend.order.order.data.dto.response.OrderDTO;
import com.backend.order.order.data.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDto(OrderEntity entity);



    @Mapping(target="id", ignore=true)
    @Mapping(target="userId", ignore=true)
    OrderEntity toEntity(OrderReqDTO dto);


    List<OrderDTO> toDtoList(List<OrderEntity> entityList);

	List<OrderEntity> toEntityList(List<OrderReqDTO> dtoList);


}
