package ru.pizza.main_warehouse.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.pizza.main_warehouse.domain.dto.response.to_restaurant.DeliveryDTO;
import ru.pizza.main_warehouse.services.MainWarehouseService;

import java.util.List;


@Controller
@RequestMapping("/warehouses/send")
@SessionAttributes({"deliverySendListDTO"})
@RequiredArgsConstructor
public class SendToRestaurantController {
    private final MainWarehouseService mainWarehouseService;

    @GetMapping
    public String index(@ModelAttribute("deliverySendListDTO") List<DeliveryDTO> deliveryDTO, Model model) {
        model.addAttribute("deliverySendListDTO", deliveryDTO);
        System.out.println(deliveryDTO);
        return "send";
    }

    @PostMapping
    public String sendToRestaurant(SessionStatus sessionStatus, @ModelAttribute("deliverySendListDTO") List<DeliveryDTO> deliveryDTO) {
        mainWarehouseService.sendNewDeliveryList(deliveryDTO);
        sessionStatus.setComplete();
        return "redirect:/warehouses";
    }
}
