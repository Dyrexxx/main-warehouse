package ru.pizza.main_warehouse.domain.dto.request.from_restaurant;

import lombok.Data;
import ru.pizza.main_warehouse.domain.enums.Status;

/***
 * dto для получения данных из мкс restaurant
 */
@Data
public class IngredientFromRestaurantDTO {
    private String title;
    private int weight;

}
