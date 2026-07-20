package com.elevate.ElevateBackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadProfileImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select an image.");
        }

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

            String extension = "";

            int index = originalFileName.lastIndexOf(".");

            if (index > 0) {
                extension = originalFileName.substring(index);
            }

            String fileName = UUID.randomUUID() + extension;

            Path targetPath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/profile/" + fileName;

        } catch (IOException e) {

            throw new RuntimeException("Unable to upload image.", e);

        }
    }

    public void deleteImage(String imagePath) {

        if (imagePath == null || imagePath.isBlank()) {
            return;
        }

        try {

            String fileName = Paths.get(imagePath).getFileName().toString();

            Path path = Paths.get(uploadDir).resolve(fileName);

            Files.deleteIfExists(path);

        } catch (IOException ignored) {

        }
    }

}