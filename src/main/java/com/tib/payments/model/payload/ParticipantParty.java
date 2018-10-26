package com.tib.payments.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.PaymentParticipant;

public class ParticipantParty {

    @JsonIgnore
    private PaymentParticipant paymentParticipant;

    public ParticipantParty() {
        this.paymentParticipant = new PaymentParticipant();
    }

    public ParticipantParty(PaymentParticipant paymentParticipant) {
        this.paymentParticipant = paymentParticipant;
    }

    @JsonProperty("account_name")
    public String getAccountName() {
        return this.paymentParticipant.getAccountName();
    }

    @JsonProperty("account_name")
    public void setAccountName(String accountName) {
        this.paymentParticipant.setAccountName(accountName);
    }

    @JsonProperty("account_number")
    public String getAccountNumber() {
        return this.paymentParticipant.getAccountNumber();
    }

    @JsonProperty("account_number")
    public void setAccountNumber(String accountNumber) {
        this.paymentParticipant.setAccountNumber(accountNumber);
    }

    @JsonProperty("account_number_code")
    public String getAccountNumberCode() {
        return this.paymentParticipant.getAccountNumberCode();
    }

    @JsonProperty("account_number_code")
    public void setAccountNumberCode(String accountNumberCode) {
        this.paymentParticipant.setAccountNumberCode(accountNumberCode);
    }

    @JsonProperty("account_type")
    public Integer getAccountType() {
        return paymentParticipant.getAccountType();
    }

    @JsonProperty("account_type")
    public void setAccountType(Integer accountType) {
        this.paymentParticipant.setAccountType(accountType);
    }

    @JsonProperty("address")
    public String getAddress() {
        return this.paymentParticipant.getAddress();
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.paymentParticipant.setAddress(address);
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

    @JsonProperty("name")
    public String getName() {
        return this.paymentParticipant.getParticipantName();
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.paymentParticipant.setParticipantName(name);
    }

    @JsonIgnore
    public PaymentParticipant getNewPaymentParticipant() {
        return paymentParticipant;
    }
}
