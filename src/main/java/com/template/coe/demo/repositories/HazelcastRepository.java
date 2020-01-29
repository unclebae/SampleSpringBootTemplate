package com.template.coe.demo.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class HazelcastRepository {

//    @Cacheable("cache-test")
    public String getCache(String id) {
        System.out.println("Insert Id: " + id);
        return "Return Echo: " + id;
    }
}
