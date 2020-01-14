package com.template.coe.demo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "User Info")
public class User {

    @ApiModelProperty(notes = "User first name")
    @NotNull(message = "First Name cannot be null")
    private String firstName;

    @ApiModelProperty(notes = "User age")
    @Min(value = 15, message = "Age should not be less than 15")
    @Max(value = 65, message = "Age should not be greater than 65")
    private int age;

    public User(@NotNull(message = "First Name cannot be null") String firstName, @Min(value = 15, message = "Age should not be less than 15") @Max(value = 65, message = "Age should not be greater than 65") int age) {
        this.firstName = firstName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
