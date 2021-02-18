package com.eteach.eteach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EteachApplication {

    public static void main(String[] args) {
        SpringApplication.run(EteachApplication.class, args);
    }

}