package com.fpt.service;

import com.fpt.repository.ChatDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatDetailServiceImpl implements ChatDetailService {
    @Autowired
    private ChatDetailRepository chatDetailRepository;

    @Override
    public int findNumberNewMessage(String userId) {
        return chatDetailRepository.findNumberNewMessage(userId);
    }
}