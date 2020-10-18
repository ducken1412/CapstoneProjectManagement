package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Profession;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {

}
