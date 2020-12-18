package com.fpt.service;

import java.util.List;

import com.fpt.entity.Users;
import org.springframework.stereotype.Service;

import com.fpt.entity.Status;

@Service
public interface StatusService {
	Status getStatusById(int id);
	Status findByName(String name);
	List<Status> getAll();
	Status findStatusByUserId(String userId);
}
