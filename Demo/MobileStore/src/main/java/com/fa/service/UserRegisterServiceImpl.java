package com.fa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.entity.UserRegister;
import com.fa.repository.UserRegisterRepository;

@Service
public class UserRegisterServiceImpl {

  @Autowired
  private UserRegisterRepository userRegisterRepository;

  public UserRegisterServiceImpl(
      UserRegisterRepository userRegisterRepository) {
    super();
    this.userRegisterRepository = userRegisterRepository;
  }

  public UserRegisterServiceImpl() {
  }

  public UserRegister findById(int id) {
    return userRegisterRepository.findById(id);
  }

  public void saveUserRegister(UserRegister userRegister) {
    userRegisterRepository.save(userRegister);
  }
}
