package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.payload.PaymentPayload;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdatePaymentTest {

    private PaymentRepository paymentRepository;

    private UpdatePayment updatePayment;

    @BeforeEach
    void setup() {
        paymentRepository = mock(PaymentRepository.class);
        updatePayment = new UpdatePayment(paymentRepository);
    }

    @Test
    void shouldUpdateProvidedPayment() {
        PaymentPayload paymentPayload = mock(PaymentPayload.class);
        Payment paymentWithUpdates = mock(Payment.class);
        Payment payment = mock(Payment.class);

        String paymentId = UUID.randomUUID().toString();
        when(paymentPayload.getId()).thenReturn(paymentId);
        when(paymentPayload.getMappedPayment()).thenReturn(paymentWithUpdates);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(payment.updatePayment(paymentWithUpdates)).thenReturn(payment);

        updatePayment.execute(paymentPayload);

        verify(payment).updatePayment(paymentWithUpdates);
        verify(paymentRepository).save(payment);
    }

    @Test

    void shouldThrowExceptionIfPaymentDoesNotExist() {
        PaymentPayload paymentPayload = mock(PaymentPayload.class);

        String paymentId = UUID.randomUUID().toString();
        when(paymentPayload.getId()).thenReturn(paymentId);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            updatePayment.execute(paymentPayload);
        });
    }
}