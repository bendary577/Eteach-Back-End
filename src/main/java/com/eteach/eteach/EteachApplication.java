package com.eteach.eteach;

import com.eteach.eteach.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
@EnableConfigurationProperties(JwtConfig.class)
@EnableAsync
public class EteachApplication {

    public static void main(String[] args) {
        SpringApplication.run(EteachApplication.class, args);
    }

}