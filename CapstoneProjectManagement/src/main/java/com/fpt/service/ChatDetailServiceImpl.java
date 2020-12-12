package com.fpt.service;

import com.fpt.entity.Users;
import com.fpt.repository.ChatDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ChatDetailServiceImpl implements ChatDetailService {
    @Autowired
    private ChatDetailRepository chatDetailRepository;

    @Autowired
    private UserService userService;

    @Override
    @Cacheable(value="numMessageCache")
    public int findNumberNewMessage(String userEmail) {
        Users userLogin = userService.findByEmail(userEmail);
        return chatDetailRepository.findNumberNewMessage(userLogin.getId());
    }

    @Override
    @CacheEvict(value="numMessageCache",allEntries = true)
    public int updateChatStatusRead(String roomId, String userId) {
        return chatDetailRepository.updateChatStatusRead(roomId,userId);
    }
}