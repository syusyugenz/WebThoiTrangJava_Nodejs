package com.example.demo.util;

import com.example.demo.dto.UserDisplayDTO;
import com.example.demo.entity.User;

public class UserDisplayMapper {
    // Chuyển từ User entity sang UserDisplayDTO
    public static UserDisplayDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDisplayDTO(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getPoint(),
            user.getImage()
        );
    }

    // Chuyển từ UserDisplayDTO sang User entity
    public static User toEntity(UserDisplayDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setPoint(dto.getPoint());
        user.setImage(dto.getImage());
        // Không set password, role, voucherUsers, reviews vì DTO không bao gồm
        return user;
    }
}