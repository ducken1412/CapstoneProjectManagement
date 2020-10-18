package com.fpt.dto;

import java.util.List;

public class UserRoleDTO {

	private String id;
	private String username;
	private List<String> role;

	public UserRoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserRoleDTO(String id,String username, List<String> role) {
		super();
		this.id = id;
		this.username = username;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRole() {
		return role;
	}

	public void setRole(List<String> role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserRoleDTO [username=" + username + ", role=" + role + "]";
	}

}
