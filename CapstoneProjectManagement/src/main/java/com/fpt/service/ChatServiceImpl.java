package com.fpt.service;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.Chat;
import com.fpt.entity.Users;
import com.fpt.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PostService postService;

    @Override
    public Chat findById(Integer id) {
        return chatRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteComment(Integer id) {
        try {
            chatRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    @CacheEvict(value="numMessageCache",allEntries = true)
    public boolean save(Chat chat) {
        try {
            chatRepository.save(chat);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public List<Chat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public List<Chat> findByRoomId(String roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    @Override
    public List<Users> findUsersInRoom(String roomId) {
        List<Users> users = chatRepository.findUsersInRoom(roomId);
        Users author = null;
        try{
            author = postService.findAuthorByPostId(Integer.parseInt(roomId));
        }catch (Exception ex) {
            System.out.println(ex);
        }
        if(author != null) {
            users.add(author);
        }
        return users;
    }

    @Override
    public List<ChatDTO> findChatsByUserId(String userId) {
        return chatRepository.findChatsByUserId(userId);
    }

}