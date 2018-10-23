package com.tib.payments.model.domain;

public enum  SchemeType {
    IMMEDIATE_PAYMENT("ImmediatePayment");

    private String name;
    SchemeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
