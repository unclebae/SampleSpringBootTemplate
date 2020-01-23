package com.template.coe.demo.controller;

import com.template.coe.demo.model.DynamicHandler;
import com.template.coe.demo.model.HandlerProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DynamicHandlerController {

    private Map<String, DynamicHandler> handlerMaps = new HashMap<>();

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @PostMapping("/api/add-handler")
    public ResponseEntity<HandlerProcessResult> addHandler(@RequestBody DynamicHandler handler, HttpServletRequest request) throws Exception {
        return registHandler(handler, request);
    }

    @GetMapping("/api/handlers")
    public ResponseEntity<Map<String, DynamicHandler>> getHandlers() {
        return ResponseEntity.ok(handlerMaps);
    }

    @GetMapping("/api/handlers/{key}")
    public ResponseEntity<DynamicHandler> getHandlersByKey(@PathVariable String key) {
        return ResponseEntity.ok(handlerMaps.get(key));
    }

    public ResponseEntity<HandlerProcessResult> registHandler(DynamicHandler handler, HttpServletRequest request) {

        try {
            System.out.println("Handler: " + handler);
            RequestMappingInfo requestMappingInfo = RequestMappingInfo
                    .paths(handler.getUri())
                    .methods(handler.getMethod())
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .build();

            requestMappingHandlerMapping.registerMapping(requestMappingInfo, this,
                    DynamicHandlerController.class.getDeclaredMethod("handlerResponse", HttpServletRequest.class));

            System.out.println(requestMappingHandlerMapping.getHandlerMethods());

            DynamicHandler dynamicHandler = handlerMaps.putIfAbsent(handler.getKey(), handler);

            if (dynamicHandler == null) {
                return ResponseEntity.ok(new HandlerProcessResult("정상적으로 등록 되었습니다. "));
            }
            else {
                return ResponseEntity.unprocessableEntity().body(new HandlerProcessResult(handler.getKey() + " 는 이미 등록된 API 입니다. "));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<Map<String, Object>> handlerResponse(HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        System.out.println("Request URI: " + requestURI);

        DynamicHandler dynamicHandler = handlerMaps.values().stream().filter((item) -> item.getUri().equals(requestURI)).findFirst().orElse(null);

        if (dynamicHandler == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dynamicHandler.getOutput());

    }

}
