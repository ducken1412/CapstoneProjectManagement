package com.fpt.service;

import java.util.List;

import com.fpt.entity.Profession;

public interface ProfessionService {

	List<Profession> findAll();

	Profession findByName(String name);

	Profession findById(Integer id);

}
