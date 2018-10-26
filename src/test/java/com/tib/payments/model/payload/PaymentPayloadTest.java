package com.tib.payments.model.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tib.payments.model.domain.ForeignExchange;
import com.tib.payments.model.domain.Money;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.domain.PaymentCharge;
import com.tib.payments.model.domain.PaymentParticipant;
import com.tib.payments.model.domain.PaymentScheme;
import com.tib.payments.model.domain.PaymentType;
import com.tib.payments.model.domain.SchemeSubType;
import com.tib.payments.model.domain.SchemeType;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

import static com.tib.payments.TestData.createNewPayment;
import static com.tib.payments.model.payload.PaymentAttributes.DATE_TIME_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentPayloadTest {

    @Test
    void shouldGenerateExpectedJsonForPaymentView() throws Exception {
        Payment payment = createNewPayment("4ee3a8d8-ca7b-4290-a52c-dd5b6165ec43");

        PaymentPayload paymentView = new PaymentPayload(payment);

        String expectedJson = new String(
            Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("test-data/sample-payment.json")).toURI())));

        ObjectMapper objectMapper = new ObjectMapper();
        String generatedJson = objectMapper.writeValueAsString(paymentView);

        JSONAssert.assertEquals(expectedJson, generatedJson, false);
    }

    @Test
    public void shouldGeneratePaymentForGivenPayload() throws Exception {
        String createPaymentPayload = new String(
            Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("test-data/sample-create-payment.json")).toURI())));

        ObjectMapper objectMapper = new ObjectMapper();
        PaymentPayload paymentPayload = objectMapper.readValue(createPaymentPayload, PaymentPayload.class);

        Payment newPayment = paymentPayload.createNewPayment();

        assertThat(newPayment).isNotNull();
        assertThat(newPayment.getId()).isNotNull();
        assertThat(newPayment.getVersion()).isEqualTo(0);
        assertThat(newPayment.getAmount()).isEqualTo(Money.moneyValue("100.21", Currency.getInstance(Locale.UK)));
        assertBeneficiaryParty(newPayment.getBeneficiary());
        assertChargesInformation(newPayment.getPaymentCharge());
        assertDebtorParty(newPayment.getDebtor());
        assertThat(newPayment.getEndToEndReference()).isEqualTo("Wil piano Jan");
        assertForeignExchange(newPayment.getForeignExchange());
        assertThat(newPayment.getNumericReference()).isEqualTo(1002001L);
        assertThat(newPayment.getPaymentId()).isEqualTo("123456789012345678");
        assertThat(newPayment.getPurpose()).isEqualTo("Paying for goods/services");
        assertThat(newPayment.getScheme()).isEqualTo(PaymentScheme.FPS);
        assertThat(newPayment.getType()).isEqualTo(PaymentType.CREDIT);
        assertThat(newPayment.getProcessingDate()).isEqualTo(LocalDate.parse("2017-01-18", DATE_TIME_FORMATTER).atStartOfDay());
        assertThat(newPayment.getReference()).isEqualTo("Payment for Em's piano lessons");
        assertThat(newPayment.getSchemeSubType()).isEqualTo(SchemeSubType.INTERNET_BANKING);
        assertThat(newPayment.getSchemeType()).isEqualTo(SchemeType.IMMEDIATE_PAYMENT);
        assertSponsorParty(newPayment.getSponsor());
    }

    private void assertSponsorParty(PaymentParticipant sponsor) {
        assertThat(sponsor.getAccountNumber()).isEqualTo("56781234");
        assertThat(sponsor.getBankId()).isEqualTo("123123");
        assertThat(sponsor.getBankIdCode()).isEqualTo("GBDSC");
    }

    private void assertForeignExchange(ForeignExchange foreignExchange) {
        assertThat(foreignExchange.getOriginalAmount()).isEqualTo(Money.moneyValue("200.42", Currency.getInstance("USD")));
        assertThat(foreignExchange.getExchangeRate()).isEqualByComparingTo(new BigDecimal("2.00000"));
        assertThat(foreignExchange.getContractReference()).isEqualTo("FX123");
    }

    private void assertDebtorParty(PaymentParticipant debtorParticipant) {
        assertThat(debtorParticipant.getAccountNumber()).isEqualTo("GB29XABC10161234567801");
        assertThat(debtorParticipant.getAccountName()).isEqualTo("EJ Brown Black");
        assertThat(debtorParticipant.getAccountNumberCode()).isEqualTo("IBAN");
        assertThat(debtorParticipant.getAddress()).isEqualTo("10 Debtor Crescent Sourcetown NE1");
        assertThat(debtorParticipant.getBankId()).isEqualTo("203301");
        assertThat(debtorParticipant.getBankIdCode()).isEqualTo("GBDSC");
        assertThat(debtorParticipant.getParticipantName()).isEqualTo("Emelia Jane Brown");

    }

    private void assertChargesInformation(PaymentCharge paymentCharge) {
        assertThat(paymentCharge.getReceiverCharge().display()).isEqualTo("1.00");
        assertThat(paymentCharge.getReceiverCharge().getCurrency().getCurrencyCode()).isEqualTo("USD");
        assertThat(paymentCharge.getBearerCode()).isEqualTo("SHAR");
        assertThat(paymentCharge.getSenderCharges()).containsExactlyInAnyOrder(
            Money.moneyValue("5.00", Currency.getInstance("GBP")),
            Money.moneyValue("10.00", Currency.getInstance("USD")));
    }

    private void assertBeneficiaryParty(PaymentParticipant beneficiaryParticipant) {
        assertThat(beneficiaryParticipant.getAccountNumber()).isEqualTo("31926819");
        assertThat(beneficiaryParticipant.getAccountName()).isEqualTo("W Owens");
        assertThat(beneficiaryParticipant.getAccountNumberCode()).isEqualTo("BBAN");
        assertThat(beneficiaryParticipant.getAccountType()).isEqualTo(0);
        assertThat(beneficiaryParticipant.getAddress()).isEqualTo("1 The Beneficiary Localtown SE2");
        assertThat(beneficiaryParticipant.getBankId()).isEqualTo("403000");
        assertThat(beneficiaryParticipant.getBankIdCode()).isEqualTo("GBDSC");
        assertThat(beneficiaryParticipant.getParticipantName()).isEqualTo("Wilfred Jeremiah Owens");
    }
}