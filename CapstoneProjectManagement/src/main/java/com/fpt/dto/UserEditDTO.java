package com.fpt.dto;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Reports;
import com.fpt.entity.Status;

import java.util.Date;
import java.util.List;

public class UserEditDTO {
    private String id;
    private String birthDate;
    private String phone;
    private String email;
    private String address;
    private String description;
    private String image;

    public UserEditDTO() {
    }

    public UserEditDTO(String id, String birthDate, String phone, String email, String address, String description, String image) {
        this.id = id;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
