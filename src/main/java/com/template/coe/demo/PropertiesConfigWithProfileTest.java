package com.template.coe.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class PropertiesConfigWithProfileTest {

    @Configuration
    @Profile("prod")
    @PropertySource("configuration-prod.properties")
    public static class ProdConfiguration {
        @Bean
        InitializingBean init() {
            return () -> log.info("prod InitializingBean");
        }
    }

    @Configuration
    @Profile({"default", "dev"})
    @PropertySource("configuration.properties")
    public static class DefaultConfiguration {
        @Bean
        InitializingBean init() {
            return () -> log.info("default InitiazlizingBean");
        }
    }

    @Bean
    InitializingBean which(Environment e, @Value("${configuration.projectName}") String projectName) {
        return () -> {
            log.info("activeProfiles: " + StringUtils.arrayToCommaDelimitedString(e.getActiveProfiles()) + "'");
            log.info("configuration.projectName" + projectName);
        };
    }

}
