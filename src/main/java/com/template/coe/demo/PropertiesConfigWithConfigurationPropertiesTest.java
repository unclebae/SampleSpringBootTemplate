package com.template.coe.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableConfigurationProperties
public class PropertiesConfigWithConfigurationPropertiesTest {

    @Autowired
    public PropertiesConfigWithConfigurationPropertiesTest(ConfigurationProjectProperties cp) {
        log.info("ConfigurationProjectProperties.projectName =>>> " + cp.getProjectName());
    }
}

@Component
@ConfigurationProperties("configuration")
class ConfigurationProjectProperties {
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
