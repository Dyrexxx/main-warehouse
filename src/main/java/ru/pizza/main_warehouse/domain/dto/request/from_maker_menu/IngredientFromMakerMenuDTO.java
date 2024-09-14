package ru.pizza.main_warehouse.domain.dto.request.from_maker_menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * dto для получение данных из мкс maker-menu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientFromMakerMenuDTO {
    private String title;
    private int minWeight;
}
