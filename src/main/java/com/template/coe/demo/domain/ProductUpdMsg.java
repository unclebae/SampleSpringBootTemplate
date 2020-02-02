package com.template.coe.demo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductUpdMsg {

    Product product;
    boolean isDelete = false;

}
