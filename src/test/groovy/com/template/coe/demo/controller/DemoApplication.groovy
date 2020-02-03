package com.template.coe.demo.controller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class DemoApplicationTest extends Specification {

    @Autowired(required = false)
    private TestResource testResource;

    def "when context is loaded then all expected beans are created"() {
        expect: "the WebController is created"
        testResource
    }
}
