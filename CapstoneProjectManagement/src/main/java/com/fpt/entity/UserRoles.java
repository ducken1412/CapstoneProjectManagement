package com.fpt.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserRoles")
public class UserRoles implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UserRoleKey userRoleKey;
	
	public UserRoles() {
		super();
	}
	public UserRoles(UserRoleKey userRoleKey) {
		super();
		this.userRoleKey = userRoleKey;
	}
	public UserRoleKey getUserRoleKey() {
		return userRoleKey;
	}
	public void setUserRoleKey(UserRoleKey userRoleKey) {
		this.userRoleKey = userRoleKey;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
