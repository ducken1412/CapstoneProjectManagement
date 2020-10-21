package com.fpt.dto;

public class MemberDTO {
    private String username;
    private String role;



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

    public MemberDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public MemberDTO() {
    }
}
