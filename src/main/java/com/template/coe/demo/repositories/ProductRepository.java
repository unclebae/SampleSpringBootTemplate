package com.template.coe.demo.repositories;

import com.template.coe.demo.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    List<Product> findByCatId(int catId);
}
