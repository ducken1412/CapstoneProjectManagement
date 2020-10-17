package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.repository.CapstoneProjectRepository;

@Service
public class CapstoneProjectServiceImpl implements CapstoneProjectService {
	@Autowired
	private CapstoneProjectRepository capstoneProjectRepository;

	@Override
	public List<String> getCapstoneProjectNameByUserId(String userId) {
		return capstoneProjectRepository.getCapstoneProjectNameByUserId(userId);
	}

	@Override
	public boolean saveRegisterProject(CapstoneProjects capstoneProjects) {
		try {
			capstoneProjectRepository.save(capstoneProjects);
			return true;
		} catch (Exception e) {
			System.out.println("error add capstone project");
		}
		return false;
	}

	@Override
	public CapstoneProjects getCapstonProjectById(Integer id) {
		CapstoneProjects cp = capstoneProjectRepository.findById(id).orElse(null);
		return cp;
	}

	@Override
	public List<CapstoneProjects> getAllProject() {
		return capstoneProjectRepository.findAll();
	}
}
