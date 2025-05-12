package com.example.demo.controller;

import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.UserManagementDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Lấy danh sách người dùng với phân trang (cho admin)
    @GetMapping
    public ResponseEntity<PagedResponseDTO<UserManagementDTO>> getAllUsersForAdmin(
            @RequestParam String currentUserRole,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) Integer lockUserId,
            @RequestParam(required = false) String searchQuery) {
        try {
            PagedResponseDTO<UserManagementDTO> users;
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                users = userService.searchUsers(currentUserRole, searchQuery, page, size);
            } else {
                users = userService.getUserList(currentUserRole, lockUserId, page, size);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    // Mở khóa tài khoản người dùng
    @PostMapping("/{userId}/unlock")
    public ResponseEntity<PagedResponseDTO<UserManagementDTO>> unlockUser(
            @PathVariable int userId,
            @RequestParam String currentUserRole,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            PagedResponseDTO<UserManagementDTO> users = userService.unlockUser(currentUserRole, userId, page, size);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    // Đăng ký người dùng mới
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            UserRegisterDTO registeredUser = userService.registerUser(userRegisterDTO);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}