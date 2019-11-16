package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CreatePaymentTest {

    private PaymentRepository paymentRepository;

    private CreatePayment createPayment;

    @BeforeEach
    void setup() {
        paymentRepository = mock(PaymentRepository.class);
        createPayment = new CreatePayment(paymentRepository);
    }

    @Test
    void shouldCreateNewPayment() {
        Payment payment = mock(Payment.class);
        Payment newPayment = mock(Payment.class);

        when(paymentRepository.save(payment)).thenReturn(newPayment);
        Payment createdPayment = createPayment.execute(payment);

        assertThat(createdPayment).isEqualTo(newPayment);
        verify(paymentRepository).save(payment);
    }
}