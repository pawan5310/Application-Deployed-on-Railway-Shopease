package com.jsp.ecommerce.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class CloudinaryHelper {

    // Directory where images will be stored
    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public String saveImage(MultipartFile image) {
        try {
            // Create uploads directory if it doesn't exist
            File directory = new File(UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique filename
            String originalFilename = image.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            String filePath = UPLOAD_DIR + uniqueFilename;
            Path path = Paths.get(filePath);
            Files.write(path, image.getBytes());

            // Return the URL that can be used in HTML
            return "/uploads/" + uniqueFilename;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}