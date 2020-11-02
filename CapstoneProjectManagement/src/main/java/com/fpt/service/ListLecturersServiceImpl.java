package com.fpt.service;



import java.awt.print.Pageable;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.fpt.repository.UserRepository;
import com.fpt.service.ListLecturersServiceImpl;
import com.fpt.dto.ListLecturersDTO;
import com.fpt.dto.UserDTO;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;


@Service

public class ListLecturersServiceImpl implements ListLecturersService{
	
private static final Logger LOGGER = LoggerFactory.getLogger(ListLecturersServiceImpl.class);
	
	@Autowired
	private UserRepository listlecturerRepository;
	
	
	@Override
	public List<Users> getAllLecturersDTOActive() {
		return listlecturerRepository.findAll();
	}
	/*
	@Override
	public Page<Users> listlecturerPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Users> list;
		List<Users> posts = findAll();
		if (posts.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, posts.size());
			list = posts.subList(startItem, toIndex);
		}
		Page<Users> bookPage = new PageImpl<Posts>(list, PageRequest.of(currentPage, pageSize), posts.size());
		return bookPage;
	}
	*/
}
