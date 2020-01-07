package com.template.coe.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class DemoController {

    @GetMapping
    public String sample() {
        return "안녕하세요. ";
    }
}
