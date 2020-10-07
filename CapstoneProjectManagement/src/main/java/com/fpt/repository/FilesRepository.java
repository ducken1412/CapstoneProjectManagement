
package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fpt.entity.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer>{
}
