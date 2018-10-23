package com.tib.payments.model.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ForeignExchange {
    private String contractReference;
    private BigDecimal exchangeRate;
    private Money originalAmount;

    public String getContractReference() {
        return contractReference;
    }

    public void setContractReference(String contractReference) {
        this.contractReference = contractReference;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Money getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Money originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Override
    public String toString() {
        return "ForeignExchange{" +
            "contractReference='" + contractReference + '\'' +
            ", exchangeRate=" + exchangeRate +
            ", originalAmount=" + originalAmount +
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
        ForeignExchange that = (ForeignExchange) o;
        return Objects.equals(contractReference, that.contractReference) &&
            Objects.equals(exchangeRate, that.exchangeRate) &&
            Objects.equals(originalAmount, that.originalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractReference, exchangeRate, originalAmount);
    }
}
