//package com.example.demo.service;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Service
//public class CloudinaryService {
//    private final Cloudinary cloudinary;
//
//    public CloudinaryService(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//    }
//
//    public Map uploadFile(MultipartFile file) throws IOException {
//        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//    }
//}

package com.example.demo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
	
    private final Cloudinary cloudinary;


    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map uploadFile(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    }

    public void deleteFile(String publicId) throws IOException {
        if (publicId == null || publicId.isEmpty()) {
            throw new IllegalArgumentException("Public ID cannot be null or empty");
        }
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        if (!"ok".equals(result.get("result"))) {
            throw new IOException("Failed to delete image on Cloudinary: " + publicId);
        }
    }
}