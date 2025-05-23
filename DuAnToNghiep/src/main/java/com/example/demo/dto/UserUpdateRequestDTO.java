package com.example.demo.dto;

public class UserUpdateRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;

    public UserUpdateRequestDTO() {
        // Constructor không tham số
    }

    public UserUpdateRequestDTO(String email, String firstName, String lastName, String phone, String image) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.image = image;
    }

    // Getters và Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}