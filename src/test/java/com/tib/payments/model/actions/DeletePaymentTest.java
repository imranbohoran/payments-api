package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeletePaymentTest {

    @Test
    void shouldDeletePaymentForGivenId() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        Payment payment = mock(Payment.class);
        String paymentId = UUID.randomUUID().toString();

        DeletePayment deletePayment = new DeletePayment(paymentRepository);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        deletePayment.execute(paymentId);

        verify(paymentRepository).delete(payment);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForUnknownPayment() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        String paymentId = UUID.randomUUID().toString();

        DeletePayment deletePayment = new DeletePayment(paymentRepository);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            deletePayment.execute(paymentId);
        });
    }
}