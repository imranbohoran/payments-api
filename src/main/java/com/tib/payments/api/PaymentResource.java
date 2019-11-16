package com.tib.payments.api;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tib.payments.model.actions.CreatePayment;
import com.tib.payments.model.actions.DeletePayment;
import com.tib.payments.model.actions.GetPayment;
import com.tib.payments.model.actions.UpdatePayment;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.payload.PaymentPayload;
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
import java.util.stream.Collectors;

import static com.tib.payments.api.ApiPaths.PAYMENTS_PATH_SINGLE_RESOURCE;
import static com.tib.payments.api.ApiPaths.PAYMENTS_RESOURCE_PATH;
import static com.tib.payments.model.payload.PaymentPayload.PATH_VARIABLE_PAYMENT_ID;
import static com.tib.payments.model.payload.PaymentPayload.RESPONSE_PAYLOAD_PAYMENT_ID;

@RestController
public class PaymentResource {

    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    private final CreatePayment createPayment;

    private final UpdatePayment updatePayment;

    private final GetPayment paymentFetcher;

    private final DeletePayment deletePayment;

    private final String applicationDomain;

    @Autowired
    public PaymentResource(@Value("${application.domain}") String applicationDomain,
                           CreatePayment createPayment, UpdatePayment updatePayment,
                           GetPayment paymentFetcher, DeletePayment deletePayment) {
        this.applicationDomain = applicationDomain;
        this.createPayment = createPayment;
        this.updatePayment = updatePayment;
        this.paymentFetcher = paymentFetcher;
        this.deletePayment = deletePayment;
    }

    @PostMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity<String> createPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        return createNewPayment(paymentCreatePayload);
    }


    @GetMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity<List<PaymentPayload>> getPayments() {

        List<Payment> allPayments = paymentFetcher.getAllPayments();

        List<PaymentPayload> paymentPayloads = allPayments.stream().map(PaymentPayload::new).collect(Collectors.toList());
        return ResponseEntity.ok(paymentPayloads);
    }

    @GetMapping(value = PAYMENTS_PATH_SINGLE_RESOURCE)
    public ResponseEntity<PaymentPayload> getPayment(@PathVariable(PATH_VARIABLE_PAYMENT_ID) String paymentId) {

        try {
            PaymentPayload paymentPayload = new PaymentPayload(paymentFetcher.getPayment(paymentId));
            return ResponseEntity.ok(paymentPayload);
        } catch (IllegalArgumentException invalidArgs) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = PAYMENTS_RESOURCE_PATH)
    public ResponseEntity updatePayment(@RequestBody PaymentPayload paymentUpdatePayload) {
        try {
            updatePayment.execute(paymentUpdatePayload);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException invalidArgs) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(value = PAYMENTS_PATH_SINGLE_RESOURCE)
    public ResponseEntity deletePayment(@PathVariable(PATH_VARIABLE_PAYMENT_ID) String paymentId) {
        try {
            deletePayment.execute(paymentId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException invalidArgs) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<String> createNewPayment(@RequestBody PaymentPayload paymentCreatePayload) {
        Payment createdPayment = createPayment.execute(paymentCreatePayload.createNewPayment());

        ObjectNode paymentIdNode = FACTORY.objectNode();
        paymentIdNode.put(RESPONSE_PAYLOAD_PAYMENT_ID, createdPayment.getId());

        return ResponseEntity
            .created(URI.create(applicationDomain + PAYMENTS_RESOURCE_PATH + createdPayment.getId()))
            .body(paymentIdNode.toString());
    }

}
