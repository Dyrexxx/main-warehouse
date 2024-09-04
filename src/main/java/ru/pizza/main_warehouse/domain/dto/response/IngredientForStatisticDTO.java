package ru.pizza.main_warehouse.domain.dto.response;

import lombok.Data;

@Data
public class IngredientForStatisticDTO {
    private String title;
    private int weight;
    private boolean isNew;
}
