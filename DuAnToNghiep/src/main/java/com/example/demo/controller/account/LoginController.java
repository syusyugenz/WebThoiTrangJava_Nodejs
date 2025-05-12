//package com.example.demo.controller.account;
//
//
//import com.example.demo.dto.PasswordResetDTO;
//import com.example.demo.dto.UserRegisterDTO;
//import com.example.demo.entity.User;
//import com.example.demo.response.ResponseData;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(value = {"*"})
//@RestController
//@RequestMapping("/api")
//public class LoginController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<ResponseData<UserRegisterDTO>> login(@RequestBody UserRegisterDTO userDTO) {
//        try {
//            // Không mã hóa mật khẩu ở đây
//            User user = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
//            
//            if (user == null) {
//                return ResponseEntity.badRequest().body(ResponseData.failure("Tên đăng nhập hoặc mật khẩu không chính xác"));
//            }
//
//            UserRegisterDTO responseUserDTO = userService.convertToDTO(user);
//            return ResponseEntity.ok(ResponseData.success("Đăng nhập thành công", List.of(responseUserDTO)));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(ResponseData.failure(e.getMessage()));
//        }
//    }
//    
//    @PostMapping("/reset-password")
//    public ResponseEntity<ResponseData<String>> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
//        try {
//            userService.resetPassword(passwordResetDTO);
//            return ResponseEntity.ok(ResponseData.success("Mật khẩu mới đã được gửi đến email của bạn", null));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(ResponseData.failure(e.getMessage()));
//        }
//    }
//
//}

package com.example.demo.controller.account;

import com.example.demo.dto.PasswordResetDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.entity.User;
import com.example.demo.response.ResponseData;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = {"*"})
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<UserRegisterDTO>> login(@RequestBody UserRegisterDTO userDTO) {
        try {
            User user = userService.loginUser(userDTO.getUsername(), userDTO.getPassword());
            if (user == null) {
                return ResponseEntity.badRequest().body(ResponseData.failure("Tên đăng nhập hoặc mật khẩu không chính xác"));
            }

            UserRegisterDTO responseUserDTO = userService.convertToDTO(user);
            return ResponseEntity.ok(ResponseData.success("Đăng nhập thành công", List.of(responseUserDTO)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseData.failure(e.getMessage()));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseData<String>> resetPassword(@RequestBody PasswordResetDTO passwordResetDTO) {
        try {
            userService.resetPassword(passwordResetDTO);
            return ResponseEntity.ok(ResponseData.success("Mật khẩu mới đã được gửi đến email của bạn", null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseData.failure(e.getMessage()));
        }
    }
}