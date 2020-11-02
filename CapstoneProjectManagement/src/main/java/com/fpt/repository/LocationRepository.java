package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Locations;

@Repository
public interface LocationRepository extends JpaRepository<Locations, Integer>{
	@Query(" FROM Locations ru WHERE ru.user.id = ?1")
	List<Locations> getLocationByUserId(String userId);
}
