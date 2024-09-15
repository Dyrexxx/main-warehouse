package ru.pizza.main_warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuildingMapper {
    BuildingToRestaurantDTO toBuildingKeyModel(BuildingFromRestaurantDTO building);
}
