package ru.pizza.main_warehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;
import ru.pizza.main_warehouse.services.MainWarehouseService;
import ru.pizza.main_warehouse.services.rest.RestRestaurantService;
import ru.pizza.main_warehouse.utils.Utils;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/warehouses")
@RequiredArgsConstructor
@SessionAttributes({"buildingList", "deliveryList", "orderIngredientList"})
public class MainWarehouseController {
    private final MainWarehouseService mainWarehouseService;
    private final RestRestaurantService restRestaurantService;


    @ModelAttribute("newIngredient")
    public Ingredient ingredient() {
        return new Ingredient();
    }

    @ModelAttribute("orderIngredientList")
    public List<Ingredient> order() {
        return new ArrayList<>();
    }

    @ModelAttribute("deliveryList")
    public List<Building> delivery() {
        return new ArrayList<>();
    }

    @ModelAttribute("buildingList")
    public List<Building> buildingList() {
       return mainWarehouseService.getBuildingsList();
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/{id}")
    public String getWarehouseId(@PathVariable int id,
                                 @ModelAttribute("buildingList") List<Building> buildingList,
                                 Model model) {
        model.addAttribute("building",
                Utils.findByIdObjectInList(buildingList, id));
        return "warehouse_form_id";
    }

    @PostMapping("/{id}/form-delivery")
    public String processingUpdateIngredient(@PathVariable int id,
                                             @ModelAttribute("buildingList") List<Building> buildingList,
                                             Model model) {
        Building building = Utils.findByIdObjectInList(buildingList, id);
        model.addAttribute("building", building);
        return "redirect:/warehouses/" + id;
    }

    @PostMapping("/order/{id}/add-delivery")
    public String processingAddDelivery(@PathVariable int id,
                                        @ModelAttribute("orderIngredientList") List<Ingredient> orderIngredientList,
                                        @ModelAttribute("buildingList") List<Building> buildingList,
                                        @ModelAttribute("deliveryList") List<Building> deliveryList) {
        Building b = Utils.findByIdObjectInList(buildingList, id);
        Building newBuilding = new Building() {{
            assert b != null;
            setId(b.getId());
            setTitle(b.getTitle());
            setIngredientList(Utils.cloneList(orderIngredientList));
        }};
        deliveryList.add(newBuilding);
        orderIngredientList.clear();
        return index();
    }

    @PostMapping("/order/{id}/add")
    public String processingAddIngredientToOrderIngredientList(@PathVariable int id,
                                          @ModelAttribute("orderIngredientList") List<Ingredient> orderIngredientList,
                                          @ModelAttribute("newIngredient") Ingredient newIngredient) {
        orderIngredientList.add(newIngredient);
        return "redirect:/warehouses/" + id;
    }

    @PostMapping("/delivery")
    @ResponseBody
    public List<Building> sendDelivery(SessionStatus sessionStatus, @ModelAttribute("deliveryList") List<Building> deliveryList) {
        sessionStatus.setComplete();
        return restRestaurantService.sendNewDeliveryList(deliveryList);
    }
}