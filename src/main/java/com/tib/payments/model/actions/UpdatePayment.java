package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdatePayment {

    private final PaymentRepository paymentRepoistory;

    @Autowired
    public UpdatePayment(PaymentRepository paymentRepository) {
        this.paymentRepoistory = paymentRepository;
    }

    public void execute(Payment payment) {
        paymentRepoistory.save(payment);
    }
}
