package com.fpt.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import com.fpt.dto.ListLecturersDTO;
import com.fpt.dto.UserDTO;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;

@Service

public interface ListLecturersService {
	List<Users> getAllLecturersDTOActive();
	//Page<Users> listlecturerPaginated(Pageable pageable);
}
