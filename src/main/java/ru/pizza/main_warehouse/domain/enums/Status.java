package ru.pizza.main_warehouse.domain.enums;

import lombok.Getter;
import lombok.ToString;

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
