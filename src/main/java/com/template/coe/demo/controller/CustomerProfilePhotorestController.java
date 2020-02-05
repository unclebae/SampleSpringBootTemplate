package com.template.coe.demo.controller;

import com.template.coe.demo.domain.Customer;
import com.template.coe.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.URI;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping(value = "/customers/{id}/photo")
public class CustomerProfilePhotorestController {

    private File root;
    private final CustomerRepository customerRepository;

    public CustomerProfilePhotorestController(@Value("${upload.dir:${user.home}/images}") String uploadDir, CustomerRepository customerRepository) {
        log.info("Upload Dir: " + uploadDir);
        this.customerRepository = customerRepository;
        this.root = new File(uploadDir);

        Assert.isTrue(this.root.exists() || this.root.mkdir(), String.format("The path '%s' must exists.", this.root.getAbsolutePath()));
    }

    @GetMapping
    ResponseEntity<Resource> read(@PathVariable Long id) {
        return this.customerRepository
                .findById(id)
                .map(
                        customer -> {
                            File file = fileFor(customer);

                            Assert.isTrue(file.exists(), String.format("file-not-found %s", file.getAbsolutePath()));
                            Resource fileSystemResource = new FileSystemResource(file);
                            return ResponseEntity.ok()
                                    .contentType(MediaType.IMAGE_PNG)
                                    .body(fileSystemResource);
                        }
                )
                .orElseThrow(() -> new CustomerNotFoundExcpeiton(id));
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT})
    Callable<ResponseEntity<?>> write(@PathVariable Long id, @RequestParam MultipartFile file) throws Exception {
        log.info(String.format("upload-start /customer/%s/photo {%s bytes}", id, file.getSize()));

        return () -> this.customerRepository.findById(id)
                .map(
                        customer -> {
                            File fileForCustomer = fileFor(customer);

                            try (
                                    InputStream in = file.getInputStream();
                                    OutputStream out = new FileOutputStream(fileForCustomer)
                            ) {
                                FileCopyUtils.copy(in, out);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
                            log.info(String.format("upload-finish /customer/%s/photo (%s)", id, location));
                            return ResponseEntity.created(location).build();
                        }
                )
                .orElseThrow(() -> new CustomerNotFoundExcpeiton(id));
    }

    private File fileFor(Customer customer) {
        return new File(this.root, Long.toString(customer.getId()) + ".png");
    }
}
