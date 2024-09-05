package ru.pizza.main_warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.domain.dto.request.from_maker_menu.IngredientFromMakerMenuDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.IngredientFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientForStatisticDTO;
import ru.pizza.main_warehouse.domain.enums.Status;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.mapper.BuildingMapper;
import ru.pizza.main_warehouse.mapper.IngredientMapper;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.Delivery;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainWarehouseService {
    private final RestTemplate restTemplate;
    private final BuildingMapper buildingMapper;
    private final IngredientMapper ingredientMapper;

    public Map<BuildingToRestaurantDTO, Status[]> createBuildingStatisticMap() {
        List<BuildingFromRestaurantDTO> buildingFromRestaurantDTOS = this.receiveBuildingList();
        List<IngredientFromMakerMenuDTO> makerMenuList = this.receiveMenuIngredients();

        return this.createBuildingStatisticMap(buildingFromRestaurantDTOS, makerMenuList);
    }

    private void isNotLocatedOrAddToMap(Map<BuildingToRestaurantDTO, Status[]> buildingStatisticMap,
                                        BuildingToRestaurantDTO building,
                                        IngredientFromMakerMenuDTO ingredientFromMakerMenuDTO,
                                        List<IngredientFromRestaurantDTO> ingredientFromRestaurantList) {
        boolean isLocated = false;
        for (IngredientFromRestaurantDTO ingredientFromRestaurantDTO : ingredientFromRestaurantList) {
            if (ingredientFromMakerMenuDTO.getTitle().equals(ingredientFromRestaurantDTO.getTitle())) {
                IngredientForStatisticDTO newIngredient = ingredientMapper.toIngredientForStatistic(ingredientFromRestaurantDTO);
                if (ingredientFromRestaurantDTO.getWeight() < ingredientFromMakerMenuDTO.getMinWeight()) {
                    buildingStatisticMap.get(building)[0].getIngredientForStatisticList().add(newIngredient);
                } else {
                    buildingStatisticMap.get(building)[1].getIngredientForStatisticList().add(newIngredient);
                }
                isLocated = true;
                break;
            }
        }
        if (!isLocated) {
            IngredientForStatisticDTO newIngredient = ingredientMapper.toIngredientForStatistic(ingredientFromMakerMenuDTO);
            newIngredient.setNew(true);
            buildingStatisticMap.get(building)[2].getIngredientForStatisticList().add(newIngredient);
        }
    }

    private Map<BuildingToRestaurantDTO, Status[]> createBuildingStatisticMap(List<BuildingFromRestaurantDTO> buildingFromRestaurantDTOS,
                                                                              List<IngredientFromMakerMenuDTO> makerMenuList) {
        Map<BuildingToRestaurantDTO, Status[]> buildingMap = new LinkedHashMap<>();
        for (BuildingFromRestaurantDTO buildingFromRestaurantDTO : buildingFromRestaurantDTOS) {
            BuildingToRestaurantDTO buildingDTO = buildingMapper.toBuildingKeyModel(buildingFromRestaurantDTO);
            buildingMap.put(buildingDTO, Status.values());
            for (IngredientFromMakerMenuDTO ingredientFromRestaurantDTOMakerMenu : makerMenuList) {
                this.isNotLocatedOrAddToMap(
                        buildingMap,
                        buildingDTO,
                        ingredientFromRestaurantDTOMakerMenu,
                        buildingFromRestaurantDTO.getIngredientList());
            }
        }
        return buildingMap;
    }

    private List<IngredientFromMakerMenuDTO> receiveMenuIngredients() {
        String url = "http://MAKER-MENU/maker/ingredients";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, IngredientFromMakerMenuDTO[].class).getBody()));
    }

    private List<BuildingFromRestaurantDTO> receiveBuildingList() {
        String url = "http://RESTAURANT/restaurant/api/buildings";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, BuildingFromRestaurantDTO[].class).getBody()));

    }

    public void sendNewDeliveryList(Delivery delivery) {
        String url = "http://RESTAURANT/restaurant/api/deliveries";
        restTemplate.postForEntity(url, delivery, Delivery.class);
    }
}