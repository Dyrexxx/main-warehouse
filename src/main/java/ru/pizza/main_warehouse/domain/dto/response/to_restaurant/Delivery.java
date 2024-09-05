package ru.pizza.main_warehouse.domain.dto.response.to_restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.pizza.main_warehouse.domain.models.thymeleaf.empty.DeliveryEmptyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * dto для формирования доставки в мкс restaurant
 */
@Getter
@Setter
@ToString
public class Delivery {
    private List<ItemDelivery> delivery;

    public Delivery() {
        this.delivery = new ArrayList<>();
    }

    public void addItem(DeliveryEmptyModel deliveryEmptyModel) {
        ItemDelivery newItemModel = new ItemDelivery(deliveryEmptyModel.getBuilding());
        if (!delivery.contains(newItemModel)) {
            delivery.add(newItemModel);
        }
        int index = delivery.indexOf(newItemModel);
        ItemDelivery findedItem = delivery.get(index);
        if (findedItem.getIngredientList().contains(deliveryEmptyModel.getIngredient())) {
            int indexIngredient = findedItem.getIngredientList().indexOf(deliveryEmptyModel.getIngredient());
            int weight = findedItem.getIngredientList().get(indexIngredient).getWeight();
            findedItem.getIngredientList().get(indexIngredient).setWeight(weight + deliveryEmptyModel.getIngredient().getWeight());
        }
        else {
            findedItem.getIngredientList().add(deliveryEmptyModel.getIngredient());
        }

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ItemDelivery {
        private BuildingToRestaurantDTO building;
        private List<IngredientForStatisticDTO> ingredientList = new ArrayList<>();

        public ItemDelivery(BuildingToRestaurantDTO buildingKey) {
            this.building = buildingKey;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemDelivery that)) return false;
            return Objects.equals(building, that.building);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(building);
        }
    }
}
