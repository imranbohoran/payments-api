package com.tib.payments.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.Payment;

import java.util.UUID;

public class PaymentPayload {

    @JsonIgnore
    private Payment payment;

    public PaymentPayload() {
        this.payment = new Payment();
    }

    public PaymentPayload(Payment payment) {
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

    @JsonProperty("id")
    public void setId(String id) {
        this.payment.setId(id);
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return this.payment.getVersion();
    }

    @JsonProperty("organisation_id")
    public String getOrganisationId() {
        return this.payment.getOrganisationId();
    }

    @JsonProperty("organisation_id")
    public void setOrganisationId(String organisationId) {
        this.payment.setOrganisationId(organisationId);
    }


    @JsonProperty("attributes")
    private PaymentAttributes attributes;

    @JsonIgnore
    public Payment createNewPayment() {
        Payment newPayment = attributes.getNewPayment();
        newPayment.setId(UUID.randomUUID().toString());
        newPayment.setVersion(0);
        newPayment.setOrganisationId(getOrganisationId());
        return newPayment;
    }

    @JsonIgnore
    public Payment getMappedPayment() {
        Payment newPayment = attributes.getNewPayment();
        newPayment.setId(getId());
        newPayment.setVersion(getVersion());
        newPayment.setOrganisationId(getOrganisationId());
        return newPayment;
    }
}
