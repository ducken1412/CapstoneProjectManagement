package com.fpt.service;

import org.springframework.stereotype.Service;

import com.fpt.entity.Status;

@Service
public interface StatusService {
	Status getStatusById(int id);
}
