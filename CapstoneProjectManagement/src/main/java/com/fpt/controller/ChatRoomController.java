package com.fpt.controller;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.*;
import com.fpt.service.ChatDetailService;
import com.fpt.service.ChatService;
import com.fpt.service.UserService;
import com.fpt.utils.Constant;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Controller
public class ChatRoomController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatDetailService chatDetailService;

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
        Users userLogin = userService.findByEmail(principal.getName());
        Chat chat = new Chat();
        chat.setRoomId(roomId);
        chat.setContent(chatMessage.getContent());
        if (userLogin != null) {
            chat.setUser(userLogin);
        }
        List<Users> users = chatService.findUsersInRoom(roomId);
        users.add(userLogin);
        List<ChatDetails> chatDetails = new ArrayList<>();
        ChatDetails temp;
        for (Users u: users) {
            temp = new ChatDetails();
            temp.setChat(chat);
            temp.setUser(u);
            temp.setReadStatus(Constant.CHAT_UNREAD);
            chatDetails.add(temp);
        }
        if(!chatDetails.isEmpty()) {
            chat.setChatDetail(chatDetails);
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

    @ResponseBody
    @GetMapping("/number-new-message")
    public String getNumMessage(Principal principal){
        if(principal == null) {
            return "errorLogin";
        }
        Users userLogin = userService.findByEmail(principal.getName());
        int num = chatDetailService.findNumberNewMessage(userLogin.getId());
        return String.valueOf(num);
    }

    @GetMapping("/chat-content")
    public String getChatContent(Model model,Principal principal){
        Users userLogin = userService.findByEmail(principal.getName());
        List<ChatDTO> chatDTOS = chatService.findChatsByUserId(userLogin.getId());
        model.addAttribute("chats", chatDTOS);
        return "chatting/dropdown-chat-content";
    }
}
