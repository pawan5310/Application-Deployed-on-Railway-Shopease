package com.jsp.ecommerce.helper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryHelper {

    private final Cloudinary cloudinary;

    public CloudinaryHelper(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String saveImage(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    image.getBytes(),
                    ObjectUtils.emptyMap()
            );
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteImageByUrl(String imageUrl) {
        try {
            // Extract public_id from URL
            String publicId = imageUrl
                    .substring(imageUrl.indexOf("/upload/") + 8)
                    .replaceAll("v[0-9]+/", "")
                    .replaceAll("\\.[a-zA-Z]+$", "");

            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
