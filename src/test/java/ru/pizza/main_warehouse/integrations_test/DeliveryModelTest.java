package ru.pizza.main_warehouse.integrations_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.pizza.main_warehouse.domain.dto.response.thymeleaf.empty.DeliveryFormDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.IngredientToRestaurantDTO;
import ru.pizza.main_warehouse.domain.models.to_restaurant.DeliveryModel;
import ru.pizza.main_warehouse.mapper.DeliveryMapper;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DeliveryModelTest {
    @Mock
    private DeliveryMapper mockDeliveryMapper;
    private DeliveryModel deliveryModelUnderTest;

    @BeforeEach
    void setUp() {
        deliveryModelUnderTest = new DeliveryModel();
    }

    @Test
    void testAddInEmptyList() {
        List<DeliveryDTO> expectedDeliveryFormList = new ArrayList<>();
        DeliveryFormDTO deliveryForm =
                new DeliveryFormDTO(new BuildingToRestaurantDTO(1, "dodo1"),
                        new IngredientToRestaurantDTO("Молоко", 5, false));
        deliveryModelUnderTest.add(deliveryForm);
        Mockito.when(mockDeliveryMapper.toDeliveryDTO(deliveryForm))
                .thenReturn(new DeliveryDTO(new BuildingToRestaurantDTO(1, "dodo1")));
        expectedDeliveryFormList.add(mockDeliveryMapper.toDeliveryDTO(deliveryForm));
        assert expectedDeliveryFormList.equals(deliveryModelUnderTest.getDeliveryList());
    }

    @Test
    void testAddRepetitiveElement() {
        //Добавление одинаковых ингредиентов в один ресторан
        List<DeliveryDTO> expectedResult =
                List.of(new DeliveryDTO(
                        new BuildingToRestaurantDTO(1, "dodo1"),
                        List.of(new IngredientToRestaurantDTO("Молоко", 100, false))));

        DeliveryFormDTO deliveryForm1 =
                new DeliveryFormDTO(new BuildingToRestaurantDTO(1, "dodo1"),
                        new IngredientToRestaurantDTO("Молоко", 20, false));
        DeliveryFormDTO deliveryForm2 =
                new DeliveryFormDTO(new BuildingToRestaurantDTO(1, "dodo1"),
                        new IngredientToRestaurantDTO("Молоко", 80, false));
        deliveryModelUnderTest.add(deliveryForm1);
        deliveryModelUnderTest.add(deliveryForm2);
        assert expectedResult.equals(deliveryModelUnderTest.getDeliveryList());
    }

    @Test
    void testAddTwoRestaurant() {
        List<DeliveryDTO> expectedDeliveryFormList =
                List.of(new DeliveryDTO(
                                new BuildingToRestaurantDTO(1, "dodo1"),
                                List.of(
                                        new IngredientToRestaurantDTO("Молоко", 40, false)
                                )),
                        new DeliveryDTO(new BuildingToRestaurantDTO(2, "dodo2"),
                                List.of(
                                        new IngredientToRestaurantDTO("Молоко", 1000, true)
                                ))
                );
        DeliveryFormDTO add1 = new DeliveryFormDTO(
                new BuildingToRestaurantDTO(1, "dodo1"),
                new IngredientToRestaurantDTO("Молоко", 40, false)
        );
        DeliveryFormDTO add2 = new DeliveryFormDTO(
                new BuildingToRestaurantDTO(2, "dodo2"),
                new IngredientToRestaurantDTO("Молоко", 1000, true)
        );

        deliveryModelUnderTest.add(add1);
        deliveryModelUnderTest.add(add2);
        assert expectedDeliveryFormList.equals(deliveryModelUnderTest.getDeliveryList());
    }
}
