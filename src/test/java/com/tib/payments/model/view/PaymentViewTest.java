package com.tib.payments.model.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tib.payments.model.domain.Payment;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static com.tib.payments.TestData.createNewPayment;

class PaymentViewTest {

    @Test
    void shouldGenerateExpectedJsonForPaymentView() throws Exception {
        Payment payment = createNewPayment("4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43");

        PaymentView paymentView = new PaymentView(payment);

        String expectedJson = new String(
            Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("test-data/sample-payment.json")).toURI())));

        ObjectMapper objectMapper = new ObjectMapper();
        String generatedJson = objectMapper.writeValueAsString(paymentView);

        JSONAssert.assertEquals(expectedJson, generatedJson, false);
    }

}