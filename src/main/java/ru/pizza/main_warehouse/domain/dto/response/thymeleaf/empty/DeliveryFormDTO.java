package ru.pizza.main_warehouse.domain.dto.response.thymeleaf.empty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;

/**
 * dto для thymeleaf. Работает с th:object
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryFormDTO {
    private BuildingToRestaurantDTO building;
    private IngredientToRestaurantDTO ingredient;
}
