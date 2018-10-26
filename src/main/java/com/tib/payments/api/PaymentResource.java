package com.tib.payments.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.payload.PaymentPayload;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PaymentResource {

    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentResource(PaymentRepository paymentRepository) {this.paymentRepository = paymentRepository;}

    @PostMapping(value = "/v1/api/payments")
    public ResponseEntity<String> createPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        return createNewPayment(paymentCreatePayload);
    }


    @GetMapping(value = "/v1/api/payments")
    public ResponseEntity<List<PaymentPayload>> getPayments() {

        List<Payment> allPayments = paymentRepository.findAll();

        List<PaymentPayload> paymentPayloads = allPayments.stream().map(PaymentPayload::new).collect(Collectors.toList());
        return ResponseEntity.ok(paymentPayloads);
    }

    @GetMapping(value = "/v1/api/payments/{payment_id}")
    public ResponseEntity<PaymentPayload> getPayment(@PathVariable("payment_id") String paymentId) {

        Optional<PaymentPayload> paymentPayload = paymentRepository.findById(paymentId)
            .map(PaymentPayload::new);

        if (paymentPayload.isPresent()) {
            return ResponseEntity.ok(paymentPayload.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = "/v1/api/payments")
    public ResponseEntity updatePayment(@RequestBody PaymentPayload paymentUpdatePayload) {
        Optional<Payment> payment = paymentRepository.findById(paymentUpdatePayload.getId());

        if(payment.isPresent()) {
            Payment paymentToSave = payment.get();
            paymentRepository.save(paymentToSave.updatePayment(paymentUpdatePayload.getMappedPayment()));

            return ResponseEntity.noContent().build();
        }

        return createNewPayment(paymentUpdatePayload);
    }

    private ResponseEntity<String> createNewPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        Payment createdPayment = paymentRepository.save(paymentCreatePayload.createNewPayment());
        ObjectNode paymentIdNode = FACTORY.objectNode();
        paymentIdNode.put("paymentId", createdPayment.getId());

        return ResponseEntity
            .created(URI.create("/v1/api/payments/" + createdPayment.getId()))
            .body(paymentIdNode.toString());
    }

}
