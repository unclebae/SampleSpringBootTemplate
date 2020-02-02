package com.template.coe.demo.repositories;

import com.template.coe.demo.domain.Product;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Integer> {

    @Cacheable("productByCategoryCache")
    List<Product> findByCatId(int catId);

    @CacheEvict(cacheNames = "productByCategoryCache", allEntries = true, key = "#result?.catId")
    Product save(Product product);

    @CacheEvict(cacheNames = "productByCategoryCache", allEntries = true, key = "#p0.catId")
    void delete(Product product);
}
