
package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.fpt.entity.Files;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FilesRepository extends JpaRepository<Files, Integer>{
    @Transactional
    @Modifying
    Integer deleteAllByPostId(Integer postId);
}
