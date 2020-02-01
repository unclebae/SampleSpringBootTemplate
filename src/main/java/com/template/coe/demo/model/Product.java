package com.template.coe.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "product", type = "external")
public class Product {

    @Id
    private String id;

    private String name;

    private int catId;
}
