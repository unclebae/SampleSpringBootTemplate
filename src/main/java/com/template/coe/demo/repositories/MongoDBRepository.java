package com.template.coe.demo.repositories;

import com.template.coe.demo.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoDBRepository extends MongoRepository<Product, String> {

    List<Product> findByCatId(int catId);
}
