package com.fpt.service;

import com.fpt.entity.Profession;
import com.fpt.entity.Sites;

import java.util.List;

public interface SitesService {

	List<Sites> findAll();

	Sites findByName(String name);

	Sites findById(Integer id);

}
