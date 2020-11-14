package com.fpt.dto;

import com.fpt.entity.Users;

import java.util.List;

public class ContactDTO {
    private String username;
    private String email;
    private String phone;
    private String role;


    public ContactDTO() {
    }

    public ContactDTO(String username, String email, String phone, String role) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
