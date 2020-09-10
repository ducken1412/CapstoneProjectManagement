package com.fa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.entity.UserRegister;

public interface UserRegisterRepository extends JpaRepository<UserRegister, Integer>{
  /**
   * Find user register by id
   * @param id
   * @return
   */
  public UserRegister findById(int id);
  
}
