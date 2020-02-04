package com.template.coe.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@PropertySource("configuration.properties")
public class PropertiesConfigTest {

    @Value("${configuration.projectName}")
    private String projectName;

    @Autowired
    PropertiesConfigTest(@Value("${configuration.projectName}") String pn) {
        log.info("Constructor: " + pn);
    }

    @Value("${configuration.projectName}")
    void setProjectName(String projectName) {
        log.info("Setter: " + projectName);
    }

    @Autowired
    void setEnvironment(Environment env) {
        log.info("setEnvironment: " + env.getProperty("configuration.projectName"));
    }

    @Bean
    InitializingBean both(Environment env, @Value("${configuration.projectName}") String projectName) {
        return () -> {
            log.info("@Bean with both dependencies (projectname): " + projectName);
            log.info("@Bean with both dependencies (env): " + env.getProperty("configuration.projectName"));
        };
    }

    @PostConstruct
    void afterPropertiesSet() throws Throwable {
        log.info("fieldValue: " + this.projectName);
    }
}
