package com.template.coe.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @GetMapping("/api/greeting")
    public String greeting() {
        return "Hello I'm Spring and Docker";
    }
}
