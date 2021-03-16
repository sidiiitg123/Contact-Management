package com.spring.smartcontact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spring"})
public class SmartcontactApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartcontactApplication.class, args);
    }

}
