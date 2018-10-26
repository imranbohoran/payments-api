package com.tib.payments.model.domain;

public enum SchemeSubType {
    INTERNET_BANKING("InternetBanking");

    private String name;

    SchemeSubType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SchemeSubType getSchemeSubTypeFor(String name) {
        for (SchemeSubType schemeSubType: SchemeSubType.values()) {
            if (schemeSubType.getName().equals(name)) {
                return schemeSubType;
            }
        }
        throw new RuntimeException("Unknown scheme sub type for name: "+ name);
    }
}
