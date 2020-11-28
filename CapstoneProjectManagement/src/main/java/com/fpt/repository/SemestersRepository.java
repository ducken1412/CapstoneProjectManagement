package com.fpt.repository;

import com.fpt.entity.Semesters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestersRepository extends JpaRepository<Semesters, Integer> {
    Semesters findByName(String name);
}
