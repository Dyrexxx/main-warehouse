package ru.pizza.main_warehouse.domain.dto.response.to_restaurant;

import lombok.Data;

import java.util.Objects;

@Data
public class IngredientForStatisticDTO {
    private String title;
    private int weight;
    private boolean isNew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientForStatisticDTO that)) return false;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
