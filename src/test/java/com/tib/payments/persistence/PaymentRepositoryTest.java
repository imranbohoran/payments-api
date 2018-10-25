package com.tib.payments.persistence;

import com.tib.payments.model.domain.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.tib.payments.TestData.createNewPayment;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "com.tib.payments.persistence")
@DataMongoTest
class PaymentRepositoryTest {

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
}