package com.fa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.dto.UserDTO;
import com.fa.entity.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

	public Users findByEmail(String email);

	public Users findByUserName(String username);

	public Users findByUserNameAndStatus(String username, Integer status);

	public List<Users> findByStatus(Integer status);

	@Query("select new com.fa.dto.UserDTO(u) From Users u  where status = ?1")
	public List<UserDTO> findAllUserDTOByStatus(Integer status);

	public Users findByUserNameAndStatus(String username, int status);

	public Users findByEmailAndStatus(String email, int status);

	public Users findById(int id);
	
	public Users findByIdAndStatus(int id , int status);
	
	@Query("SELECT new com.fa.dto.UserDTO(u) From Users u  where id = ?1")
	public UserDTO getUser(int id);
	
	@Query("SELECT new com.fa.dto.UserDTO(u) From Users u  where userName = ?1")
	public UserDTO getUser(String userName);
}
