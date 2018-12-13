package com.cloud.um.app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({ "com.cloud.um.persistence" })
@EnableJpaRepositories(basePackages = "com.cloud.um.persistence.repository")
@EntityScan(basePackages = "com.cloud.um.persistence.model")
public class UmPersistenceConfig {
    public UmPersistenceConfig() {}
}
