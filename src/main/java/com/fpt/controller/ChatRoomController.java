package com.fpt.controller;

import com.fpt.entity.Chat;
import com.fpt.entity.Message;
import com.fpt.entity.Users;
import com.fpt.service.ChatService;
import com.fpt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

import static java.lang.String.format;

@Controller
public class ChatRoomController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/chat/{postId}")
    public String chat(@PathVariable String postId, Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        Users users = userService.findByEmail(principal.getName());
        if (users != null) {
            model.addAttribute("loggedUser", users.getUsername());
        } else {
            return "error/403Page";
        }
        List<Chat> chats = chatService.findByRoomId(postId);
        model.addAttribute("chats",chats);
        return "chatting/mychat";
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage, Principal principal) {

        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
        Users users = userService.findByEmail(principal.getName());
        Chat chat = new Chat();
        chat.setRoomId(roomId);
        chat.setContent(chatMessage.getContent());
        if (users != null) {
            chat.setUser(users);
        }
        chatService.save(chat);
    }

    @MessageMapping("/chat/{roomId}/addUser")
    public void addUser(@DestinationVariable String roomId, @Payload Message chatMessage,
                        SimpMessageHeaderAccessor headerAccessor) {

        String currentRoomId = (String) headerAccessor.getSessionAttributes().put("room_id", roomId);
        if (currentRoomId != null) {
            Message leaveMessage = new Message();
            leaveMessage.setType(Message.MessageType.LEAVE);
            leaveMessage.setSender(chatMessage.getSender());
            messagingTemplate.convertAndSend(format("/chat-room/%s", currentRoomId), leaveMessage);
        }
        headerAccessor.getSessionAttributes().put("name", chatMessage.getSender());
        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
    }
}
