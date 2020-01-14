package com.template.coe.demo.controller;

import com.template.coe.demo.domain.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "SwaggerTest Apis")
@RestController
public class SwaggerSampleController {

    @ApiOperation(value = "Greeting Method using Get", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message="Successfully retrieved greeting"),
            @ApiResponse(code = 401, message="You are not authorized to view the resources")
    })
    @GetMapping("/")
    public String getHello() {
        return "Hello Swagger";
    }

    @ApiOperation(value = "Posting userInfo to DB", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message="Successfully saved user info"),
            @ApiResponse(code = 401, message="You are not authorized to view the resources")
    })
    @PostMapping("/")
    public User postHello(@ApiParam(value = "UserInfo", required=true) @Valid @RequestBody User user) {
        return user;
    }

}
