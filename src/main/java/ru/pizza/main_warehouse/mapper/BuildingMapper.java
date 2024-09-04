package ru.pizza.main_warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.BuildingKeyDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuildingMapper {
    BuildingKeyDTO toBuildingKeyModel(BuildingFromRestaurantDTO building);
    List<BuildingKeyDTO> toBuildingKeyList(List<BuildingFromRestaurantDTO> buildings);
}
