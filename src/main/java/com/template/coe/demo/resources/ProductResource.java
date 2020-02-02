package com.template.coe.demo.resources;

import com.template.coe.demo.domain.Product;
import com.template.coe.demo.exception.BadRequestException;
import com.template.coe.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductResource {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/api/products")
    ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productRepository.getOne(id), HttpStatus.OK);
    }

    @PostMapping("/api/products")
    ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        Product saveedProduct = productRepository.save(product);
        return new ResponseEntity<>(saveedProduct, HttpStatus.OK);
    }

    @PostMapping("/api/products/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        Product existingProdct = productRepository.getOne(id);

        try {
            existingProdct.setCatId(product.getCatId());
            existingProdct.setName(product.getName());
            Product savedProduct = productRepository.save(existingProdct);

            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/products/{id}")
    Product deleteProduct(@PathVariable("id") int id) {

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            String errMsg = "Product Not found with code " + id;

            throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
        }
        productRepository.delete(product.get());

        return product.get();
    }

    @ExceptionHandler(BadRequestException.class)
    void handleBadRequests(BadRequestException bre, HttpServletResponse response) throws IOException {
        int respCode = (bre.getErrorCode() == BadRequestException.ID_NOT_FOUND) ? HttpStatus.NOT_FOUND.value() : HttpStatus.BAD_REQUEST.value() ;
        response.sendError(respCode, bre.getErrorCode() + ":" + bre.getMessage());
    }
}
