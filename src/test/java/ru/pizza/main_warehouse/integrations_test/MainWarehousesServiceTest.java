package ru.pizza.main_warehouse.integrations_test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pizza.main_warehouse.domain.dto.request.from_maker_menu.IngredientFromMakerMenuDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.BuildingFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.request.from_restaurant.IngredientFromRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.statistic.IngredientStatusDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;
import ru.pizza.main_warehouse.domain.enums.Status;
import ru.pizza.main_warehouse.services.MainWarehouseService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MainWarehousesServiceTest {
    @Autowired
    private MainWarehouseService mainWarehouseService;

    @Test
    void testCreateBuildingStatisticMap() {
        // Получение ресторанов из мкс Restaurant
        List<BuildingFromRestaurantDTO> receiveRestaurantBuildings = Arrays.asList(
                new BuildingFromRestaurantDTO(1, "dodo1", Arrays.asList(
                        new IngredientFromRestaurantDTO("Молоко", 200),
                        new IngredientFromRestaurantDTO("Тесто", 300),
                        new IngredientFromRestaurantDTO("Банан", 1000),
                        new IngredientFromRestaurantDTO("Гранат", 5000)
                )),
                new BuildingFromRestaurantDTO(2, "dodo2", Arrays.asList(
                        new IngredientFromRestaurantDTO("Молоко", 100),
                        new IngredientFromRestaurantDTO("Тесто", 100),
                        new IngredientFromRestaurantDTO("Банан", 100),
                        new IngredientFromRestaurantDTO("Персик", 50)
                ))
        );

        // Получение всех задействующих ингредиентов из мкс Maker-menu
        List<IngredientFromMakerMenuDTO> receiveMakerMenuIngredients = Arrays.asList(
                new IngredientFromMakerMenuDTO("Молоко", 600),
                new IngredientFromMakerMenuDTO("Тесто", 600),
                new IngredientFromMakerMenuDTO("Банан", 600),
                new IngredientFromMakerMenuDTO("Гранат", 600),
                new IngredientFromMakerMenuDTO("Персик", 600),
                new IngredientFromMakerMenuDTO("Сливки", 600)
        );

        // То что должно получиться в выводе
        final Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> expectedResult = new LinkedHashMap<>();
        expectedResult.put(new BuildingToRestaurantDTO(1, "dodo1"),
                List.of(new IngredientStatusDTO(Status.SHORTAGE,
                                new ArrayList<>(Arrays.asList(
                                        new IngredientToRestaurantDTO("Молоко", 200, false),
                                        new IngredientToRestaurantDTO("Тесто", 300, false)))),
                        new IngredientStatusDTO(Status.ACCEPTABLE,
                                new ArrayList<>(Arrays.asList(
                                        new IngredientToRestaurantDTO("Банан", 1000, false),
                                        new IngredientToRestaurantDTO("Гранат", 5000, false)))),
                        new IngredientStatusDTO(Status.NOT_FOUND,
                                new ArrayList<>(Arrays.asList(
                                        new IngredientToRestaurantDTO("Персик", 0, true),
                                        new IngredientToRestaurantDTO("Сливки", 0, true))))
                ));
        expectedResult.put(new BuildingToRestaurantDTO(2, "dodo2"),
                List.of(new IngredientStatusDTO(Status.SHORTAGE,
                                new ArrayList<>(Arrays.asList(
                                        new IngredientToRestaurantDTO("Молоко", 100, false),
                                        new IngredientToRestaurantDTO("Тесто", 100, false),
                                        new IngredientToRestaurantDTO("Банан", 100, false),
                                        new IngredientToRestaurantDTO("Персик", 50, false)))),
                        new IngredientStatusDTO(Status.ACCEPTABLE, new ArrayList<>()),
                        new IngredientStatusDTO(Status.NOT_FOUND,
                                new ArrayList<>(Arrays.asList(
                                        new IngredientToRestaurantDTO("Гранат", 0, true),
                                        new IngredientToRestaurantDTO("Сливки", 0, true))))
                ));



        Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> actualResult =
                mainWarehouseService.createBuildingStatisticMap(receiveRestaurantBuildings, receiveMakerMenuIngredients);
        //Проверка на истину
        assertEquals(expectedResult, actualResult);
    }
}
