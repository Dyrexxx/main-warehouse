package ru.pizza.main_warehouse.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Building implements Cloneable{
    private int id;
    private String name;
    private List<Ingredient> ingredientList;

    @Override
    public Building clone() {
        try {
            Building clone = (Building) super.clone();
            clone.setIngredientList(new ArrayList<>());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}