package com.tib.payments.model.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    CREDIT("Credit");

    private String name;

    PaymentType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public static PaymentType getPaymentTypeFor(String name) {
        for(PaymentType type : PaymentType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        throw new RuntimeException("Unknown payment type name: "+ name);
    }
}
