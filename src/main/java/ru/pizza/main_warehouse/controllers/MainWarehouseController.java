package ru.pizza.main_warehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pizza.main_warehouse.domain.dto.response.thymeleaf.empty.DeliveryFormDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.dto.response.statistic.IngredientStatusDTO;
import ru.pizza.main_warehouse.domain.models.to_restaurant.DeliveryModel;
import ru.pizza.main_warehouse.services.MainWarehouseService;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/warehouses")
@SessionAttributes({"buildingStatisticMap", "deliverySendListDTO"})
@RequiredArgsConstructor
public class MainWarehouseController {
    private final MainWarehouseService mainWarehouseService;
    private final DeliveryModel deliveryModel = new DeliveryModel();

    @ModelAttribute("buildingStatisticMap")
    public Map<BuildingToRestaurantDTO, List<IngredientStatusDTO>> buildingStatisticMap() {
        return mainWarehouseService.createBuildingStatisticMap(
                mainWarehouseService.receiveBuildingList(), mainWarehouseService.receiveMenuIngredients());
    }
    @ModelAttribute("deliverySendListDTO")
    public List<DeliveryDTO> deliverySendListDTO() {
        return deliveryModel.getDeliveryList();
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("deliveryFormDTO", new DeliveryFormDTO());
        return "index";
    }

    @PostMapping
    public String add(@ModelAttribute DeliveryFormDTO deliveryFormDTO) {
        deliveryModel.add(deliveryFormDTO);
        return "redirect:/warehouses";
    }
}