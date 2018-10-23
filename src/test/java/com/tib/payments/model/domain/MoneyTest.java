package com.tib.payments.model.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    @Test
    void shouldCreateMoneyValueFromString() {
        Money money = Money.moneyValue("10.44", Currency.getInstance(Locale.UK));

        assertThat(money).isEqualTo(new Money(BigDecimal.valueOf(10.44), Currency.getInstance(Locale.UK)));
    }

    @Test
    void shouldDisplayWithDecimalPointsAsPerCurrencyLocale() {
        Money moneyUK = Money.moneyValue("11.44", Currency.getInstance(Locale.UK));
        Money moneyJapan = Money.moneyValue("11.44", Currency.getInstance(Locale.JAPAN));

        assertThat(moneyUK.display()).isEqualTo("11.44");
        assertThat(moneyJapan.display()).isEqualTo("11");
    }

}