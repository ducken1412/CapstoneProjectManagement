package com.fpt.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.repository.CapstoneProjectRepository;


@Service
public class CapstoneProjectServiceImpl implements CapstoneProjectService {

	@Autowired
	private CapstoneProjectRepository registerProjectServiceImpl;
	
	@Override
	public boolean saveRegisterProject(CapstoneProjects capstoneProjects) {
		try {
			registerProjectServiceImpl.save(capstoneProjects);
			return true;
		} catch (Exception e) {
			System.out.println("error add capstone project");
		}
		return false;
	}

	@Override
	public CapstoneProjects getCapstonProjectById(Integer id) {
		CapstoneProjects cp = registerProjectServiceImpl.findById(id).orElse(null);
		return cp;
	}
}
