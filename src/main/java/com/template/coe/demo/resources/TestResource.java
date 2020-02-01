package com.template.coe.demo.resources;

import com.template.coe.demo.model.Product;
import com.template.coe.demo.repositories.HazelcastRepository;
import com.template.coe.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestResource {

    @Autowired
    private HazelcastRepository hazelcastRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/api/{id}")
    public String getCache(@PathVariable("id") String id) {
        return hazelcastRepository.getCache(id);
    }

    @GetMapping("/api/es/{id}")
    public List<Product> getProductByCatId(@PathVariable("id") int id) {
        return productRepository.findByCatId(id);
    }
}
