package ru.pizza.main_warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.pizza.main_warehouse.domain.dto.request.from_maker_menu.IngredientFromMakerMenuDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.IngredientFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {
    IngredientForStatisticDTO toIngredientForStatistic(IngredientFromMakerMenuDTO ingredient);
    IngredientForStatisticDTO toIngredientForStatistic(IngredientFromRestaurantDTO ingredient);
}
