package com.tib.payments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tib.payments.model.payload.PaymentPayload;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.tib.payments.persistence")
@SpringBootTest
@WebAppConfiguration()
class PaymentsIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    void shouldCompleteAllPaymentOperations() throws Exception {
        String requestPayloadFirstPayment = new String(Files.readAllBytes(Paths.get(
            Objects.requireNonNull(PaymentsIntegrationTest.class.getClassLoader().getResource("test-data/sample-create-payment.json")).toURI())));

        String requestPayloadSecondPayment = new String(Files.readAllBytes(Paths.get(
            Objects.requireNonNull(PaymentsIntegrationTest.class.getClassLoader().getResource("test-data/sample-create-payment-2.json")).toURI())));

        // POST- Creation Payment
        String firstPaymentPostResponse = mockMvc.perform(post("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(requestPayloadFirstPayment))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Map<String, String> firstPaymentCreateResponse = objectMapper.readValue(firstPaymentPostResponse, new TypeReference<Map<String, String>>() {});

        String paymentId = firstPaymentCreateResponse.get("paymentId");

        assertThat(paymentId).isNotNull();

        // GET - Get the created payment
        String firstPayment = mockMvc.perform(get("/v1/api/payments/" + paymentId)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type").value("Payment"))
            .andExpect(jsonPath("$.id").value(paymentId))
            .andExpect(jsonPath("$.version").value(0))
            .andExpect(jsonPath("$.organisation_id").value("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb"))
            .andExpect(jsonPath("$.attributes.amount").value("100.21"))
            .andExpect(jsonPath("$.attributes.currency").value("GBP"))
            .andReturn().getResponse().getContentAsString();

        // POST - Create second payment
        String secondPaymentPostResponse = mockMvc.perform(post("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(requestPayloadSecondPayment))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

        Map<String, String> secondPaymentCreateResponse = objectMapper.readValue(secondPaymentPostResponse, new TypeReference<Map<String, String>>() {});

        String paymentIdSecondPayment = secondPaymentCreateResponse.get("paymentId");

        // GET ALL
        mockMvc.perform(get("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].type").value("Payment"))
            .andExpect(jsonPath("$.[0].id").value(paymentId))
            .andExpect(jsonPath("$.[0].version").value(0))
            .andExpect(jsonPath("$.[0].organisation_id").value("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb"))
            .andExpect(jsonPath("$.[0].attributes.amount").value("100.21"))
            .andExpect(jsonPath("$.[0].attributes.currency").value("GBP"))
            .andExpect(jsonPath("$.[1].type").value("Payment"))
            .andExpect(jsonPath("$.[1].id").value(paymentIdSecondPayment))
            .andExpect(jsonPath("$.[1].version").value(0))
            .andExpect(jsonPath("$.[1].organisation_id").value("c60b7681-5ca2-4634-8d4b-1d3581864c10"))
            .andExpect(jsonPath("$.[1].attributes.amount").value("400.21"))
            .andExpect(jsonPath("$.[1].attributes.currency").value("GBP"));

        // PUT operation on 1 payment
        PaymentPayload paymentPayload = objectMapper.readValue(firstPayment, PaymentPayload.class);
        String firstPaymentNewOrganisationId = "5bf36188-8226-4a2b-8e77-fb6b7f39a512";
        paymentPayload.setOrganisationId(firstPaymentNewOrganisationId);

        String updatedSecondPayment = objectMapper.writeValueAsString(paymentPayload);
        mockMvc.perform(put("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(updatedSecondPayment))
            .andExpect(status().isNoContent());

        // GET - the payment that was modified
        mockMvc.perform(get("/v1/api/payments/" + paymentId)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.organisation_id").value(firstPaymentNewOrganisationId))
            .andReturn().getResponse().getContentAsString();

        // DELETE - payments
        mockMvc.perform(delete("/v1/api/payments/" + paymentId)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        mockMvc.perform(delete("/v1/api/payments/" + paymentIdSecondPayment)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // GET - deleted payments
        mockMvc.perform(get("/v1/api/payments/" + paymentId)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());

        mockMvc.perform(get("/v1/api/payments/" + paymentIdSecondPayment)
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound());

    }
}
