package ru.pizza.main_warehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pizza.main_warehouse.domain.enums.Status;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.BuildingToRestaurantDTO;
import ru.pizza.main_warehouse.domain.models.IngredientStatusModel;
import ru.pizza.main_warehouse.services.MainWarehouseService;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.Delivery;
import ru.pizza.main_warehouse.domain.models.thymeleaf.empty.DeliveryEmptyModel;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/warehouses")
@SessionAttributes({"buildingStatisticMap", "delivery"})
@RequiredArgsConstructor
public class MainWarehouseController {
    private Delivery delivery;
    private final MainWarehouseService mainWarehouseService;

    @ModelAttribute("buildingStatisticMap")
    public Map<BuildingToRestaurantDTO, List<IngredientStatusModel>> buildingStatisticMap() {
        return mainWarehouseService.createBuildingStatisticMap();
    }

    @ModelAttribute("delivery")
    public Delivery deliveryList() {
        return delivery = new Delivery();
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("deliveryEmptyDTO", new DeliveryEmptyModel());
        return "index";
    }

    @PostMapping
    public String add(@ModelAttribute DeliveryEmptyModel deliveryEmptyModel) {
        delivery.addItem(deliveryEmptyModel);
        return "redirect:/warehouses";
    }
}