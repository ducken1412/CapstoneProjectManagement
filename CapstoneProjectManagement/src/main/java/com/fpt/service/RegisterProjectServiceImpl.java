package com.fpt.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;
import com.fpt.repository.RegisterProjectRepository;


@Service
public class RegisterProjectServiceImpl implements RegisterProjectService {

	@Autowired
	private RegisterProjectRepository registerProjectServiceImpl;
	
	@Override
	public boolean saveRegisterProject(CapstoneProjects capstoneProjects) {
		try {
			registerProjectServiceImpl.save(capstoneProjects);
			return true;
		} catch (Exception e) {
			System.out.println("error");
		}
		return false;
	}
}
