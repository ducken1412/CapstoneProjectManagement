package com.fpt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	public Optional<Status> findById(Integer id);
}
