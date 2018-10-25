package com.tib.payments.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.Payment;

public class PaymentView {

    @JsonIgnore
    private Payment payment;

    public PaymentView(Payment payment) {
        this.payment = payment;
        this.attributes = new PaymentAttributes(payment);
        this.type = "Payment";
    }

    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    public String getId() {
        return this.payment.getId();
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return this.payment.getVersion();
    }

    @JsonProperty("organisation_id")
    public String getOrganisationId() {
        return this.payment.getOrganisationId();
    }

    @JsonProperty("attributes")
    private PaymentAttributes attributes;
}
