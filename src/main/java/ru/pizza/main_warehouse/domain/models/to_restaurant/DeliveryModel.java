package ru.pizza.main_warehouse.domain.models.to_restaurant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.pizza.main_warehouse.domain.dto.response.thymeleaf.empty.DeliveryFormDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;
import ru.pizza.main_warehouse.mapper.DeliveryMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DeliveryModel {
    private final List<DeliveryDTO> deliveryList = new ArrayList<>();
    private final DeliveryMapper deliveryMapper = DeliveryMapper.INSTANCE;

    /***
     * Добавляет ингредиент в корзину доставки к определенному ресторану
     * @param deliveryFormDTO dto в которой находится информация в какой ресторан доставить и какой ингредиент доставить
     */
    public void add(DeliveryFormDTO deliveryFormDTO) {
        DeliveryDTO newDeliveryDTO = deliveryMapper.toDeliveryDTO(deliveryFormDTO);
        newDeliveryDTO.getIngredientList().add(deliveryFormDTO.getIngredient());
        if (!deliveryList.contains(newDeliveryDTO)) {
            deliveryList.add(newDeliveryDTO);
        } else {
            int index = deliveryList.indexOf(newDeliveryDTO);
            List<IngredientToRestaurantDTO> findedDeliveryList = deliveryList.get(index).getIngredientList();
            IngredientToRestaurantDTO formIngredient = deliveryFormDTO.getIngredient();
            if (findedDeliveryList.contains(deliveryFormDTO.getIngredient())) {
                int indexIngredient = findedDeliveryList.indexOf(formIngredient);
                int weight = findedDeliveryList.get(indexIngredient).getWeight();
                findedDeliveryList.get(indexIngredient).setWeight(weight + formIngredient.getWeight());
            } else {
                findedDeliveryList.add(formIngredient);
            }
        }
    }
}