package com.tib.payments.persistence;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.tib.payments.persistence")
@DataMongoTest
class PaymentRepositoryTest {

    private static final Currency ukCurrency = Currency.getInstance(Locale.UK);

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    void shouldPersistPayment() {

        Payment newPayment = createNewPayment();

        Payment savedPayment = paymentRepository.save(newPayment);

        assertThat(savedPayment).isEqualTo(newPayment);
    }

    @Test
    void shouldReturnAllPayments() {
        Payment newPayment = createNewPayment();
        Payment newPayment2 = createNewPayment();

        assertThat(paymentRepository.findAll().size()).isEqualTo(0);

        paymentRepository.saveAll(asList(newPayment, newPayment2));

        assertThat(paymentRepository.findAll().size()).isEqualTo(2);

    }


    private Payment createNewPayment() {
        Payment newPayment = new Payment();
        newPayment.setId(UUID.randomUUID().toString());

        newPayment.setAmount(Money.moneyValue("122", ukCurrency));
        newPayment.setDebtor(createDebtor());
        newPayment.setEndToEndReference("Wil piano Jan");
        newPayment.setForeignExchange(createForex());
        newPayment.setNumericReference(1002001L);
        newPayment.setPaymentId("123456789012345678");
        newPayment.setOrganisationId("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb");
        newPayment.setPaymentCharge(createPaymentCharge());
        newPayment.setProcessingDate(LocalDateTime.now());
        newPayment.setPurpose("Paying for goods/services");
        newPayment.setReference("Payment for Em's piano lessons");
        newPayment.setScheme(PaymentScheme.FPS);
        newPayment.setSchemeSubType(SchemeSubType.INTERNET_BANKING);
        newPayment.setSchemeType(SchemeType.IMMEDIATE_PAYMENT);
        newPayment.setSponsor(createSponsor());
        newPayment.setType(PaymentType.CREDIT);
        newPayment.setVersion(0);
        return newPayment;
    }


    private PaymentParticipant createSponsor() {
        PaymentParticipant sponsor = new PaymentParticipant();
        sponsor.setAccountNumber("56781234");
        sponsor.setBankId("123123");
        sponsor.setBankIdCode("GBDSC");
        return sponsor;
    }

    private PaymentCharge createPaymentCharge() {
        PaymentCharge paymentCharge = new PaymentCharge();
        paymentCharge.setBearerCode("SHAR");
        paymentCharge.setReceiverCharge(Money.moneyValue("1.00", ukCurrency));
        paymentCharge.setSenderCharges(asList(Money.moneyValue("5.00", ukCurrency), Money.moneyValue("10.00", Currency.getInstance(Locale.US))));
        return paymentCharge;
    }

    private ForeignExchange createForex() {
        ForeignExchange foreignExchange = new ForeignExchange();
        foreignExchange.setContractReference("FX123");
        foreignExchange.setExchangeRate(BigDecimal.valueOf(2.00000));
        foreignExchange.setOriginalAmount(Money.moneyValue("200.42", ukCurrency));
        return foreignExchange;
    }

    private PaymentParticipant createDebtor() {
        PaymentParticipant debtor = new PaymentParticipant();
        debtor.setAccountName("EJ Brown Black");
        debtor.setAccountNumber("GB29XABC10161234567801");
        debtor.setAccountNumberCode("IBAN");
        debtor.setAddress("10 Debtor Crescent Sourcetown NE1");
        debtor.setBankId("203301");
        debtor.setBankIdCode("GBDSC");
        debtor.setParticipantName("Emelia Jane Brown");
        return debtor;
    }
}