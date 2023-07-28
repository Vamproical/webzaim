package com.mimoun.webzaim.model;

import java.util.Arrays;

public enum PaymentDictionary {
    ONE("1", 1),
    ZERO("0", 2),
    A("A", 3),
    TWO("2", 4),
    THREE("3", 5),
    X("X", 0);

    private final String value;
    private final int weight;


    PaymentDictionary(String value, int weight) {
        this.weight = weight;
        this.value = value;
    }

    public static PaymentDictionary findByValue(String val) {
        return Arrays.stream(values())
                     .filter(v -> v.value.equals(val))
                     .findFirst()
                     .orElse(X);
    }

    public int getWeight() {
        return weight;
    }

    public String getValue() {
        return value;
    }
}
