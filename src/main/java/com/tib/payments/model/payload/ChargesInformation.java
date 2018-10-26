package com.tib.payments.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.Money;
import com.tib.payments.model.domain.PaymentCharge;

import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChargesInformation {

    @JsonIgnore
    private PaymentCharge paymentCharge;

    @JsonProperty("sender_charges")
    private List<Charge> senderCharges;

    @JsonIgnore
    private String receiverChargeAmount;

    @JsonIgnore
    private String receiverChargeCurrency;

    public ChargesInformation() {
        this.paymentCharge = new PaymentCharge();
    }

    public ChargesInformation(PaymentCharge paymentCharge) {
        this.paymentCharge = paymentCharge;
        this.senderCharges = this.paymentCharge.getSenderCharges()
            .stream()
            .map(sc -> new Charge(sc.display(), sc.getCurrency().getCurrencyCode()))
            .collect(Collectors.toList());
    }

    @JsonProperty("bearer_code")
    public String getBearerCode() {
        return this.paymentCharge.getBearerCode();
    }

    @JsonProperty("bearer_code")
    public void setBearerCode(String bearerCode) {
        this.paymentCharge.setBearerCode(bearerCode);

    }

    @JsonProperty("receiver_charges_amount")
    public String getReceiverChargeAmount() {
        return this.paymentCharge.getReceiverCharge().display();
    }


    @JsonProperty("receiver_charges_currency")
    public String getReceiverChargeCurrency() {
        return this.paymentCharge.getReceiverCharge().getCurrency().getCurrencyCode();
    }

    public void setSenderCharges(List<Charge> senderCharges) {
        this.senderCharges = senderCharges;
    }

    @JsonProperty("receiver_charges_amount")
    public void setReceiverChargeAmount(String receiverChargeAmount) {
        this.receiverChargeAmount = receiverChargeAmount;
    }

    @JsonProperty("receiver_charges_currency")
    public void setReceiverChargeCurrency(String receiverChargeCurrency) {
        this.receiverChargeCurrency = receiverChargeCurrency;
    }

    public static class Charge {

        private String amount;
        private String currencyCode;

        public Charge() {}

        public Charge(String amount, String currencyCode) {
            this.amount = amount;
            this.currencyCode = currencyCode;
        }

        @JsonProperty("amount")
        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @JsonProperty("currency")
        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCoe(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        @Override
        public String toString() {
            return "Charge{" +
                "amount='" + amount + '\'' +
                ", currencyCoe='" + currencyCode + '\'' +
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
            Charge charge = (Charge) o;
            return Objects.equals(amount, charge.amount) &&
                Objects.equals(currencyCode, charge.currencyCode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(amount, currencyCode);
        }
    }

    @JsonIgnore
    public PaymentCharge getNewPaymentCharge() {
        paymentCharge.setReceiverCharge(
            Money.moneyValue(receiverChargeAmount, Currency.getInstance(receiverChargeCurrency)));

        paymentCharge.setSenderCharges(
            senderCharges.stream()
                .map(charge -> Money.moneyValue(charge.getAmount(), Currency.getInstance(charge.getCurrencyCode())))
                .collect(Collectors.toList()));

        return paymentCharge;
    }

}
