package ru.pizza.main_warehouse.domain.models.thymeleaf.empty;

import lombok.Data;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;

@Data
public class DeliveryEmptyModel {
    private BuildingToRestaurantDTO building;
    private IngredientForStatisticDTO ingredient;
}
