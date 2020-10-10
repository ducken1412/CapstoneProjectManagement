package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer>{
	
}
