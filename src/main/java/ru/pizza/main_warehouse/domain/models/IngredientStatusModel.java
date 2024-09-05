package ru.pizza.main_warehouse.domain.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;
import ru.pizza.main_warehouse.domain.enums.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель для заполнения данными из двух мкс (maker-menu, restaurant)
 */
@Data
@NoArgsConstructor
public class IngredientStatusModel {
    private Status status;
    private List<IngredientForStatisticDTO> ingredientList;

    public IngredientStatusModel(Status status) {
        this.status = status;
        this.ingredientList = new ArrayList<>();
    }
}
