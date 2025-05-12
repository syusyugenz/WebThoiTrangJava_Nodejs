package com.example.demo.service;

import com.example.demo.dto.ChangePasswordDTO;
import com.example.demo.dto.PagedResponseDTO;
import com.example.demo.dto.PasswordResetDTO;
import com.example.demo.dto.UserDisplayDTO;
import com.example.demo.dto.UserManagementDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.dto.UserUpdateRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CloudinaryService cloudinaryService;

    // Phương thức lấy danh sách người dùng với phân trang
    public PagedResponseDTO<UserManagementDTO> getUserList(String currentUserRole, Integer lockUserId, int page, int size) {
        if (!"admin".equalsIgnoreCase(currentUserRole)) {
            throw new SecurityException("Chỉ admin mới có quyền truy cập danh sách người dùng");
        }

        // Nếu có yêu cầu khóa tài khoản
        if (lockUserId != null) {
            User userToLock = userRepository.findByUserId(lockUserId);
            if (userToLock != null && "user".equalsIgnoreCase(userToLock.getRole())) {
                userToLock.setLocked(true);
                userRepository.save(userToLock);
            } else {
                throw new IllegalArgumentException("Không thể khóa tài khoản: Người dùng không tồn tại hoặc không phải user");
            }
        }

        // Phân trang và sắp xếp theo userId giảm dần
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        Page<User> usersPage = userRepository.findAll(pageable);

        // Chuyển đổi thành UserManagementDTO
        List<UserManagementDTO> userList = usersPage.getContent().stream()
                .map(user -> new UserManagementDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone(),
                        user.getPoint(),
                        user.getImage(),
                        user.getRole(),
                        user.isLocked()
                ))
                .collect(Collectors.toList());

        // Tạo PagedResponseDTO
        return new PagedResponseDTO<>(
                userList,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );
    }

    // Phương thức mở khóa tài khoản
    public PagedResponseDTO<UserManagementDTO> unlockUser(String currentUserRole, Integer userId, int page, int size) {
        if (!"admin".equalsIgnoreCase(currentUserRole)) {
            throw new SecurityException("Chỉ admin mới có quyền mở khóa tài khoản");
        }

        if (userId != null) {
            User userToUnlock = userRepository.findByUserId(userId);
            if (userToUnlock != null && "user".equalsIgnoreCase(userToUnlock.getRole())) {
                userToUnlock.setLocked(false);
                userRepository.save(userToUnlock);
            } else {
                throw new IllegalArgumentException("Không thể mở khóa tài khoản: Người dùng không tồn tại hoặc không phải user");
            }
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        Page<User> usersPage = userRepository.findAll(pageable);

        List<UserManagementDTO> userList = usersPage.getContent().stream()
                .map(user -> new UserManagementDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone(),
                        user.getPoint(),
                        user.getImage(),
                        user.getRole(),
                        user.isLocked()
                ))
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(
                userList,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );
    }

    // Phương thức tìm kiếm người dùng theo username hoặc email
    public PagedResponseDTO<UserManagementDTO> searchUsers(String currentUserRole, String query, int page, int size) {
        if (!"admin".equalsIgnoreCase(currentUserRole)) {
            throw new SecurityException("Chỉ admin mới có quyền truy cập danh sách người dùng");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        Page<User> usersPage;

        if (query != null && !query.trim().isEmpty()) {
            usersPage = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }

        List<UserManagementDTO> userList = usersPage.getContent().stream()
                .map(user -> new UserManagementDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhone(),
                        user.getPoint(),
                        user.getImage(),
                        user.getRole(),
                        user.isLocked()
                ))
                .collect(Collectors.toList());

        return new PagedResponseDTO<>(
                userList,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Tên đăng nhập hoặc mật khẩu không đúng");
        }
        if (user.isLocked()) {
            throw new IllegalArgumentException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ admin để mở khóa.");
        }
        return user;
    }

    public UserRegisterDTO registerUser(UserRegisterDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng sử dụng email khác!");
        }

        if (userDTO.getPassword() == null || userDTO.getEmail() == null ||
                userDTO.getFirstName() == null || userDTO.getLastName() == null) {
            throw new IllegalArgumentException("Các trường không được để trống");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhone(userDTO.getPhone());
        user.setPoint(userDTO.getPoint() != null ? userDTO.getPoint() : 0);

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByUsername(changePasswordDTO.getUsername());
        if (user == null || !passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Tên đăng nhập hoặc mật khẩu cũ không đúng");
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }

    public UserRegisterDTO convertToDTO(User user) {
        UserRegisterDTO userDTO = new UserRegisterDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhone(user.getPhone());
        userDTO.setPoint(user.getPoint());
        return userDTO;
    }

    public void resetPassword(PasswordResetDTO passwordResetDTO) {
        User user = userRepository.findByEmail(passwordResetDTO.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Email không tồn tại");
        }

        String newPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);

        sendEmail(user.getEmail(), newPassword);
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmail(String to, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Đặt lại mật khẩu");
        message.setText("Mật khẩu mới của bạn là: " + newPassword);
        mailSender.send(message);
    }

    public UserDisplayDTO getUserById(int userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }
        return convertToDisplayDTO(user);
    }

    public UserDisplayDTO convertToDisplayDTO(User user) {
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

    public UserRegisterDTO updateUserInfo(int userId, UserUpdateRequestDTO userUpdateRequest) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        user.setEmail(userUpdateRequest.getEmail());
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setPhone(userUpdateRequest.getPhone());

        user = userRepository.save(user);
        return convertToDTO(user);
    }

    public UserRegisterDTO updateUserImage(int userId, MultipartFile imageFile) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                Map uploadResult = cloudinaryService.uploadFile(imageFile);
                String imageUrl = (String) uploadResult.get("secure_url");
                user.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Không thể tải lên hình ảnh", e);
            }
        }

        user = userRepository.save(user);
        return convertToDTO(user);
    }
}