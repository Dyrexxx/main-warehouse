package ru.pizza.main_warehouse.domain.dto.request.from_restaurant;

import lombok.*;

import java.util.List;
import java.util.Objects;

/***
 * dto для получение данных из мкс restaurant
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BuildingFromRestaurantDTO {
    private int id;
    private String title;
    private List<IngredientFromRestaurantDTO> ingredientList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BuildingFromRestaurantDTO buildingFromRestaurantDTO)) return false;
        return id == buildingFromRestaurantDTO.id && Objects.equals(title, buildingFromRestaurantDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}