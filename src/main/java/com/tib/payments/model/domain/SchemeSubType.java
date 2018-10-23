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
}
