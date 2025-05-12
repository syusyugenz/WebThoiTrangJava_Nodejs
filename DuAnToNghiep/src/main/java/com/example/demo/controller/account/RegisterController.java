package com.example.demo.controller.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.response.ResponseData;
import com.example.demo.service.UserService;

@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseData<UserRegisterDTO>> registerUser(@RequestBody UserRegisterDTO userDTO) {
        try {
            UserRegisterDTO registeredUser = userService.registerUser(userDTO);
            ResponseData<UserRegisterDTO> response = new ResponseData<>(true, "Đăng ký người dùng thành công!", List.of(registeredUser));
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            ResponseData<UserRegisterDTO> response = new ResponseData<>(false, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            ResponseData<UserRegisterDTO> response = new ResponseData<>(false, "Đã xảy ra lỗi trong quá trình đăng ký.", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}