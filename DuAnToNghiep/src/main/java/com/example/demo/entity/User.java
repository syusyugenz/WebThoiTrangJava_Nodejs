//
//
//package com.example.demo.entity;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "Users")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int userId; // PK
//
//    private String username;
//    private String password;
//    private String email;
//    private String role;
//    private String firstName;
//    private String lastName;
//    private String phone;
//    private int point;
//    
//    private String image; // Thêm cột image
//
//
//    public User() {
//        // Constructor không tham số
//    }
//
//    // Getters and Setters
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public int getPoint() {
//        return point;
//    }
//
//    public void setPoint(int point) {
//        this.point = point;
//    }
//
//    public String getImage() { // Getter cho image
//        return image;
//    }
//
//    public void setImage(String image) { // Setter cho image
//        this.image = image;
//    }
//}

package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId; // PK

    private String username;
    private String password;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String phone;
    private int point;
    
    private String image; // Cột image

    @Column(name = "is_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isLocked = false; // Thêm thuộc tính isLocked, mặc định là false

    public User() {
        // Constructor không tham số
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter và Setter cho isLocked
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}