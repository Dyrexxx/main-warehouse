package ru.pizza.main_warehouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;
import ru.pizza.main_warehouse.models.Ingredient.Status;
import ru.pizza.main_warehouse.utils.DimaUtils;

import java.util.List;
import java.util.Objects;

@Service
public class MainWarehouseService {
    private final RestTemplate restTemplate;


    @Autowired
    public MainWarehouseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Building> getBuildingsList() {
        List<Building> buildings = getRestaurantsFromWarehouseList();
        addStatus(buildings);
        return buildings;
    }

    public List<Building> sendDelivery(List<Building> buildings) {
        return restTemplate.postForObject("http://localhost:8085/dodo/new-delivery", buildings, List.class);
    }

    private void addStatus(List<Building> buildings) {
        List<Ingredient> makerMenuList = this.getIngredientsFromMakerMenuList();
        for (Building building : buildings) {

            List<Ingredient> makerMenuListCopy = DimaUtils.cloneList(makerMenuList);
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

    private List<Building> getRestaurantsFromWarehouseList() {
        String url2 = "http://localhost:8085/dodo/buildings";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url2, Building[].class).getBody()));
    }

    private List<Ingredient> getIngredientsFromMakerMenuList() {
        String url1 = "http://localhost:8083/maker/ingredients";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url1, Ingredient[].class).getBody()));
    }
}