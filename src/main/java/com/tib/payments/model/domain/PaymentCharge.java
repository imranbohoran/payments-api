package com.tib.payments.model.domain;

import java.util.List;
import java.util.Objects;

public class PaymentCharge {

    private String bearerCode;
    private List<Money> senderCharges;
    private Money receiverCharge;

    public String getBearerCode() {
        return bearerCode;
    }

    public void setBearerCode(String bearerCode) {
        this.bearerCode = bearerCode;
    }

    public List<Money> getSenderCharges() {
        return senderCharges;
    }

    public void setSenderCharges(List<Money> senderCharges) {
        this.senderCharges = senderCharges;
    }

    public Money getReceiverCharge() {
        return receiverCharge;
    }

    public void setReceiverCharge(Money receiverCharge) {
        this.receiverCharge = receiverCharge;
    }

    @Override
    public String toString() {
        return "PaymentCharge{" +
            "bearerCode='" + bearerCode + '\'' +
            ", senderCharges=" + senderCharges +
            ", receiverCharge=" + receiverCharge +
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
        PaymentCharge that = (PaymentCharge) o;
        return Objects.equals(bearerCode, that.bearerCode) &&
            Objects.equals(senderCharges, that.senderCharges) &&
            Objects.equals(receiverCharge, that.receiverCharge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bearerCode, senderCharges, receiverCharge);
    }
}
