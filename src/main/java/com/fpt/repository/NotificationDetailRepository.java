package com.fpt.repository;

import com.fpt.entity.NotificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface NotificationDetailRepository extends JpaRepository<NotificationDetails, Integer> {
    @Query("select n from NotificationDetails n where n.user.id = ?1")
    List<NotificationDetails> getIdNotification(String id);

    //get notification detail by top 5
    @Query(value = "SELECT * FROM notification_details AS n WHERE n.user_id = ?1 ORDER BY n.notification_id DESC LIMIT 5 ",nativeQuery = true)
    List<NotificationDetails> getIdNotificationByTop5(String id);

    //count notification by user id
    @Query("select count (n) from NotificationDetails n where n.user.id = ?1")
    Integer countNotificationDetailByUserId(String id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `notification_details` (`type`,`notification_id`,`user_id`) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void addNotificationDetailNativeQuery(String type, Integer nid,String userId);
}
