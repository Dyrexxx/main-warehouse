package ru.pizza.main_warehouse.domain.models.thymeleaf.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pizza.main_warehouse.domain.dto.response.BuildingKeyDTO;
import ru.pizza.main_warehouse.domain.dto.response.IngredientForStatisticDTO;
import ru.pizza.main_warehouse.domain.models.thymeleaf.empty.DeliveryEmptyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class Delivery {
    private List<ItemModel> delivery;

    public Delivery() {
        this.delivery = new ArrayList<>();
    }

    public void addItem(DeliveryEmptyModel deliveryEmptyModel) {
        ItemModel newItemModel = new ItemModel(deliveryEmptyModel.getBuilding());
        if (!delivery.contains(newItemModel)) {
            delivery.add(newItemModel);
        }

        delivery.get(delivery.indexOf(newItemModel)).ingredientForStatisticDTOS.add(deliveryEmptyModel.getIngredient());

    }

    @Getter
    @Setter
    @ToString
    public static class ItemModel {
        private BuildingKeyDTO buildingKey;
        private List<IngredientForStatisticDTO> ingredientForStatisticDTOS = new ArrayList<>();

        public ItemModel(BuildingKeyDTO buildingKey) {
            this.buildingKey = buildingKey;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ItemModel itemModel)) return false;
            return Objects.equals(buildingKey, itemModel.buildingKey);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(buildingKey);
        }
    }
}
