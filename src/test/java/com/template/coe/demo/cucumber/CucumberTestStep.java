package com.template.coe.demo.cucumber;

import com.template.coe.demo.model.Product;
import io.cucumber.java8.En;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberTestStep implements En {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Product> productResponse;
    private ResponseEntity<String> errResponse;

    public CucumberTestStep() {
        Given("(.*) Service is running", (String serviceName) -> {
            ResponseEntity<String> healthResponse = restTemplate.getForEntity("/health", String.class, new HashMap<>());
            Assert.assertEquals(HttpStatus.OK, healthResponse.getStatusCode());
        });
    }
}
