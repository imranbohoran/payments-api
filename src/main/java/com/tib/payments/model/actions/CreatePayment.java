package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePayment {

    private final PaymentRepository paymentRepository;

    @Autowired
    public CreatePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment execute(Payment newPayment) {
        return paymentRepository.save(newPayment);
    }
}
