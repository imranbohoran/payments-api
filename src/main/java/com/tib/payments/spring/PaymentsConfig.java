package com.tib.payments.spring;

import com.tib.payments.model.actions.CreatePayment;
import com.tib.payments.model.actions.DeletePayment;
import com.tib.payments.persistence.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tib.payments.persistence")
public class PaymentsConfig {

    @Bean
    public CreatePayment createPayment(PaymentRepository paymentRepository) {
        return new CreatePayment(paymentRepository);
    }

    @Bean
    public DeletePayment deletePayment(PaymentRepository paymentRepository) {
        return new DeletePayment(paymentRepository);
    }
}
