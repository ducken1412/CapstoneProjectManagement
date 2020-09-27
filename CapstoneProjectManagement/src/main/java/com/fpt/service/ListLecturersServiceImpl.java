package com.fpt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.repository.ListLecturers;
import com.fpt.service.ListLecturersServiceImpl;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.repository.ListLecturers;;

@Service

public class ListLecturersServiceImpl implements ListLecturersService{
	
private static final Logger LOGGER = LoggerFactory.getLogger(ListLecturersServiceImpl.class);
	
	@Autowired
	private ListLecturers lislecturersRepository;
	
	
	@Override
	public List<ListLecturersDTO> getAllLecturersDTOActive() {
		return lislecturersRepository.findAllLecturersDTOByStatus(1);
	}

}
