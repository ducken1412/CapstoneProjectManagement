package com.fpt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer>{
	//get notification by user id
	
//	List<Notifications> findByReceivers_Id(String id);
}
