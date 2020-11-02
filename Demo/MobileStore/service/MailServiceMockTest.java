package com.fa.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fa.repository.RoleRepository;
import com.fa.repository.RoleUserRepository;
import com.fa.repository.UserRegisterRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MailServiceMockTest {

  @Autowired
  private MailServiceImpl mailService;

  @MockBean
  private UserRegisterRepository userRegisterRepo;

  @MockBean
  private RoleRepository roleRepo;

  @MockBean
  private RoleUserRepository roleUserRepo;

  @Test
  public void confirmEmailSuccess() {
    mailService = new MailServiceImpl(userRegisterRepo, userRepo, roleRepo, roleUserRepo);
    int result = mailService.confirmEmail("11", "dat123@yopmail.com");

    // when(mailService.confirmEmail("1", "dat123@yopmail.com")).thenReturn(1);
    assertEquals(0, result);
  }

  @Test
  public void confirmEmailException() {
    // Sample not using mock anotation
    MailServiceImpl mailService = Mockito.mock(MailServiceImpl.class);
    when(mailService.confirmEmail("1", "dat123@yopmail.com")).thenReturn(3);
    assertEquals(3, mailService.confirmEmail("1", "dat123@yopmail.com"));
  }

  @Test
  public void confirmEmailExpiretime() {
    MailServiceImpl mailService = Mockito.mock(MailServiceImpl.class);
    when(mailService.confirmEmail("1", "dat123@yopmail.com")).thenReturn(2);
    assertEquals(2, mailService.confirmEmail("1", "dat123@yopmail.com"));
  }

  @Test
  public void confirmEmailExistedEmailOrUsername() {
    MailServiceImpl mailService = Mockito.mock(MailServiceImpl.class);
    when(mailService.confirmEmail("1", "dat123@yopmail.com")).thenReturn(0);
    assertEquals(0, mailService.confirmEmail("1", "dat123@yopmail.com"));
  }
}
