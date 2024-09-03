package ru.pizza.main_warehouse.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.models.Ingredient;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestMakerMenuService {
    private final RestTemplate restTemplate;

    public List<Ingredient> receiveMenuIngredients() {
        String url = "http://MAKER-MENU/maker/ingredients";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, Ingredient[].class).getBody()));
    }
}
