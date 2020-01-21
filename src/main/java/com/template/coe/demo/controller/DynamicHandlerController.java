package com.template.coe.demo.controller;

import com.template.coe.demo.model.DynamicHandler;
import com.template.coe.demo.service.DynamicHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@RestController
public class DynamicHandlerController {

    @Autowired
    DynamicHandlerService dynamicHandlerService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostMapping("/api/add-handler")
    public String addHandler(@RequestBody DynamicHandler handler) throws Exception {
        return registHandler(handler);
    }

    public String registHandler(DynamicHandler handler) {
        try {
            System.out.println("Handler: " + handler);
            RequestMappingInfo requestMappingInfo = RequestMappingInfo
                    .paths(handler.getKey() + handler.getKey())
                    .methods(handler.getMethod())
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .build();

            requestMappingHandlerMapping.registerMapping(requestMappingInfo, this,
                    DynamicHandlerController.class.getDeclaredMethod("handlerResponse"));

            System.out.println(requestMappingHandlerMapping.getHandlerMethods());
            return handler.getKey() + handler.getUri();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    public ResponseEntity<String> handlerResponse() {
//        System.out.println("Response: " + handler);
//        return ResponseEntity.ok(handler.getOutput());
        return ResponseEntity.ok("OK");
    }

}
