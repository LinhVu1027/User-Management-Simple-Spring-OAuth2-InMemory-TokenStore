package com.cloud.um.app.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        final ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        final String activeProfiles = environment.getProperty("spring.profiles.active");

        if (activeProfiles != null) {
            environment.setActiveProfiles(activeProfiles.split(","));
        }
    }
}
