package com.tib.payments.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.tib.payments.persistence")
public class PaymentsConfig {
}
