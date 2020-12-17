package com.fpt.dto;

import com.fpt.entity.CapstoneProjectDetails;
import com.fpt.entity.Status;
import com.fpt.entity.Users;

public class MemberEditDTO {
    private String username;
    private String role;
    private String status;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MemberEditDTO() {
    }

    public MemberEditDTO(String username, String role, String status) {
        this.username = username;
        this.role = role;
        this.status = status;
    }

    public MemberEditDTO(Users user) {
        this.username = user.getUsername();
    }
    public MemberEditDTO(Status status){this.status = status.getName();}

}
