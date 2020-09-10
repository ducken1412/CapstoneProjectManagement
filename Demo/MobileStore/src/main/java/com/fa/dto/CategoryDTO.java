package com.fa.dto;

import java.util.Date;

import com.fa.entity.Category;
import com.fa.entity.Users;

public class CategoryDTO {

  private Integer id;
  private String name;
  private String description;
  private Date createDate;
  private Date updateTime;
  private Integer status;
  private Users createdBy;
  private Users updatedBy;
  
  public CategoryDTO() {
  }
  
  public CategoryDTO(Category category) {
    super();
    this.id = category.getId();
    this.name = category.getName();
    this.description = category.getDescription();
    this.createDate = category.getCreateDate();
    this.updateTime = category.getUpdateTime();
    this.status = category.getStatus();
    this.createdBy = category.getCreatedBy();
    this.updatedBy = category.getUpdatedBy();
  }
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }
  public Users getCreatedBy() {
    return createdBy;
  }
  public void setCreatedBy(Users createdBy) {
    this.createdBy = createdBy;
  }
  public Users getUpdatedBy() {
    return updatedBy;
  }
  public void setUpdatedBy(Users updatedBy) {
    this.updatedBy = updatedBy;
  }
  
  
}
