package ru.pizza.main_warehouse.utils;

import ru.pizza.main_warehouse.models.Building;
import ru.pizza.main_warehouse.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class DimaUtils {
    public static Building findById(List<Building> list, int id) {
        for (Building building : list) {
            if (building.getId() == id) {
                return building;
            }
        }
        return null;
    }
    public static List<Ingredient> cloneList(List<Ingredient> list) {
        List<Ingredient> clone = new ArrayList<>(list.size());
        for (Ingredient item : list) clone.add(item.clone());
        return clone;
    }
}
