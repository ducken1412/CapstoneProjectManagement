package com.fpt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Locations;
import com.fpt.repository.CapstoneProjectRepository;
import com.fpt.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	@Autowired
	private LocationRepository locationRepository;
	@Override
	public List<Locations> getLocationByUserId(String userId) {
		return locationRepository.getLocationByUserId(userId);
	}
	
	

	

}
