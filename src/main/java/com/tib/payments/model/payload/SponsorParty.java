package com.tib.payments.model.payload;

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
        return this.paymentParticipant.getAccountNumber();
    }

    @JsonProperty("account_number")
    public void setAccountNumber(String accountNumber) {
        this.paymentParticipant.setAccountNumber(accountNumber);
    }

    @JsonProperty("bank_id")
    public String getBankId() {
        return this.paymentParticipant.getBankId();
    }

    @JsonProperty("bank_id")
    public void setBankId(String bankId) {
        this.paymentParticipant.setBankId(bankId);
    }

    @JsonProperty("bank_id_code")
    public String getBankIdCode() {
        return this.paymentParticipant.getBankIdCode();
    }

    @JsonProperty("bank_id_code")
    public void setBankIdCode(String bankIdCode) {
        this.paymentParticipant.setBankIdCode(bankIdCode);
    }

    @JsonIgnore
    public PaymentParticipant getNewSponsor() {
        return paymentParticipant;
    }
}
