package ru.pizza.main_warehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.Delivery;
import ru.pizza.main_warehouse.services.MainWarehouseService;


@Controller
@RequestMapping("/warehouses/send")
@SessionAttributes({"delivery"})
@RequiredArgsConstructor
public class SendToRestaurantController {
    private final MainWarehouseService mainWarehouseService;

    @GetMapping
    public String index(@ModelAttribute("delivery") Delivery delivery, Model model) {
        model.addAttribute("delivery", delivery);
        return "send";
    }

    @PostMapping
    public String sendToRestaurant(@ModelAttribute("delivery") Delivery delivery) {
        mainWarehouseService.sendNewDeliveryList(delivery);
        return "redirect:/warehouses";
    }
}
