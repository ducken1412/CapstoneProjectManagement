package com.fa.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RoleUser")
public class RoleUser implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private RoleUserKey roleUserKey;

	public RoleUser() {
		super();
	}

	public RoleUser(RoleUserKey roleUserKey) {
		super();
		this.roleUserKey = roleUserKey;
	}

	public RoleUserKey getRoleUserKey() {
		return roleUserKey;
	}

	public void setRoleUserKey(RoleUserKey roleUserKey) {
		this.roleUserKey = roleUserKey;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "RoleUser [roleUserKey=" + roleUserKey + "]";
	}

}
