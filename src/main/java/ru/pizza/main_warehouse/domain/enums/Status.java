package ru.pizza.main_warehouse.domain.enums;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public enum Status {
    SHORTAGE("Недостаток"),
    ACCEPTABLE("Приемлимо"),
    NOT_FOUND("Нет в наличии");

    private final String valueName;

    Status(String valueName) {
        this.valueName = valueName;
    }



}
