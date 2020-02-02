package com.template.coe.demo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Product {

    @Id
    private int id;

    private String name;

    private int catId;

}
