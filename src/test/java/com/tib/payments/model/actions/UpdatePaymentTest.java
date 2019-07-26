package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UpdatePaymentTest {

    private PaymentRepository paymentRepository;

    private UpdatePayment updatePayment;

    @BeforeEach
    void setup() {
        paymentRepository = mock(PaymentRepository.class);
        updatePayment = new UpdatePayment(paymentRepository);
    }

    @Test
    public void shouldUpdateProvidedPayment() {
        Payment payment = mock(Payment.class);

        updatePayment.execute(payment);

        verify(paymentRepository).save(payment);
    }
}