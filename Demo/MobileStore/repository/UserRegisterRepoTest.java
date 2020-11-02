package com.fa.repository;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.fa.entity.UserRegister;
import com.fa.service.UserRegisterServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserRegisterRepoTest {

  @Autowired
  private static UserRegisterServiceImpl userService;
  
  @Autowired
  private static UserRegisterRepository userRegisterRepository;
  
  @Test
  public void findByIdSuccess() {
    
    UserRegister userRegister = userService.findById(11);
    System.out.println(userRegister);
   // when(mailService.confirmEmail("1", "dat123@yopmail.com")).thenReturn(1);
    //Why null?
    assertNull(userRegister);
  }
  
  public static void main(String[] args) {
    UserRegisterServiceImpl userService1 = new UserRegisterServiceImpl(userRegisterRepository);
    UserRegister userRegister  = userService1.findById(11);
    System.out.println(userRegister);
  }
  

}
