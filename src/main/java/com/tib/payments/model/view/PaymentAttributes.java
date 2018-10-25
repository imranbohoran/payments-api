package com.tib.payments.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.Payment;

import java.time.format.DateTimeFormatter;

public class PaymentAttributes {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        this.foreignExchangeView = new ForeignExchangeView(payment.getForeignExchange());
        this.sponsorParty = new SponsorParty(payment.getSponsor());
    }

    @JsonProperty("amount")
    public String getAmount() {
        return this.payment.getAmount().display();
    }

    @JsonProperty("beneficiary_party")
    private ParticipantParty beneficiaryParty;

    @JsonProperty("charges_information")
    private ChargesInformation chargesInformation;

    @JsonProperty("currency")
    public String getCurrency() {
        return this.payment.getAmount().getCurrency().getCurrencyCode();
    }

    @JsonProperty("debtor_party")
    private ParticipantParty debtorParty;

    @JsonProperty("end_to_end_reference")
    public String getEndToEndReference() {
        return this.payment.getEndToEndReference();
    }

    @JsonProperty("fx")
    private ForeignExchangeView foreignExchangeView;

    @JsonProperty("numeric_reference")
    private Long getNumericReference() {
        return this.payment.getNumericReference();
    }

    @JsonProperty("payment_id")
    private String getPaymentId() {
        return this.payment.getPaymentId();
    }

    @JsonProperty("payment_purpose")
    private String getPaymentPurpose() {
        return this.payment.getPurpose();
    }

    @JsonProperty("payment_scheme")
    private String getPaymentScheme() {
        return this.payment.getScheme().name();
    }

    @JsonProperty("payment_type")
    private String getPaymentType() {
        return this.payment.getType().getName();
    }

    @JsonProperty("processing_date")
    private String getProcessingDate() {
        return this.payment.getProcessingDate().format(DATE_TIME_FORMATTER);
    }

    @JsonProperty("reference")
    private String getReference() {
        return this.payment.getReference();
    }

    @JsonProperty("scheme_payment_sub_type")
    private String getSchemePaymentSubType() {
        return this.payment.getSchemeSubType().getName();
    }

    @JsonProperty("scheme_payment_type")
    private String getSchemePaymentType() {
        return this.payment.getSchemeType().getName();
    }

    @JsonProperty("sponsor_party")
    private SponsorParty sponsorParty;

}
