package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpt.entity.Locations;


public interface LocationRepository extends JpaRepository<Locations, String>{
//	@Query("SELECT ru.location.name  WHERE ru.user.id = ?1")
//	List<String> getLocationByUserId(String UserId);
}
