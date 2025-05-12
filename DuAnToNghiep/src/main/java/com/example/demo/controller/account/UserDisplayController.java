package com.example.demo.controller.account;

import com.example.demo.dto.UserDisplayDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.UserUpdateRequestDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api")
public class UserDisplayController {

    @Autowired
    private UserService userService;

    // Endpoint để lấy thông tin người dùng theo userId
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDisplayDTO> getUserById(@PathVariable int userId) {
        UserDisplayDTO userDisplayDTO = userService.getUserById(userId);
        return ResponseEntity.ok(userDisplayDTO);
    }
    
    @PutMapping("/users/{userId}/info")
    public ResponseEntity<UserRegisterDTO> updateUserInfo(
            @PathVariable int userId,
            @RequestBody UserUpdateRequestDTO userUpdateRequest) {

        UserRegisterDTO updatedUser = userService.updateUserInfo(userId, userUpdateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint để cập nhật hình ảnh người dùng
    @PutMapping("/users/{userId}/image")
    public ResponseEntity<UserRegisterDTO> updateUserImage(
            @PathVariable int userId,
            @RequestParam("imageFile") MultipartFile imageFile) {

        UserRegisterDTO updatedUser = userService.updateUserImage(userId, imageFile);
        return ResponseEntity.ok(updatedUser);
    }
}