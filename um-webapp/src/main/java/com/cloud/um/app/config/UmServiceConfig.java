package com.cloud.um.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.cloud.um.service" })
public class UmServiceConfig {
    public UmServiceConfig() {}
}
