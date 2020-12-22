package com.fpt.dto;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Semesters;
import com.fpt.entity.Sites;
import com.fpt.entity.Status;

public interface UserManagementDTO {
    String getId();
    String getUserName();
    String getFirstName();
    String getLastName();
    Integer getGender();
    String getPhone();
    String getEmail();
    String getStatus();
    String getNameCapstone();
    String getSemester();
    String getSite();
    String getImage();
    Integer getCapstoneId();
    String getCreatedDate();
}
