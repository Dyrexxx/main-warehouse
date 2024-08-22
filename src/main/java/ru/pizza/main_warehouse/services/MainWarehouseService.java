package ru.pizza.main_warehouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;
import ru.pizza.main_warehouse.models.Ingredient.Status;
import ru.pizza.main_warehouse.utils.DimaUtils;

import java.util.List;

@Service
public class MainWarehouseService {

    private final RestTemplate restTemplate;
    private static final String url1 = "http://localhost:8083/maker/ingredients";
    private static final String url2 = "http://localhost:8085/dodo/buildings";

    @Autowired
    public MainWarehouseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Building> getBuildingsList() {
        List<Building> buildings = getRestaurantsWarehouseList();
        addStatus(buildings);
        return buildings;
    }

    private void addStatus(List<Building> buildings) {
        List<Ingredient> makerMenuList = ingredientsMakerMenuList();
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

    public ResponseEntity sendDelivery(List<Building> buildings) {
        return restTemplate.postForEntity("http://localhost:8085/dodo/new-delivery", buildings, ResponseEntity.class);
    }

    private List<Building> getRestaurantsWarehouseList() {
        List<Building> buildings = List.of(restTemplate.getForEntity(url2,
                Building[].class).getBody());
        return buildings;
    }

    private List<Ingredient> ingredientsMakerMenuList() {
        Ingredient[] ingredients = restTemplate.getForEntity(url1,
                Ingredient[].class).getBody();
        return List.of(ingredients);
    }
}