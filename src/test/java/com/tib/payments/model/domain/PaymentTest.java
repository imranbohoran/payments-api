package com.tib.payments.model.domain;

import com.tib.payments.TestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {

    @Test
    void updatePaymentShouldUpdateExistingPaymentWithNewOne() {
        String existingId = UUID.randomUUID().toString();
        Payment existingPayment = TestData.createNewPayment(existingId);

        Payment paymentWithUpdates = TestData.createNewPayment(existingId);
        Money updatedAmount = Money.moneyValue("444.44", Currency.getInstance(Locale.UK));
        paymentWithUpdates.setAmount(updatedAmount);

        Payment updatedPayment = existingPayment.updatePayment(paymentWithUpdates);

        assertThat(updatedPayment.getId()).isEqualTo(existingPayment.getId());
        assertThat(updatedPayment.getVersion()).isEqualTo(existingPayment.getVersion() + 1);
        assertThat(updatedPayment.getAmount()).isEqualTo(updatedAmount);
    }

    @Test
    void updatingPaymentShouldThrowExceptionIfUpdatingWithIncorrectPaymentWithDifferentId() {
        Payment existingPayment = TestData.createNewPayment();
        Payment supposedlyExistingPaymentWithUpdates = TestData.createNewPayment();

        Money updatedAmount = Money.moneyValue("444.44", Currency.getInstance(Locale.UK));
        supposedlyExistingPaymentWithUpdates.setAmount(updatedAmount);

        Assertions.assertThrows(IllegalArgumentException.class,
            () -> existingPayment.updatePayment(supposedlyExistingPaymentWithUpdates));
    }
}