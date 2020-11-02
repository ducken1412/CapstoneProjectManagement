package com.fa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "[UsersRegister]")
public class UserRegister {

  @Id
  @Column(name = "id")
  private int id;
  @Column(name = "code")
  private String code;
  @Column(name = "date")
  private Date date;

  public UserRegister() {
  }

  public UserRegister(int id, String code, Date date) {
    super();
    this.id = id;
    this.code = code;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}
