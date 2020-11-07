package com.fpt.service;

import java.util.List;

import com.fpt.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.entity.Notifications;
import com.fpt.repository.NotificationsRepository;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    public List<Notifications> getAllTitle() {
        return notificationsRepository.findAll();
    }

    @Override
    public List<NotificationDTO> getTitle() {
        return notificationsRepository.getAllTitle();
    }

    @Override
    public boolean addNotification(Notifications notifications) {
        try {
            notificationsRepository.save(notifications);
            return true;
        } catch (Exception e) {
            System.out.println("eror add notification");
        }
        return false;
    }

    @Override
    public Notifications getOneNoification(Integer id) {
        return notificationsRepository.getOne(id);
    }

    @Override
    public Notifications getNotificationById(Integer id) {
        return notificationsRepository.findAllById(id);
    }

    @Override
    public List<Notifications> getTop5NotificationsByCreatedDate() {
        return notificationsRepository.getTop5NotificationsByCreatedDate();
    }

    @Override
    public Page<Notifications> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return notificationsRepository.getAllTitlePaggin(secondPageWithFiveElements);
    }

    @Override
    public Page<Notifications> getAllTitlePagginByUserId(Pageable pageable, String id) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        Pageable secondPageWithFiveElements = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return notificationsRepository.g2etAllTitlePagginByUserId(secondPageWithFiveElements, id);
    }


//	@Override
//	public List<Notifications> getTitleByUserId(String id) {
//		return notificationsRepository.findByReceivers_Id(id);
//	}

}
