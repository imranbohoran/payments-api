package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;

public class CreatePayment {

    private final PaymentRepository paymentRepository;

    public CreatePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment execute(Payment newPayment) {
        return paymentRepository.save(newPayment);
    }
}
