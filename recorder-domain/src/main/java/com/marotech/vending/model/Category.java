package com.marotech.vending.model;


import com.marotech.vending.util.CategoryComparator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Category {
    FOOT("Foot"), BIKE("Bike"), CAR("Small Car"),
    PICK_UP_TRUCK("Pick Up Truck"), BIG_TRUCK("Big Truck"), BOAT("Boat");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Category> getSortedValues() {
        List<Category> cats = Arrays.asList(values());
        Collections.sort(cats, new CategoryComparator());
        return cats;
    }

    public static Category fromString(String text) {
        for (Category c : Category.values()) {
            if (c.name.equalsIgnoreCase(text)) {
                return c;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + text);
    }
}
