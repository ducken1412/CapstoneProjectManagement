package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.fpt.dto.ListLecturersDTO;
import com.fpt.dto.UserDTO;
import com.fpt.entity.Users;

@Service

public interface ListLecturersService {
	List<Users> getAllLecturersDTOActive();
}
