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

    public static SchemeType getSchemeTypeFor(String name) {
        for (SchemeType schemeType: SchemeType.values()) {
            if (schemeType.getName().equals(name)) {
                return schemeType;
            }
        }

        throw new RuntimeException("Unknown scheme type for name: "+ name);
    }
}
