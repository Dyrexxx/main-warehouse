package ru.pizza.main_warehouse.domain.dto.response.to_restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * dto для формирования доставки в мкс restaurant
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDTO {
    private  BuildingToRestaurantDTO building;
    private  List<IngredientToRestaurantDTO> ingredientList = new ArrayList<>();

    public DeliveryDTO(BuildingToRestaurantDTO building) {
        this.building = building;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryDTO that)) return false;
        return Objects.equals(building, that.building);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(building);
    }
}
