package com.template.coe.demo.resources;

import com.template.coe.demo.repositories.HazelcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @Autowired
    private HazelcastRepository hazelcastRepository;

    @GetMapping("/api/{id}")
    public String getCache(@PathVariable("id") String id) {
        return hazelcastRepository.getCache(id);
    }
}
