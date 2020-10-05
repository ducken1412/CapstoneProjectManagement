package com.fpt.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fpt.entity.Users;

@Service

public interface LecturersService {
	List<Users> getAllLecturersDTOActive();
	//Page<Users> listlecturerPaginated(Pageable pageable);
	Page<Users> findPaginated(Pageable pageable);
}
