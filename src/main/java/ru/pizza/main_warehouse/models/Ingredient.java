package ru.pizza.main_warehouse.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient implements Cloneable{
    private String title;
    private int weight;
    private int minWeight;
    private boolean isNew;
    private Status status;

    @Override
    public Ingredient clone() {
        try {
            Ingredient clone = (Ingredient) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    public enum Status {
        NOT_FOUND, SHORTAGE, ACCEPTABLE
    }
}
