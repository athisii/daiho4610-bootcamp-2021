package com.tothenew.entities.order;

public enum PaymentMethod {
    CARD("CARD"),
    CASH("CASH");
    private final String type;

    PaymentMethod(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
