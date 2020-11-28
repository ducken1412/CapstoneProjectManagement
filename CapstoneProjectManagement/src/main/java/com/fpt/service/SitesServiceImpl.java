package com.fpt.service;

import com.fpt.entity.Profession;
import com.fpt.entity.Sites;
import com.fpt.repository.ProfessionRepository;
import com.fpt.repository.SitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitesServiceImpl implements SitesService {

	@Autowired
	private SitesRepository sitesRepository;

	@Override
	public List<Sites> findAll() {
		return sitesRepository.findAll();
	}

	@Override
	public Sites findByName(String name) {
		return sitesRepository.findByName(name);
	}

	@Override
	public Sites findById(Integer id) {
		return sitesRepository.findById(id).orElse(null);
	}

}
