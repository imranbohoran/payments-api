package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetPaymentTest {


    @Test
    void shouldGetPaymentForGivenPaymentId() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);

        GetPayment paymentFetcher = new GetPayment(paymentRepository);

        String paymentId = UUID.randomUUID().toString();
        Payment payment = mock(Payment.class);
        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        Payment fetchedPayment = paymentFetcher.getPayment(paymentId);

        assertThat(fetchedPayment).isEqualTo(payment);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNoPaymentFound() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);

        GetPayment paymentFetcher = new GetPayment(paymentRepository);

        String paymentId = UUID.randomUUID().toString();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentFetcher.getPayment(paymentId);
        });
    }

    @Test
    void shouldGetAllPayments() {
        PaymentRepository paymentRepository = mock(PaymentRepository.class);

        GetPayment paymentFetcher = new GetPayment(paymentRepository);

        Payment payment = mock(Payment.class);
        Payment payment1 = mock(Payment.class);
        when(paymentRepository.findAll()).thenReturn(asList(payment, payment1));

        List<Payment> fetchedPayments = paymentFetcher.getAllPayments();

        assertThat(fetchedPayments).isNotEmpty();
        assertThat(fetchedPayments).containsExactlyInAnyOrder(payment, payment1);
    }
}