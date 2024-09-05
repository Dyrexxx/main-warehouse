package ru.pizza.main_warehouse.domain.dto.request.from_maker_menu;

import lombok.Data;

/***
 * dto для получение данных из мкс maker-menu
 */
@Data
public class IngredientFromMakerMenuDTO {
    private String title;
    private int weight;
    private int minWeight;
}
