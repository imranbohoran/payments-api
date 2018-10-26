package com.tib.payments.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.String.format;

@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    private int version;
    private String organisationId;
    private PaymentCharge paymentCharge;
    private Money amount;
    private PaymentParticipant beneficiary;
    private PaymentParticipant debtor;
    private String endToEndReference;
    private ForeignExchange foreignExchange;
    private Long numericReference;
    private String paymentId;
    private String purpose;
    private PaymentScheme scheme;
    private PaymentType type;
    private LocalDateTime processingDate;
    private String reference;
    private SchemeSubType schemeSubType;
    private SchemeType schemeType;
    private PaymentParticipant sponsor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public PaymentCharge getPaymentCharge() {
        return paymentCharge;
    }

    public void setPaymentCharge(PaymentCharge paymentCharge) {
        this.paymentCharge = paymentCharge;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public PaymentParticipant getDebtor() {
        return debtor;
    }

    public void setDebtor(PaymentParticipant debtor) {
        this.debtor = debtor;
    }

    public String getEndToEndReference() {
        return endToEndReference;
    }

    public void setEndToEndReference(String endToEndReference) {
        this.endToEndReference = endToEndReference;
    }

    public ForeignExchange getForeignExchange() {
        return foreignExchange;
    }

    public void setForeignExchange(ForeignExchange foreignExchange) {
        this.foreignExchange = foreignExchange;
    }

    public Long getNumericReference() {
        return numericReference;
    }

    public void setNumericReference(Long numericReference) {
        this.numericReference = numericReference;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public PaymentScheme getScheme() {
        return scheme;
    }

    public void setScheme(PaymentScheme scheme) {
        this.scheme = scheme;
    }

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public LocalDateTime getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(LocalDateTime processingDate) {
        this.processingDate = processingDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public SchemeSubType getSchemeSubType() {
        return schemeSubType;
    }

    public void setSchemeSubType(SchemeSubType schemeSubType) {
        this.schemeSubType = schemeSubType;
    }

    public SchemeType getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(SchemeType schemeType) {
        this.schemeType = schemeType;
    }

    public PaymentParticipant getSponsor() {
        return sponsor;
    }

    public void setSponsor(PaymentParticipant sponsor) {
        this.sponsor = sponsor;
    }

    public PaymentParticipant getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(PaymentParticipant beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public String toString() {
        return "Payment{" +
            "id='" + id + '\'' +
            ", version=" + version +
            ", organisationId='" + organisationId + '\'' +
            ", paymentCharge=" + paymentCharge +
            ", amount=" + amount +
            ", beneficiary=" + beneficiary +
            ", debtor=" + debtor +
            ", endToEndReference='" + endToEndReference + '\'' +
            ", foreignExchange=" + foreignExchange +
            ", numericReference=" + numericReference +
            ", paymentId='" + paymentId + '\'' +
            ", purpose='" + purpose + '\'' +
            ", scheme=" + scheme +
            ", type=" + type +
            ", processingDate=" + processingDate +
            ", reference='" + reference + '\'' +
            ", schemeSubType=" + schemeSubType +
            ", schemeType=" + schemeType +
            ", sponsor=" + sponsor +
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
        Payment payment = (Payment) o;
        return version == payment.version &&
            Objects.equals(id, payment.id) &&
            Objects.equals(organisationId, payment.organisationId) &&
            Objects.equals(paymentCharge, payment.paymentCharge) &&
            Objects.equals(amount, payment.amount) &&
            Objects.equals(beneficiary, payment.beneficiary) &&
            Objects.equals(debtor, payment.debtor) &&
            Objects.equals(endToEndReference, payment.endToEndReference) &&
            Objects.equals(foreignExchange, payment.foreignExchange) &&
            Objects.equals(numericReference, payment.numericReference) &&
            Objects.equals(paymentId, payment.paymentId) &&
            Objects.equals(purpose, payment.purpose) &&
            scheme == payment.scheme &&
            type == payment.type &&
            Objects.equals(processingDate, payment.processingDate) &&
            Objects.equals(reference, payment.reference) &&
            schemeSubType == payment.schemeSubType &&
            schemeType == payment.schemeType &&
            Objects.equals(sponsor, payment.sponsor);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(id, version, organisationId, paymentCharge, amount, beneficiary, debtor, endToEndReference, foreignExchange, numericReference,
                paymentId, purpose, scheme, type, processingDate, reference, schemeSubType, schemeType, sponsor);
    }


    public Payment updatePayment(Payment payment) {

        if (this.id.equals(payment.getId())) {
            payment.setVersion(this.getVersion() + 1);
            return payment;
        }

        throw new IllegalArgumentException(
            format("Incorrect payment attempted to be updated. Payment with ID %s, attempted to be updated by Payment with Id %s",
                this.id, payment.getId()));
    }
}
