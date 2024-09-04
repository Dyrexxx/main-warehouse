package ru.pizza.main_warehouse.domain.models.thymeleaf.empty;

import lombok.Data;
import ru.pizza.main_warehouse.domain.dto.response.BuildingKeyDTO;
import ru.pizza.main_warehouse.domain.dto.response.IngredientForStatisticDTO;

@Data
public class DeliveryEmptyModel {
    private BuildingKeyDTO building;
    private IngredientForStatisticDTO ingredient;
}
