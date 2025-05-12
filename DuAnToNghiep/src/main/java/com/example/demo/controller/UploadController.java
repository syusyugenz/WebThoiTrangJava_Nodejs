package com.example.demo.controller;

import com.example.demo.response.ResponseData;
import com.example.demo.service.CloudinaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
	private final CloudinaryService cloudinaryService;

    public UploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/image")
    public ResponseEntity<ResponseData<Map>> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map result = cloudinaryService.uploadFile(file);
        return ResponseEntity.ok(ResponseData.success("Upload thành công", List.of(result)));
    }
}