package com.template.coe.demo.resources;

import com.template.coe.demo.domain.Product;
import com.template.coe.demo.exception.BadRequestException;
import com.template.coe.demo.message.ProductMsgProducer;
import com.template.coe.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ProductMsgProducer productMsgProducer;

    @GetMapping("/api/products")
    ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/products/{id}")
    ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        return new ResponseEntity<>(productRepository.findById(id).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/api/products")
    ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        Product saveedProduct = productRepository.save(product);
        return new ResponseEntity<>(saveedProduct, HttpStatus.OK);
    }

    @PutMapping("/api/products/{id}")
    ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
        Optional<Product> existingProdct = productRepository.findById(id);

        if (existingProdct.isEmpty()) {
            String errMsg = "Product Not found with code " + id;
            throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
        }
        Product selectedProduct = existingProdct.get();

        selectedProduct.setCatId(product.getCatId());
        selectedProduct.setName(product.getName());
        Product savedProduct = productRepository.save(selectedProduct);

        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{id}")
    Product deleteProduct(@PathVariable("id") int id) {

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            String errMsg = "Product Not found with code " + id;

            throw new BadRequestException(BadRequestException.ID_NOT_FOUND, errMsg);
        }
        productRepository.delete(product.get());
        productMsgProducer.sendUpdate(product.get(), true);

        return product.get();
    }

    @ExceptionHandler(BadRequestException.class)
    void handleBadRequests(BadRequestException bre, HttpServletResponse response) throws IOException {
        int respCode = (bre.getErrorCode() == BadRequestException.ID_NOT_FOUND) ? HttpStatus.NOT_FOUND.value() : HttpStatus.BAD_REQUEST.value() ;
        response.sendError(respCode, bre.getErrorCode() + ":" + bre.getMessage());
    }
}
