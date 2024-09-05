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
import ru.pizza.main_warehouse.domain.models.IngredientStatusModel;
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

    /**
     * Получает список всех ингредиентов из мкс maker-menu. Получает список всех ресторанов
     * и их складов из мкс restaurant. Обрабатывает в формате (Нет в наличии, нехватка, достаточно)
     *
     * @return Возвращает Map данных собраных с двух микросервисов (maker-menu, restaurant)
     */
    public Map<BuildingToRestaurantDTO, List<IngredientStatusModel>> createBuildingStatisticMap() {
        List<BuildingFromRestaurantDTO> buildingFromRestaurantDTOS = this.receiveBuildingList();
        List<IngredientFromMakerMenuDTO> makerMenuList = this.receiveMenuIngredients();
        Map<BuildingToRestaurantDTO, List<IngredientStatusModel>> buildingMap = new LinkedHashMap<>();
        for (BuildingFromRestaurantDTO buildingFromRestaurantDTO : buildingFromRestaurantDTOS) {
            BuildingToRestaurantDTO buildingDTO = buildingMapper.toBuildingKeyModel(buildingFromRestaurantDTO);
            buildingMap.put(buildingDTO, createIngredientStatusModelList());
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

    /**
     * Создает скелет для обработки данных с двух микросервисов
     * @return Возвращает скелет для заполнения List
     */
    private List<IngredientStatusModel> createIngredientStatusModelList() {
        List<IngredientStatusModel> ingredientStatusModelList = new ArrayList<>();
        for(Status status : Status.values()) {
           ingredientStatusModelList.add(new IngredientStatusModel(status));
        }
        return ingredientStatusModelList;
    }

    /**
     * Заполняет Map соединяя данные с двух микросервисов (maker-menu, restaurant)
     *
     * @param buildingStatisticMap итоговая Map куда сливаются данные двух мкс (maker-menu, restaurant)
     * @param building ресторан в который заполняются данные
     * @param ingredientFromMakerMenuDTO ингредиент из мкс maker-menu
     * @param ingredientFromRestaurantList ингредиент из мкс restaurant
     */
    private void isNotLocatedOrAddToMap(Map<BuildingToRestaurantDTO, List<IngredientStatusModel>> buildingStatisticMap,
                                        BuildingToRestaurantDTO building,
                                        IngredientFromMakerMenuDTO ingredientFromMakerMenuDTO,
                                        List<IngredientFromRestaurantDTO> ingredientFromRestaurantList) {
        boolean isLocated = false;
        for (IngredientFromRestaurantDTO ingredientFromRestaurantDTO : ingredientFromRestaurantList) {
            if (ingredientFromMakerMenuDTO.getTitle().equals(ingredientFromRestaurantDTO.getTitle())) {
                IngredientForStatisticDTO newIngredient = ingredientMapper.toIngredientForStatistic(ingredientFromRestaurantDTO);
                if (ingredientFromRestaurantDTO.getWeight() < ingredientFromMakerMenuDTO.getMinWeight()) {
                    buildingStatisticMap.get(building).get(0).getIngredientList().add(newIngredient);
                } else {
                    buildingStatisticMap.get(building).get(1).getIngredientList().add(newIngredient);
                }
                isLocated = true;
                break;
            }
        }
        if (!isLocated) {
            IngredientForStatisticDTO newIngredient = ingredientMapper.toIngredientForStatistic(ingredientFromMakerMenuDTO);
            newIngredient.setNew(true);
            buildingStatisticMap.get(building).get(2).getIngredientList().add(newIngredient);
        }
    }

    /**
     *
     * @return Получает данные из мкс (maker-menu)
     */
    private List<IngredientFromMakerMenuDTO> receiveMenuIngredients() {
        String url = "http://MAKER-MENU/maker/ingredients";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, IngredientFromMakerMenuDTO[].class).getBody()));
    }

    /**
     *
     * @return получение данных ресторанов из мкс restaurant
     */
    private List<BuildingFromRestaurantDTO> receiveBuildingList() {
        String url = "http://RESTAURANT/restaurant/api/buildings";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, BuildingFromRestaurantDTO[].class).getBody()));

    }

    /**
     * Отправляет данных собранной доставки в рестораны. В мкс restaurant
     *
     * @param delivery собранная корзина доставки
     */
    public void sendNewDeliveryList(Delivery delivery) {
        String url = "http://RESTAURANT/restaurant/api/deliveries";
        restTemplate.postForEntity(url, delivery, Delivery.class);
    }
}