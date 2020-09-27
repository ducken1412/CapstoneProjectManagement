package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fpt.entity.Users;
@Repository
public interface UserRepository  extends JpaRepository<Users, String>{	

}
