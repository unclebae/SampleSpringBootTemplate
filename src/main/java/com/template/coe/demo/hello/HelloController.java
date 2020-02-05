package com.template.coe.demo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    HelloWorldClient helloWorldClient;

    @GetMapping(value = "/hello/{firstName}/{lastName}")
    public String hello(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        String resultValue = helloWorldClient.sayHello(firstName, lastName);

        return resultValue;
    }

}
