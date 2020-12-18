package com.fpt.service;

import java.util.List;

import com.fpt.dto.UserManagementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.entity.Users;
import com.fpt.repository.UserRepository;
import com.fpt.repository.UserRolesRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRolesRepository userRoleRepository;

	@Override
	public Users findById(String id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<Users> getAllUser() {
		return userRepository.findAll();
	}
	@Override
	public List<UserManagementDTO> getAllUserStudent(String site, String semester) {
		return userRepository.getAllUserStudent(site, semester);
	}

	public boolean deleteUser(String id) {
		try {
			userRepository.deleteById(id);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean save(Users user) {
		try {
			userRepository.save(user);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveAll(List<Users> user) {
		try {
			userRepository.saveAll(user);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Users> findAll() {
		return userRepository.findAll();
}

	@Override
	public List<Users> getUserByRoleId(Integer id) {
		// TODO Auto-generated method stub
		return userRoleRepository.getUserByRoleId(id);
	}


	@Override
	public List<Users> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Users findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public Page<Users> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
		return userRepository.getUserByRoleId(secondPageWithFiveElements, 4);
	}

	@Override
	public Page<Users> findPaginatedNotLecture2Booked(Pageable pageable, String lId1, String lId2) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
		return userRepository.getLectureNoteBooked2ByRoleId(secondPageWithFiveElements, 4, lId1,lId2);
	}

	@Override
	public Page<Users> findPaginatedNotLectureBooked(Pageable pageable, String lId) {
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
		return userRepository.getLectureNoteBookedByRoleId(secondPageWithFiveElements, 4, lId);
	}

	@Override
	public Users getUserByUserName(String id) {
		return userRepository.getUserByUserName(id);
	}

	@Override
	public List<Users> getUserByUserRoleAndProjectId(Integer id, Integer cid) {
		return userRepository.getUserByUserRoleAndProjectId(id,cid);
	}


	@Override
	public List<UserManagementDTO> getUserStudentByStatusId(Integer id) {
		return userRepository.getUserStudentByStatusId(id);
	}

	@Override
	public Integer countStudent(Integer id,String site, String semester) {
		return userRepository.countStudent(id,site,semester);
	}

	@Override
	public List<UserManagementDTO> getAllUserStudentHasNoTeam(String site, String semester) {
		return userRepository.getAllUserStudentHasNoTeam(site,semester);
	}

	@Override
	public Integer countStudentHasNoTeam(String site, String semester) {
		return userRepository.countStudentHasNoTeam(site, semester);
	}

	@Override
	public Integer countAllStudent(String site, String semester) {
		return userRepository.countAllStudent(site,semester);
	}

	@Override
	public Integer countStudentEligibleCapstone(String site, String semester) {
		return userRepository.countStudentEligibleCapstone(site,semester);
	}


}
