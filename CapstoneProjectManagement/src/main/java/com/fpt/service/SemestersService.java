package com.fpt.service;

import com.fpt.entity.Semesters;

import java.util.List;

public interface SemestersService {

	List<Semesters> findAll();

	Semesters findByName(String name);

	Semesters findById(Integer id);

}
