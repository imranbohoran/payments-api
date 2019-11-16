package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;

import java.util.Optional;

public class DeletePayment {

    private final PaymentRepository paymentRepository;

    public DeletePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void execute(String paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (payment.isPresent()) {
            paymentRepository.delete(payment.get());
        } else {
            throw new IllegalArgumentException();
        }
    }
}
