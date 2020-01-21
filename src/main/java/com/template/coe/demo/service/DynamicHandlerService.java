package com.template.coe.demo.service;

import com.template.coe.demo.controller.DynamicHandlerController;
import com.template.coe.demo.model.DynamicHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.Map;

@Service
public class DynamicHandlerService {

    @Autowired
    DynamicHandlerController controller;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public String addHandler(DynamicHandler handler) throws Exception {
        System.out.println("Handler: " + handler);
        RequestMappingInfo requestMappingInfo = RequestMappingInfo
                .paths(handler.getKey() + handler.getKey())
                .methods(handler.getMethod())
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .build();

        Class[] cArr = new Class[1];
        cArr[0] = DynamicHandler.class;
        cArr[0] = DynamicHandler.class;

        requestMappingHandlerMapping.registerMapping(requestMappingInfo, controller, this.getClass().getDeclaredMethod("handlerResponse", cArr ));

        return handler.getKey() + handler.getUri();
    }

    public ResponseEntity<Map<String, Object>> handlerResponse(DynamicHandler handler) {
        return ResponseEntity.ok(handler.getOutput());
    }
}
//
//        handlerMapping.registerMapping(
//                RequestMappingInfo.paths("/simpleHandler").methods(RequestMethod.GET)
//                        .produces(MediaType.APPLICATION_JSON_VALUE).build(),
//                this,
//                // Method to be executed when above conditions apply, i.e.: when HTTP
//                // method and URL are called)
//                MyController.class.getDeclaredMethod("simpleHandler"));
//
//        handlerMapping.registerMapping(
//                RequestMappingInfo.paths("/x/y/z/parametrizedHandler").methods(RequestMethod.GET)
//                        .produces(MediaType.APPLICATION_JSON_VALUE).build(),
//                this,
//                // Method to be executed when above conditions apply, i.e.: when HTTP
//                // method and URL are called)
//                MyController.class.getDeclaredMethod("parametrizedHandler", String.class, HttpServletRequest.class));
//    }
//
//    public List<String> simpleHandler() {
//        return Arrays.asList("simpleHandler called");
//    }
//
//    public ResponseEntity<List<String>> parametrizedHandler(
//            @RequestParam(value = "param1", required = false) String param1,
//            HttpServletRequest servletRequest) {
//        return ResponseEntity.ok(Arrays.asList("parametrizedHandler called", param1));
//    }
//}