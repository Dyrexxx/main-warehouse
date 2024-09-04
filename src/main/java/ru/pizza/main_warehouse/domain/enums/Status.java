package ru.pizza.main_warehouse.domain.enums;

import lombok.Getter;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Status {
    SHORTAGE("Недостаток", new ArrayList<>()),
    ACCEPTABLE("Приемлимо", new ArrayList<>()),
    NOT_FOUND("Нет в наличии", new ArrayList<>());

    private final String valueName;
    private final List<IngredientForStatisticDTO> ingredientForStatisticList;

    Status(String valueName, List<IngredientForStatisticDTO> ingredientForStatisticDTOS) {
        this.valueName = valueName;
        this.ingredientForStatisticList = ingredientForStatisticDTOS;
    }
}
