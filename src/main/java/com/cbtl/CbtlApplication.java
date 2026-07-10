package com.cbtl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // required for the daily reservation-cleanup job
public class CbtlApplication {
    public static void main(String[] args) {
        SpringApplication.run(CbtlApplication.class, args);
    }
}
