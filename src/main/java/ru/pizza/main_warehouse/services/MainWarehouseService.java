package ru.pizza.main_warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;
import ru.pizza.main_warehouse.models.Ingredient.Status;
import ru.pizza.main_warehouse.services.rest.RestMakerMenuService;
import ru.pizza.main_warehouse.services.rest.RestRestaurantService;
import ru.pizza.main_warehouse.utils.Utils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainWarehouseService {
    private final RestRestaurantService restRestaurantService;
    private final RestMakerMenuService restMakerMenuService;

    public List<Building> getBuildingsList() {
        List<Building> buildings = restRestaurantService.receiveBuildingList();
        addStatus(buildings);
        return buildings;
    }

    private void addStatus(List<Building> buildings) {
        List<Ingredient> makerMenuList = restMakerMenuService.receiveMenuIngredients();
        for (Building building : buildings) {
            List<Ingredient> makerMenuListCopy = Utils.cloneList(makerMenuList);
            for (Ingredient makerMenuIngredient : makerMenuListCopy) {
                boolean isLocated = false;

                for (Ingredient ingredientBuilding : building.getIngredientList()) {
                    if (makerMenuIngredient.getTitle().equals(ingredientBuilding.getTitle())) {
                        if (ingredientBuilding.getWeight() < makerMenuIngredient.getMinWeight()) {
                            makerMenuIngredient.setStatus(Status.SHORTAGE);
                        } else {
                            makerMenuIngredient.setStatus(Status.ACCEPTABLE);
                        }
                        makerMenuIngredient.setWeight(ingredientBuilding.getWeight());
                        isLocated = true;
                        break;
                    }
                }

                if (!isLocated) {
                    makerMenuIngredient.setStatus(Status.NOT_FOUND);
                    makerMenuIngredient.setNew(true);
                }
            }
            building.setIngredientList(makerMenuListCopy);
        }
    }
}