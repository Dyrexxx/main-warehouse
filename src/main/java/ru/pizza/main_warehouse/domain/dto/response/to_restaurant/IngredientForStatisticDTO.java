package ru.pizza.main_warehouse.domain.dto.response.to_restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
