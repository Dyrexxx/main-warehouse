package ru.pizza.main_warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;
import ru.pizza.main_warehouse.services.MainWarehouseService;
import ru.pizza.main_warehouse.utils.DimaUtils;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/warehouses")
@SessionAttributes({"buildingList", "deliveryList", "order"})
public class MainWarehouseController {
    private List<Ingredient> order;
    private final MainWarehouseService mainWarehouseService;
    private List<Building> buildingList;

    @Autowired
    public MainWarehouseController(MainWarehouseService mainWarehouseService) {
        this.mainWarehouseService = mainWarehouseService;
    }


    @ModelAttribute("newIngredient")
    public Ingredient ingredient() {
        return new Ingredient();
    }

    @ModelAttribute("order")
    public List<Ingredient> order() {
        return this.order = new ArrayList<>();
    }

    @ModelAttribute("deliveryList")
    public List<Building> delivery() {
        return new ArrayList<>();
    }

    @ModelAttribute("buildingList")
    public List<Building> buildingList() {
        return buildingList = mainWarehouseService.getBuildingsList();
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/{id}")
    public String getWarehouseId(@PathVariable int id, Model model) {
        model.addAttribute("building",
                DimaUtils.findById(buildingList, id));
        return "warehouse_form_id";
    }

    @PostMapping("/{id}/form-delivery")
    public String processingUpdateIngredient(@PathVariable int id, Model model) {
        Building building = DimaUtils.findById(buildingList, id);
        model.addAttribute("building", building);
        return "redirect:/warehouses/" + id;
    }

    @PostMapping("/order/{id}/add-delivery")
    public String processingAddDelivery(@PathVariable int id, @ModelAttribute List<Building> deliveryList) {
        Building b = DimaUtils.findById(buildingList, id);
        Building newBuilding = new Building() {{
            setId(b.getId());
            setName(b.getName());
            setIngredientList(DimaUtils.cloneList(order));
        }};
        deliveryList.add(newBuilding);
        order.clear();
        return index();
    }

    @PostMapping("/order/{id}/add")
    public String processingAddIngredient(@PathVariable int id, @ModelAttribute Ingredient newIngredient) {
        order.add(newIngredient);
        return "redirect:/warehouses/" + id;
    }

    @PostMapping("/delivery")
    @ResponseBody
    public ResponseEntity sendDelivery(SessionStatus sessionStatus, @ModelAttribute List<Building> deliveryList) {
        sessionStatus.setComplete();
        return mainWarehouseService.sendDelivery(deliveryList);
    }
}