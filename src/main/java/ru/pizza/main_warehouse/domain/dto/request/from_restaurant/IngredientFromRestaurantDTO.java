package ru.pizza.main_warehouse.domain.dto.request.from_restaurant;

import lombok.*;

/***
 * dto для получения данных из мкс restaurant
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientFromRestaurantDTO{
    private String title;
    private int weight;
}
