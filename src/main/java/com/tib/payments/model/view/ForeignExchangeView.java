package com.tib.payments.model.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tib.payments.model.domain.ForeignExchange;

import java.math.BigDecimal;

public class ForeignExchangeView {

    @JsonIgnore
    private ForeignExchange foreignExchange;

    @JsonIgnore
    private String amount;

    @JsonIgnore
    private String currency;

    public ForeignExchangeView() {
        this.foreignExchange = new ForeignExchange();
    }

    public ForeignExchangeView(ForeignExchange foreignExchange) {
        this.foreignExchange = foreignExchange;
    }

    @JsonProperty("contract_reference")
    private String getContractReference() {
        return this.foreignExchange.getContractReference();
    }

    @JsonProperty("exchange_rate")
    private String getExchangeRate() {
        return this.foreignExchange.getExchangeRate().toPlainString();
    }

    @JsonProperty("original_amount")
    private String getOriginalAmount() {
        return this.foreignExchange.getOriginalAmount().display();
    }

    @JsonProperty("original_currency")
    private String getOriginalCurrency() {
        return this.foreignExchange.getOriginalAmount().getCurrency().getCurrencyCode();
    }

    @JsonProperty("contract_reference")
    private void setContractReference(String contractReference) {
        this.foreignExchange.setContractReference(contractReference);
    }

    @JsonProperty("exchange_rate")
    private void setExchangeRate(String exchangeRate) {
        this.foreignExchange.setExchangeRate(new BigDecimal(exchangeRate));
    }

    @JsonProperty("original_amount")
    private void setOriginalAmount(String originalAmount) {
        this.amount = originalAmount;
    }

    @JsonProperty("original_currency")
    private void setOriginalCurrency(String currency) {
        this.currency = currency;
    }
}
