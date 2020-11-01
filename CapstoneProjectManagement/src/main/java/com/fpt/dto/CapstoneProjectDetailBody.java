package com.fpt.dto;

public class CapstoneProjectDetailBody {
    private Integer id;
    private String description_action;
    private Integer capstone_project_id;
    private Integer status_id;
    private String user_id;
    private String email;
    private String first_name;
    private Integer gender;
    private String image;
    private String last_name;
    private String phone;
    private String user_name;
    private Integer roleid;
    private String rolename;
    private String nameStatus;

    public CapstoneProjectDetailBody() {

    }
    public CapstoneProjectDetailBody(Integer id, String description_action, Integer capstone_project_id, Integer status_id, String user_id, String email, String first_name, Integer gender, String image, String last_name, String phone, String user_name, Integer roleid, String rolename, String nameStatus) {
        super();
        this.id = id;
        this.description_action = description_action;
        this.capstone_project_id = capstone_project_id;
        this.status_id = status_id;
        this.user_id = user_id;
        this.email = email;
        this.first_name = first_name;
        this.gender = gender;
        this.image = image;
        this.last_name = last_name;
        this.phone = phone;
        this.user_name = user_name;
        this.roleid = roleid;
        this.rolename = rolename;
        this.nameStatus = nameStatus;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription_action() {
        return description_action;
    }

    public void setDescription_action(String description_action) {
        this.description_action = description_action;
    }

    public Integer getCapstone_project_id() {
        return capstone_project_id;
    }

    public void setCapstone_project_id(Integer capstone_project_id) {
        this.capstone_project_id = capstone_project_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getNameStatus() {
        return nameStatus;
    }

    public void setNameStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }
}
