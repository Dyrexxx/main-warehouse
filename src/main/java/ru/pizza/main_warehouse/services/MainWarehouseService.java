package ru.pizza.main_warehouse.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.domain.dto.request.from_maker_menu.IngredientFromMakerMenuDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.IngredientFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;
import ru.pizza.main_warehouse.domain.enums.Status;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.statistic.IngredientStatusDTO;
import ru.pizza.main_warehouse.mapper.BuildingMapper;
import ru.pizza.main_warehouse.mapper.IngredientMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainWarehouseService {
    private final BuildingMapper buildingMapper;
    private final IngredientMapper ingredientMapper;
    private final RestTemplate restTemplate;

    /**
     * Получает список всех ингредиентов из мкс maker-menu. Получает список всех ресторанов
     * и их складов из мкс restaurant. Обрабатывает в формате (Нет в наличии, нехватка, достаточно)
     *
     * @return Возвращает Map данных собраных с двух микросервисов (maker-menu, restaurant)
     */
    public Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> createBuildingStatisticMap(List<BuildingFromRestaurantDTO> buildingFromRestaurantList,
                                                                                              List<IngredientFromMakerMenuDTO> ingredientFromMakerMenuList) {
        Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> buildingMap = new LinkedHashMap<>();
        for (BuildingFromRestaurantDTO buildingFromRestaurantDTO : buildingFromRestaurantList) {
            BuildingToRestaurantDTO buildingDTO = buildingMapper.toBuildingKeyModel(buildingFromRestaurantDTO);
            buildingMap.put(buildingDTO, createIngredientStatusModelList());
            for (IngredientFromMakerMenuDTO ingredientFromRestaurantDTOMakerMenu : ingredientFromMakerMenuList) {
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
     *
     * @return Возвращает скелет для заполнения List
     */
    private List<IngredientStatusDTO> createIngredientStatusModelList() {
        List<IngredientStatusDTO> ingredientStatusDTOList = new ArrayList<>();
        for (Status status : Status.values()) {
            ingredientStatusDTOList.add(new IngredientStatusDTO(status));
        }
        return ingredientStatusDTOList;
    }

    /**
     * Заполняет Map соединяя данные с двух микросервисов (maker-menu, restaurant)
     *
     * @param buildingStatisticMap         итоговая Map куда сливаются данные двух мкс (maker-menu, restaurant)
     * @param building                     ресторан в который заполняются данные
     * @param ingredientFromMakerMenuDTO   ингредиент из мкс maker-menu
     * @param ingredientFromRestaurantList ингредиент из мкс restaurant
     */
    private void isNotLocatedOrAddToMap(Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> buildingStatisticMap,
                                        BuildingToRestaurantDTO building,
                                        IngredientFromMakerMenuDTO ingredientFromMakerMenuDTO,
                                        List<IngredientFromRestaurantDTO> ingredientFromRestaurantList) {
        boolean isLocated = false;
        for (IngredientFromRestaurantDTO ingredientFromRestaurantDTO : ingredientFromRestaurantList) {
            if (ingredientFromMakerMenuDTO.getTitle().equals(ingredientFromRestaurantDTO.getTitle())) {
                IngredientToRestaurantDTO newIngredient = ingredientMapper.toRestaurant(ingredientFromRestaurantDTO);
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
            IngredientToRestaurantDTO newIngredient = ingredientMapper.toRestaurant(ingredientFromMakerMenuDTO);
            newIngredient.setNew(true);
            buildingStatisticMap.get(building).get(2).getIngredientList().add(newIngredient);
        }
    }
    /**
     * @return Получает данные из мкс (maker-menu)
     */
    public List<IngredientFromMakerMenuDTO> receiveMenuIngredients() {
        String url = "http://MAKER-MENU/maker/ingredients";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, IngredientFromMakerMenuDTO[].class).getBody()));
    }

    /**
     * @return получение данных ресторанов из мкс restaurant
     */
    public List<BuildingFromRestaurantDTO> receiveBuildingList() {
        String url = "http://RESTAURANT/restaurant/api/buildings";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, BuildingFromRestaurantDTO[].class).getBody()));

    }

    /**
     * Отправляет данных собранной доставки в рестораны. В мкс restaurant
     *
     * @param deliveryDTO собранная корзина доставки
     */
    public void sendNewDeliveryList(List<DeliveryDTO> deliveryDTO) {
        String url = "http://RESTAURANT/restaurant/api/deliveries";
        restTemplate.postForEntity(url, deliveryDTO, DeliveryDTO.class);
    }
}