package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.fpt.dto.ListLecturersDTO;

@Service

public interface ListLecturersService {
	List<ListLecturersDTO> getAllLecturersDTOActive();
}
