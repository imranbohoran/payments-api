package com.tib.payments.api;

import com.tib.payments.TestData;
import com.tib.payments.model.domain.Money;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.persistence.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PaymentResource.class)
@ComponentScan(basePackages = "com.tib.payments")
class PaymentResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PaymentRepository paymentRepository;

    @Captor
    ArgumentCaptor<Payment> paymentArgumentCaptor;

    @Test
    void shouldCreateNewPayment() throws Exception {
        String requestPayload = new String(Files.readAllBytes(Paths.get(
            Objects.requireNonNull(PaymentResourceTest.class.getClassLoader().getResource("test-data/sample-create-payment.json")).toURI())));

        Payment newPayment = mock(Payment.class);

        when(paymentRepository.save(paymentArgumentCaptor.capture())).thenReturn(newPayment);
        when(newPayment.getId()).thenAnswer((Answer<String>) invocation -> paymentArgumentCaptor.getValue().getId());

        mockMvc.perform(post("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(requestPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.paymentId").value(paymentArgumentCaptor.getValue().getId()));

        assertThat(paymentArgumentCaptor.getValue().getAmount()).isEqualTo(Money.moneyValue("100.21", Currency.getInstance(Locale.UK)));
    }

    @Test
    void shouldReturnAllPayments() throws Exception {
        Payment payment1 = TestData.createNewPayment(UUID.randomUUID().toString());
        Payment payment2 = TestData.createNewPayment(UUID.randomUUID().toString());

        when(paymentRepository.findAll()).thenReturn(asList(payment1, payment2));

        mockMvc.perform(get("/v1/api/payments")
            .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].type").value("Payment"))
            .andExpect(jsonPath("$.[0].id").value(payment1.getId()))
            .andExpect(jsonPath("$.[0].version").value(0))
            .andExpect(jsonPath("$.[0].organisation_id").value("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb"))
            .andExpect(jsonPath("$.[0].attributes.amount").value("100.21"))
            .andExpect(jsonPath("$.[0].attributes.currency").value("GBP"))
            .andExpect(jsonPath("$.[0].attributes.end_to_end_reference").value("Wil piano Jan"))
            .andExpect(jsonPath("$.[0].attributes.numeric_reference").value(1002001))
            .andExpect(jsonPath("$.[0].attributes.payment_id").value("123456789012345678"))
            .andExpect(jsonPath("$.[0].attributes.payment_purpose").value("Paying for goods/services"))
            .andExpect(jsonPath("$.[0].attributes.payment_scheme").value("FPS"))
            .andExpect(jsonPath("$.[0].attributes.payment_type").value("Credit"))
            .andExpect(jsonPath("$.[0].attributes.processing_date").value("2017-01-18"))
            .andExpect(jsonPath("$.[0].attributes.reference").value("Payment for Em's piano lessons"))
            .andExpect(jsonPath("$.[0].attributes.scheme_payment_sub_type").value("InternetBanking"))
            .andExpect(jsonPath("$.[0].attributes.scheme_payment_type").value("ImmediatePayment"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.account_name").value("W Owens"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.account_number").value("31926819"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.account_number_code").value("BBAN"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.account_type").value(0))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.address").value("1 The Beneficiary Localtown SE2"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.bank_id").value("403000"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.bank_id_code").value("GBDSC"))
            .andExpect(jsonPath("$.[0].attributes.beneficiary_party.name").value("Wilfred Jeremiah Owens"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.bearer_code").value("SHAR"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.sender_charges.[0].amount").value("5.00"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.sender_charges.[0].currency").value("GBP"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.sender_charges.[1].amount").value("10.00"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.sender_charges.[1].currency").value("USD"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.receiver_charges_amount").value("1.00"))
            .andExpect(jsonPath("$.[0].attributes.charges_information.receiver_charges_currency").value("USD"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.account_name").value("EJ Brown Black"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.account_number").value("GB29XABC10161234567801"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.account_number_code").value("IBAN"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.address").value("10 Debtor Crescent Sourcetown NE1"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.bank_id").value("203301"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.bank_id_code").value("GBDSC"))
            .andExpect(jsonPath("$.[0].attributes.debtor_party.name").value("Emelia Jane Brown"))
            .andExpect(jsonPath("$.[0].attributes.fx.contract_reference").value("FX123"))
            .andExpect(jsonPath("$.[0].attributes.fx.exchange_rate").value("2.00000"))
            .andExpect(jsonPath("$.[0].attributes.fx.original_amount").value("200.42"))
            .andExpect(jsonPath("$.[0].attributes.fx.original_currency").value("USD"))
            .andExpect(jsonPath("$.[0].attributes.sponsor_party.account_number").value("56781234"))
            .andExpect(jsonPath("$.[0].attributes.sponsor_party.bank_id").value("123123"))
            .andExpect(jsonPath("$.[0].attributes.sponsor_party.bank_id_code").value("GBDSC"))
            .andExpect(jsonPath("$.[1].type").value("Payment"))
            .andExpect(jsonPath("$.[1].id").value(payment2.getId()))
            .andExpect(jsonPath("$.[1].version").value(0))
            .andExpect(jsonPath("$.[1].organisation_id").value("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb"))
            .andExpect(jsonPath("$.[1].attributes.amount").value("100.21"))
            .andExpect(jsonPath("$.[1].attributes.currency").value("GBP"))
            .andExpect(jsonPath("$.[1].attributes.end_to_end_reference").value("Wil piano Jan"))
            .andExpect(jsonPath("$.[1].attributes.numeric_reference").value(1002001))
            .andExpect(jsonPath("$.[1].attributes.payment_id").value("123456789012345678"))
            .andExpect(jsonPath("$.[1].attributes.payment_purpose").value("Paying for goods/services"))
            .andExpect(jsonPath("$.[1].attributes.payment_scheme").value("FPS"))
            .andExpect(jsonPath("$.[1].attributes.payment_type").value("Credit"))
            .andExpect(jsonPath("$.[1].attributes.processing_date").value("2017-01-18"))
            .andExpect(jsonPath("$.[1].attributes.reference").value("Payment for Em's piano lessons"))
            .andExpect(jsonPath("$.[1].attributes.scheme_payment_sub_type").value("InternetBanking"))
            .andExpect(jsonPath("$.[1].attributes.scheme_payment_type").value("ImmediatePayment"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.account_name").value("W Owens"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.account_number").value("31926819"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.account_number_code").value("BBAN"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.account_type").value(0))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.address").value("1 The Beneficiary Localtown SE2"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.bank_id").value("403000"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.bank_id_code").value("GBDSC"))
            .andExpect(jsonPath("$.[1].attributes.beneficiary_party.name").value("Wilfred Jeremiah Owens"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.bearer_code").value("SHAR"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.sender_charges.[0].amount").value("5.00"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.sender_charges.[0].currency").value("GBP"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.sender_charges.[1].amount").value("10.00"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.sender_charges.[1].currency").value("USD"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.receiver_charges_amount").value("1.00"))
            .andExpect(jsonPath("$.[1].attributes.charges_information.receiver_charges_currency").value("USD"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.account_name").value("EJ Brown Black"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.account_number").value("GB29XABC10161234567801"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.account_number_code").value("IBAN"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.address").value("10 Debtor Crescent Sourcetown NE1"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.bank_id").value("203301"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.bank_id_code").value("GBDSC"))
            .andExpect(jsonPath("$.[1].attributes.debtor_party.name").value("Emelia Jane Brown"))
            .andExpect(jsonPath("$.[1].attributes.fx.contract_reference").value("FX123"))
            .andExpect(jsonPath("$.[1].attributes.fx.exchange_rate").value("2.00000"))
            .andExpect(jsonPath("$.[1].attributes.fx.original_amount").value("200.42"))
            .andExpect(jsonPath("$.[1].attributes.fx.original_currency").value("USD"))
            .andExpect(jsonPath("$.[1].attributes.sponsor_party.account_number").value("56781234"))
            .andExpect(jsonPath("$.[1].attributes.sponsor_party.bank_id").value("123123"))
            .andExpect(jsonPath("$.[1].attributes.sponsor_party.bank_id_code").value("GBDSC"));
    }


}