package com.tib.payments.model.actions;

import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.payload.PaymentPayload;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdatePayment {

    private final PaymentRepository paymentRepository;

    @Autowired
    public UpdatePayment(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void execute(PaymentPayload paymentUpdatePayload) {
        Optional<Payment> persistedPayment = paymentRepository.findById(paymentUpdatePayload.getId());

        if (persistedPayment.isPresent()) {
            paymentRepository.save(persistedPayment.get().updatePayment(paymentUpdatePayload.getMappedPayment()));
        } else {
            throw new IllegalArgumentException();
        }
    }
}
