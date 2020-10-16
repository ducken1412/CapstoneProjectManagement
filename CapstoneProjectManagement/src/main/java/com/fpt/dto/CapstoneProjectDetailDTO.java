package com.fpt.dto;

import java.util.Map;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Status;
import com.fpt.entity.Users;

public class CapstoneProjectDetailDTO {
	private Integer id;
	private CapstoneProjects capstoneProject;
	private Users user;
	private Status status;
	public static Map<String, Object> detailMap;
	
	public CapstoneProjectDetailDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public CapstoneProjectDetailDTO(CapstoneProjects capstoneProject, Users user, Status status) {
		super();
		this.capstoneProject = capstoneProject;
		this.user = user;
		this.status = status;
	}
	public CapstoneProjectDetailDTO(Integer id, CapstoneProjects capstoneProject, Users user, Status status) {
		super();
		this.id = id;
		this.capstoneProject = capstoneProject;
		this.user = user;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CapstoneProjects getCapstoneProject() {
		return capstoneProject;
	}
	public void setCapstoneProject(CapstoneProjects capstoneProject) {
		this.capstoneProject = capstoneProject;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	
}
