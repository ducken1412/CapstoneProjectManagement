package com.fpt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.entity.Locations;


@Service
public interface LocationService {
	List<Locations> getLocationByUserId(String userId);
	
}
