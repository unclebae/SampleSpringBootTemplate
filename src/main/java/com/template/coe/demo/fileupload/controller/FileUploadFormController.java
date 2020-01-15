package com.template.coe.demo.fileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileUploadFormController {
    @GetMapping("/")
    public String fileUploadPage(Model model) throws Exception {
        return "uploadForm";
    }
}
