package com.template.coe.demo.model;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

public class DynamicHandler {

    private String key;

    private String uri;

    private RequestMethod method;

    private Map<String, Object> input;

    private Map<String, Object> input_comment;

    private Map<String, Object> output;

    private Map<String, Object> output_comment;

    public DynamicHandler() {
    }

    public DynamicHandler(String key, String uri, RequestMethod method, Map<String, Object> input, Map<String, Object> input_comment, Map<String, Object> output, Map<String, Object> output_comment) {
        this.key = key;
        this.uri = uri;
        this.method = method;
        this.input = input;
        this.input_comment = input_comment;
        this.output = output;
        this.output_comment = output_comment;
    }

    public Map<String, Object> getInput_comment() {
        return input_comment;
    }

    public void setInput_comment(Map<String, Object> input_comment) {
        this.input_comment = input_comment;
    }

    public Map<String, Object> getOutput_comment() {
        return output_comment;
    }

    public void setOutput_comment(Map<String, Object> output_comment) {
        this.output_comment = output_comment;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public Map<String, Object> getOutput() {
        return output;
    }

    public void setOutput(Map<String, Object> output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "DynamicHandler{" +
                "key='" + key + '\'' +
                ", uri='" + uri + '\'' +
                ", method=" + method +
                ", input=" + input +
                ", input_comment=" + input_comment +
                ", output=" + output +
                ", output_comment=" + output_comment +
                '}';
    }
}
