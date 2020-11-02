package com.fpt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Status;
import com.fpt.repository.StatusRepository;

@Service
public class StatusServiceImpl implements StatusService{
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public Status getStatusById(int id) {
		Status status = statusRepository.findById(id).orElse(null);	
		return status;
	}

	@Override
	public Status findByName(String name) {
		return statusRepository.findByName(name);
	}

	@Override
	public List<Status> getAll() {
		List<Status>  status = statusRepository.findAll();
		return  status;
	}



}
