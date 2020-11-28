package com.fpt.repository;

import com.fpt.entity.Sites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SitesRepository extends JpaRepository<Sites, Integer> {
    Sites findByName(String name);
}
