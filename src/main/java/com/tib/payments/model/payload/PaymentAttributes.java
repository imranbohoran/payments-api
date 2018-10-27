package com.tib.payments.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.Money;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.domain.PaymentScheme;
import com.tib.payments.model.domain.PaymentType;
import com.tib.payments.model.domain.SchemeSubType;
import com.tib.payments.model.domain.SchemeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

public class PaymentAttributes {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @JsonIgnore
    private Payment payment;

    @JsonIgnore
    private String amount;

    @JsonIgnore
    private String currency;

    public PaymentAttributes() {
        this.payment = new Payment();
    }

    public PaymentAttributes(Payment payment) {
        this.payment = payment;
        this.beneficiaryParty = new ParticipantParty(payment.getBeneficiary());
        this.debtorParty = new ParticipantParty(payment.getDebtor());
        this.chargesInformation = new ChargesInformation(payment.getPaymentCharge());
        this.foreignExchangeView = new ForeignExchangePayload(payment.getForeignExchange());
        this.sponsorParty = new SponsorParty(payment.getSponsor());
    }

    @JsonProperty("amount")
    public String getAmount() {
        if(this.payment.getAmount() != null) {
            return this.payment.getAmount().display();
        } else {
            return this.amount;
        }
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }


    @JsonProperty("beneficiary_party")
    private ParticipantParty beneficiaryParty;

    @JsonProperty("charges_information")
    private ChargesInformation chargesInformation;

    @JsonProperty("currency")
    public String getCurrency() {
        if (this.payment.getAmount() != null) {
            return this.payment.getAmount().getCurrency().getCurrencyCode();
        } else  {
            return this.currency;
        }
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("debtor_party")
    private ParticipantParty debtorParty;

    @JsonProperty("end_to_end_reference")
    public String getEndToEndReference() {
        return this.payment.getEndToEndReference();
    }

    @JsonProperty("end_to_end_reference")
    public void setEndToEndReference(String endToEndReference) {
        this.payment.setEndToEndReference(endToEndReference);
    }

    @JsonProperty("fx")
    private ForeignExchangePayload foreignExchangeView;

    @JsonProperty("numeric_reference")
    public Long getNumericReference() {
        return this.payment.getNumericReference();
    }

    @JsonProperty("numeric_reference")
    public void setNumericReference(Long numericReference) {
        this.payment.setNumericReference(numericReference);
    }

    @JsonProperty("payment_id")
    public String getPaymentId() {
        return this.payment.getPaymentId();
    }

    @JsonProperty("payment_id")
    public void setPaymentId(String paymentId) {
        this.payment.setPaymentId(paymentId);
    }

    @JsonProperty("payment_purpose")
    public String getPaymentPurpose() {
        return this.payment.getPurpose();
    }

    @JsonProperty("payment_purpose")
    public void setPaymentPurpose(String paymentPurpose) {
        this.payment.setPurpose(paymentPurpose);
    }

    @JsonProperty("payment_scheme")
    public String getPaymentScheme() {
        return this.payment.getScheme().name();
    }

    @JsonProperty("payment_scheme")
    public void setPaymentScheme(String paymentScheme) {
        this.payment.setScheme(PaymentScheme.valueOf(paymentScheme));
    }

    @JsonProperty("payment_type")
    public String getPaymentType() {
        return this.payment.getType().getName();
    }

    @JsonProperty("payment_type")
    public void setPaymentType(String paymentType) {
        this.payment.setType(PaymentType.getPaymentTypeFor(paymentType));
    }

    @JsonProperty("processing_date")
    public String getProcessingDate() {
        return this.payment.getProcessingDate().format(DATE_TIME_FORMATTER);
    }

    @JsonProperty("processing_date")
    public void setProcessingDate(String date) {
        this.payment.setProcessingDate(LocalDate.parse(date, DATE_TIME_FORMATTER).atStartOfDay());
    }

    @JsonProperty("reference")
    public String getReference() {
        return this.payment.getReference();
    }

    @JsonProperty("reference")
    public void setReference(String reference) {
        this.payment.setReference(reference);
    }

    @JsonProperty("scheme_payment_sub_type")
    public String getSchemePaymentSubType() {
        return this.payment.getSchemeSubType().getName();
    }

    @JsonProperty("scheme_payment_sub_type")
    public void setSchemePaymentSubType(String schemePaymentSubType) {
        this.payment.setSchemeSubType(SchemeSubType.getSchemeSubTypeFor(schemePaymentSubType));
    }

    @JsonProperty("scheme_payment_type")
    public String getSchemePaymentType() {
        return this.payment.getSchemeType().getName();
    }

    @JsonProperty("scheme_payment_type")
    public void setSchemePaymentType(String schemePaymentType) {
        this.payment.setSchemeType(SchemeType.getSchemeTypeFor(schemePaymentType));
    }

    @JsonProperty("sponsor_party")
    public SponsorParty sponsorParty;

    @JsonIgnore
    public Payment getNewPayment() {
        payment.setAmount(Money.moneyValue(amount, Currency.getInstance(currency)));
        payment.setBeneficiary(beneficiaryParty.getNewPaymentParticipant());
        payment.setSponsor(sponsorParty.getNewSponsor());
        payment.setPaymentCharge(chargesInformation.getNewPaymentCharge());
        payment.setForeignExchange(foreignExchangeView.getnewForeignExchange());
        payment.setDebtor(debtorParty.getNewPaymentParticipant());

        return payment;
    }
}
