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
}
