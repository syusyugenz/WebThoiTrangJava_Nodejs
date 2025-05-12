package com.example.demo.dto;

public class UserManagementDTO {
    private int userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Integer point;
    private String image;
    private String role; // Thêm role để admin biết quyền của user
    private boolean isLocked; // Trạng thái khóa

    // Constructor
    public UserManagementDTO(int userId, String username, String email, String firstName, 
                             String lastName, String phone, Integer point, String image, 
                             String role, boolean isLocked) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.point = point;
        this.image = image;
        this.role = role;
        this.isLocked = isLocked;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getPoint() { return point; }
    public void setPoint(Integer point) { this.point = point; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public boolean isLocked() { return isLocked; }
    public void setLocked(boolean isLocked) { this.isLocked = isLocked; }
}