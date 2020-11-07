package com.fpt.repository;

import java.util.List;
import java.util.Optional;

import com.fpt.dto.NotificationDTO;
import com.fpt.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    //get notification by user id

    //	List<Notifications> findByReceivers_Id(String id);
    @Query("select new com.fpt.dto.NotificationDTO(n) from Notifications n where n.type = 'all' order by n.created_date desc")
    List<NotificationDTO> getAllTitle();

    Notifications findAllById(Integer id);

    //get top 6
    @Query(value = "SELECT * FROM notifications AS n  WHERE n.type = 'all' ORDER BY n.created_date DESC LIMIT 6", nativeQuery = true)
    List<Notifications> getTop5NotificationsByCreatedDate();

    //paggin notification global
    @Query("select n from Notifications n where n.type = 'all' order by n.created_date desc")
    Page<Notifications> getAllTitlePaggin(Pageable pageable);

    @Query("select n from Notifications n , NotificationDetails no where n.id = no.notification.id and no.user.id = ?1 order by n.created_date desc")
    Page<Notifications> getAllTitlePagginByUserId(Pageable pageable, String id);
}
