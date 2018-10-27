package com.tib.payments.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.payload.PaymentPayload;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import static com.tib.payments.api.ApiPaths.PAYMENTS_PATH_SINGLE_RESOURCE;
import static com.tib.payments.api.ApiPaths.PAYMENTS_RESOURCE_PATH;
import static com.tib.payments.model.payload.PaymentPayload.PATH_VARIABLE_PAYMENT_ID;
import static com.tib.payments.model.payload.PaymentPayload.RESPONSE_PAYLOAD_PAYMENT_ID;

@RestController
public class PaymentResource {

    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    private final PaymentRepository paymentRepository;

    private final String applicationDomain;

    @Autowired
    public PaymentResource(@Value("${application.domain}") String applicationDomain, PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.applicationDomain = applicationDomain;
    }

    @PostMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity<String> createPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        return createNewPayment(paymentCreatePayload);
    }


    @GetMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity<List<PaymentPayload>> getPayments() {

        List<Payment> allPayments = paymentRepository.findAll();

        List<PaymentPayload> paymentPayloads = allPayments.stream().map(PaymentPayload::new).collect(Collectors.toList());
        return ResponseEntity.ok(paymentPayloads);
    }

    @GetMapping(value = PAYMENTS_PATH_SINGLE_RESOURCE)
    public ResponseEntity<PaymentPayload> getPayment(@PathVariable(PATH_VARIABLE_PAYMENT_ID) String paymentId) {

        Optional<PaymentPayload> paymentPayload = paymentRepository.findById(paymentId)
            .map(PaymentPayload::new);

        if (paymentPayload.isPresent()) {
            return ResponseEntity.ok(paymentPayload.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity updatePayment(@RequestBody PaymentPayload paymentUpdatePayload) {
        Optional<Payment> payment = paymentRepository.findById(paymentUpdatePayload.getId());

        if(payment.isPresent()) {
            Payment paymentToSave = payment.get();
            paymentRepository.save(paymentToSave.updatePayment(paymentUpdatePayload.getMappedPayment()));

            return ResponseEntity.noContent().build();
        }

        return createNewPayment(paymentUpdatePayload);
    }

    @DeleteMapping(value = PAYMENTS_PATH_SINGLE_RESOURCE)
    public ResponseEntity deletePayment(@PathVariable(PATH_VARIABLE_PAYMENT_ID) String paymentId) {
        paymentRepository.deleteById(paymentId);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<String> createNewPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        Payment createdPayment = paymentRepository.save(paymentCreatePayload.createNewPayment());
        ObjectNode paymentIdNode = FACTORY.objectNode();
        paymentIdNode.put(RESPONSE_PAYLOAD_PAYMENT_ID, createdPayment.getId());

        return ResponseEntity
            .created(URI.create(applicationDomain + PAYMENTS_RESOURCE_PATH + createdPayment.getId()))
            .body(paymentIdNode.toString());
    }

}
