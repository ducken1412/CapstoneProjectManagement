package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.dto.ListLecturersDTO;
import com.fpt.entity.Users;
@Repository
public interface ListLecturers extends JpaRepository<Users, String>{
	
//	@Query("select new com.fa.dto.UserDTO(u) From Users u  where status = ?1")
	public List<ListLecturersDTO> findAllLecturersDTOByStatus(Integer status);

}
