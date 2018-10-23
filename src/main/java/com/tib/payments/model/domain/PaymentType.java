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
}
