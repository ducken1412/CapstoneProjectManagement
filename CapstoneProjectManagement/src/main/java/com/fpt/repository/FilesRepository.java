
package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fpt.entity.Files;

import java.util.List;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer>{

    //get File By Post Id
    @Query("SELECT f FROM Files f where f.post.id = ?1")
    List<Files> getFileByPostId(Integer id);
}
