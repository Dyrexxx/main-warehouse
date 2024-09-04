package ru.pizza.main_warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuildingMapper {
    BuildingDTO toBuildingKeyModel(BuildingFromRestaurantDTO building);
    List<BuildingDTO> toBuildingKeyList(List<BuildingFromRestaurantDTO> buildings);
}
