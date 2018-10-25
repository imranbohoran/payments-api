package com.tib.payments;

import com.tib.payments.model.domain.ForeignExchange;
import com.tib.payments.model.domain.Money;
import com.tib.payments.model.domain.Payment;
import com.tib.payments.model.domain.PaymentCharge;
import com.tib.payments.model.domain.PaymentParticipant;
import com.tib.payments.model.domain.PaymentScheme;
import com.tib.payments.model.domain.PaymentType;
import com.tib.payments.model.domain.SchemeSubType;
import com.tib.payments.model.domain.SchemeType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

import static java.util.Arrays.asList;

public class TestData {
    private static final Currency UK_CURRENCY = Currency.getInstance(Locale.UK);
    private static final Currency US_CURRENCY = Currency.getInstance(Locale.US);

    public static Payment createNewPayment(String id) {
        Payment newPayment = createNewPayment();
        newPayment.setId(id);
        return newPayment;
    }

    public static Payment createNewPayment() {
        Payment newPayment = new Payment();
        newPayment.setId(UUID.randomUUID().toString());

        newPayment.setAmount(Money.moneyValue("100.21", UK_CURRENCY));
        newPayment.setBeneficiary(createBeneficiary());
        newPayment.setDebtor(createDebtor());
        newPayment.setEndToEndReference("Wil piano Jan");
        newPayment.setForeignExchange(createForex());
        newPayment.setNumericReference(1002001L);
        newPayment.setPaymentId("123456789012345678");
        newPayment.setOrganisationId("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb");
        newPayment.setPaymentCharge(createPaymentCharge());
        newPayment.setProcessingDate(LocalDateTime.of(2017, 1, 18, 0, 0));
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

    private static PaymentParticipant createBeneficiary() {
        PaymentParticipant beneficiary = new PaymentParticipant();
        beneficiary.setAccountName("W Owens");
        beneficiary.setAccountNumber("31926819");
        beneficiary.setAccountNumberCode("BBAN");
        beneficiary.setAccountType(0);
        beneficiary.setAddress("1 The Beneficiary Localtown SE2");
        beneficiary.setBankId("403000");
        beneficiary.setBankIdCode("GBDSC");
        beneficiary.setParticipantName("Wilfred Jeremiah Owens");
        return beneficiary;
    }

    private static PaymentParticipant createSponsor() {
        PaymentParticipant sponsor = new PaymentParticipant();
        sponsor.setAccountNumber("56781234");
        sponsor.setBankId("123123");
        sponsor.setBankIdCode("GBDSC");
        return sponsor;
    }

    private static PaymentCharge createPaymentCharge() {
        PaymentCharge paymentCharge = new PaymentCharge();
        paymentCharge.setBearerCode("SHAR");
        paymentCharge.setReceiverCharge(Money.moneyValue("1.00", US_CURRENCY));
        paymentCharge.setSenderCharges(asList(Money.moneyValue("5.00", UK_CURRENCY), Money.moneyValue("10.00", Currency.getInstance(Locale.US))));
        return paymentCharge;
    }

    private static ForeignExchange createForex() {
        ForeignExchange foreignExchange = new ForeignExchange();
        foreignExchange.setContractReference("FX123");
        foreignExchange.setExchangeRate(new BigDecimal("2.00000"));
        foreignExchange.setOriginalAmount(Money.moneyValue("200.42", US_CURRENCY));
        return foreignExchange;
    }

    private static PaymentParticipant createDebtor() {
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
