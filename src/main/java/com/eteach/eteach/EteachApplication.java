package com.eteach.eteach;

import com.eteach.eteach.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
@EnableConfigurationProperties(JwtConfig.class)
public class EteachApplication {

    public static void main(String[] args) {
        SpringApplication.run(EteachApplication.class, args);
    }

}