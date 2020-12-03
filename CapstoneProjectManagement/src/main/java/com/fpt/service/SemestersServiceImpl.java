package com.fpt.service;

import com.fpt.entity.Semesters;
import com.fpt.entity.Sites;
import com.fpt.repository.SemestersRepository;
import com.fpt.repository.SitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemestersServiceImpl implements SemestersService {

	@Autowired
	private SemestersRepository semestersRepository;

	@Override
	public List<Semesters> findAll() {
		return semestersRepository.findAll();
	}

	@Override
	public Semesters findByName(String name) {
		return semestersRepository.findByName(name);
	}

	@Override
	public Semesters findById(Integer id) {
		return semestersRepository.findById(id).orElse(null);
	}

	@Override
	public Semesters getSemesterByUserId(String userId) {
		return semestersRepository.getSemesterByUserId(userId);
	}

}
