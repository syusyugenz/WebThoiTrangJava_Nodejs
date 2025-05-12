package com.example.demo.controller.account;

import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.response.ResponseData;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api")
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<ResponseData<String>> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userService.changePassword(changePasswordDTO);
            return ResponseEntity.ok(ResponseData.success("Đổi mật khẩu thành công", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseData.failure(e.getMessage()));
        }
    }
}