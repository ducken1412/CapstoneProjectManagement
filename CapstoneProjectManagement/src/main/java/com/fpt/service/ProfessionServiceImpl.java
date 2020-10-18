package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Profession;
import com.fpt.repository.ProfessionRepository;

@Service
public class ProfessionServiceImpl implements ProfessionService {

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	public List<Profession> findAll() {
		return professionRepository.findAll();
	}

}
