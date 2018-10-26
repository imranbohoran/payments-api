package com.tib.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tib.payments")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
