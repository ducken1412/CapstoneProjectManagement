package com.fpt.service;

import org.springframework.stereotype.Service;

import com.fpt.entity.CapstoneProjects;

@Service
public interface CapstoneProjectService {
	boolean saveRegisterProject(CapstoneProjects capstoneProjects);
}
