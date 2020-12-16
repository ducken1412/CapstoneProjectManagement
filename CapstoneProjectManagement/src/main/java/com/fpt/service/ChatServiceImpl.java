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

    @Autowired
    private UserService userService;

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
        if(roomId.equals("gr_tr_dep_heads")){
            return chatRepository.findUsersInRoomSpecial();
        }
        List<Users> users = chatRepository.findUsersInRoom(roomId);
        Users author = null;
        if(roomId.startsWith("pr")) {
            String tmp = roomId.substring(3);
            String username = tmp.substring(0,tmp.indexOf("_"));
            users.add(userService.findByUsername(username).get(0));
            return users;
        }

        try{
            author = postService.findAuthorByPostId(Integer.parseInt(roomId.substring(3)));
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

    @Override
    public List<ChatDTO> findChatPrivateByUserId(String userId) {
        return chatRepository.findChatPrivateByUserId(userId);
    }

    @Override
    public String findRoomChatPrivate(String roomId1, String roomId2) {
        return chatRepository.findRoomChatPrivate(roomId1,roomId2);
    }

    @Override
    public ChatDTO findChatTrainingDeptAndHeads(String userId) {
        return chatRepository.findChatTrainingDeptAndHeads(userId);
    }

    @Override
    public List<Users> findUsersInRoomSpecial() {
        return chatRepository.findUsersInRoomSpecial();
    }

}