package com.template.coe.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestResource {

    private String name;

    @GetMapping("/api/test")
    public String testGreeting() {
        return "Hello I'm testing controller";
    }

    @PutMapping("/api/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setName(@RequestBody final String name) {
        this.name = name;
    }

    @DeleteMapping("/api/test")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetToDefault() {
        this.name = null;
    }
}
