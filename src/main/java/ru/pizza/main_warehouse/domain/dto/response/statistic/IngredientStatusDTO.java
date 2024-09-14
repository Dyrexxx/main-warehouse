package ru.pizza.main_warehouse.domain.dto.response.statistic;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;
import ru.pizza.main_warehouse.domain.enums.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель для заполнения данными из двух мкс (maker-menu, restaurant)
 */
@Data
@NoArgsConstructor
public class IngredientStatusDTO {
    private Status status;
    private List<IngredientToRestaurantDTO> ingredientList;

    public IngredientStatusDTO(Status status) {
        this.status = status;
        this.ingredientList = new ArrayList<>();
    }
    public IngredientStatusDTO(Status status, List<IngredientToRestaurantDTO> ingredientList) {
        this.status = status;
        this.ingredientList = ingredientList;
    }
}
