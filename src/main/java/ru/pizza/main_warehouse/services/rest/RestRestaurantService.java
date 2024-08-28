package ru.pizza.main_warehouse.services.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.pizza.main_warehouse.models.Building;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestRestaurantService {
    private final RestTemplate restTemplate;

    public List<Building> receiveBuildingList() {
        String url = "http://localhost:8085/dodo/buildings";
        return List.of(Objects.requireNonNull(restTemplate.getForEntity(url, Building[].class).getBody()));

    }

    public List<Building> sendNewDeliveryList(List<Building> newDeliveryList) {
        String url = "http://localhost:8085/dodo/new-delivery";
        System.out.println(newDeliveryList);
        return restTemplate.postForObject(url, newDeliveryList, newDeliveryList.getClass());
    }

}
