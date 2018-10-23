package com.tib.payments.model.domain;

import java.util.Objects;

public class PaymentParticipant {
    private String accountName;
    private String accountNumber;
    private String accountNumberCode;
    private String address;
    private String bankId;
    private String bankIdCode;
    private String participantName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumberCode() {
        return accountNumberCode;
    }

    public void setAccountNumberCode(String accountNumberCode) {
        this.accountNumberCode = accountNumberCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankIdCode() {
        return bankIdCode;
    }

    public void setBankIdCode(String bankIdCode) {
        this.bankIdCode = bankIdCode;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    @Override
    public String toString() {
        return "PaymentParticipant{" +
            "accountName='" + accountName + '\'' +
            ", accountNumber='" + accountNumber + '\'' +
            ", accountNumberCode='" + accountNumberCode + '\'' +
            ", address='" + address + '\'' +
            ", bankId='" + bankId + '\'' +
            ", bankIdCode='" + bankIdCode + '\'' +
            ", participantName='" + participantName + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentParticipant that = (PaymentParticipant) o;
        return Objects.equals(accountName, that.accountName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accountNumberCode, that.accountNumberCode) &&
            Objects.equals(address, that.address) &&
            Objects.equals(bankId, that.bankId) &&
            Objects.equals(bankIdCode, that.bankIdCode) &&
            Objects.equals(participantName, that.participantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, accountNumber, accountNumberCode, address, bankId, bankIdCode, participantName);
    }
}
