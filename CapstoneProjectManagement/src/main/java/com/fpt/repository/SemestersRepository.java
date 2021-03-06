package com.fpt.repository;

import com.fpt.entity.CapstoneProjects;
import com.fpt.entity.Reports;
import com.fpt.entity.Semesters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestersRepository extends JpaRepository<Semesters, Integer> {
    Semesters findByName(String name);

    @Query("SELECT ru.capstoneProject.semester FROM CapstoneProjectDetails ru WHERE ru.user.id = ?1")
    Semesters getSemesterByUserId(String userId);
}
