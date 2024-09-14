package ru.pizza.main_warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.pizza.main_warehouse.domain.dto.response.thymeleaf.empty.DeliveryFormDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;

@Mapper
public interface DeliveryMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);
    DeliveryDTO toDeliveryDTO(DeliveryFormDTO deliveryFormDTO);
}
