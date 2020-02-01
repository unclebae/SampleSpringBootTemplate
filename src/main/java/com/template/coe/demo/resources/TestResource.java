package com.template.coe.demo.resources;

import com.template.coe.demo.domain.Product;
import com.template.coe.demo.repositories.HazelcastRepository;
import com.template.coe.demo.repositories.MongoDBRepository;
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
    private MongoDBRepository mongoDBRepository;

    @GetMapping("/api/{id}")
    public String getCache(@PathVariable("id") String id) {
        return hazelcastRepository.getCache(id);
    }

    @GetMapping("/api/mongo/{catId}")
    public List<Product> getProductByCatId(@PathVariable("catId") int catId) {
        return mongoDBRepository.findByCatId(catId);
    }
}
