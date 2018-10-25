package com.tib.payments.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.PaymentParticipant;

public class SponsorParty {

    @JsonIgnore
    private PaymentParticipant paymentParticipant;

    public SponsorParty() {
        this.paymentParticipant = new PaymentParticipant();
    }

    public SponsorParty(PaymentParticipant paymentParticipant) {
        this.paymentParticipant = paymentParticipant;
    }

    @JsonProperty("account_number")
    public String getAccountNumber() {
        return paymentParticipant.getAccountNumber();
    }

    @JsonProperty("bank_id")
    public String getBankId() {
        return this.paymentParticipant.getBankId();
    }

    @JsonProperty("bank_id_code")
    public String getBankIdCode() {
        return this.paymentParticipant.getBankIdCode();
    }
}
