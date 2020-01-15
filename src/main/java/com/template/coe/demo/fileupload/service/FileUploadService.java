package com.template.coe.demo.fileupload.service;

import com.template.coe.demo.fileupload.config.FileStorageProperties;
import com.template.coe.demo.fileupload.exception.FileStorageException;
import com.template.coe.demo.fileupload.exception.MyFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    private final Path fileUploadLocation;

    public FileUploadService(FileStorageProperties fileStorageProperties) {
        this.fileUploadLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileUploadLocation);
        } catch (IOException e) {
            throw new FileStorageException("Could not create the directory", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileUploadLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        Path filePath = this.fileUploadLocation.resolve(fileName).normalize();

        try {
            UrlResource urlResource = new UrlResource(filePath.toUri());
            if (urlResource.exists()) {
                return urlResource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new MyFileNotFoundException("File not found " + fileName);
        }
    }
}
