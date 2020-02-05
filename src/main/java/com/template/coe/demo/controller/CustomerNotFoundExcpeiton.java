package com.template.coe.demo.controller;

public class CustomerNotFoundExcpeiton extends RuntimeException {

    public CustomerNotFoundExcpeiton(Long id) {
        throw new RuntimeException("Exception by Id " + id);
    }
}
