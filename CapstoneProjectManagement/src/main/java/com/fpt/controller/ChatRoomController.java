package com.fpt.controller;

import com.fpt.dto.ChatDTO;
import com.fpt.entity.*;
import com.fpt.service.*;
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

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

@Controller
public class ChatRoomController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PostService postService;

    @Autowired
    private ChatDetailService chatDetailService;

    @Autowired
    private CapstoneProjectService capstoneProjectService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @GetMapping("/messenger/{postId}")
    public String messenger(@PathVariable String postId, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        try{
            byte[] decodedDocIdBytes = Base64.getDecoder().decode(postId);
            String decodedDocIdAsString = new String(decodedDocIdBytes, "UTF-8");
            postId = decodedDocIdAsString;
        }catch (Exception ex){

        }
        Users user = userService.findByEmail(principal.getName());
        model.addAttribute("loggedUser", user.getUsername());
        try {
            List<Chat> chats = chatService.findByRoomId(postId);
            if (postId.startsWith("pr")) {
                model.addAttribute("roomId", postId);
                List<String> list = new ArrayList<>(Arrays.asList(postId.split("_")));
                boolean checkAuth = false;
                for (String str : list) {
                    if (!str.equals(user.getUsername()) && list.indexOf(str) != 0) {
                        model.addAttribute("title", str);
                        break;
                    }
                    if(str.equals(user.getUsername())) {
                        checkAuth = true;
                    }
                }
                for (String str : list) {
                    if(str.equals(user.getUsername())) {
                        checkAuth = true;
                        break;
                    }
                }
                if(!checkAuth) {
                    return "error/403Page";
                }
            } else if (postId.equals("gr_tr_dep_heads")) {
                boolean checkRole = false;
                for (UserRoles userRoles : user.getRoleUser()) {
                    if (userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_HEAD_DB) || userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_TRAINING_DEP_DB)) {
                        checkRole = true;
                        break;
                    }
                }
                if(!checkRole) {
                    return "error/403Page";
                }
                model.addAttribute("title", "Heads And Training Department");
                model.addAttribute("roomId", postId);
            } else if (postId.startsWith("cap")) {
                CapstoneProjects capstoneProject = capstoneProjectService.findById(Integer.parseInt(postId.substring(postId.indexOf("_")+1)));
                List<CapstoneProjects> capstoneProjects = capstoneProjectService.findCapstoneProjectRegistedBySupervisorId(user.getId());
                boolean checkAuth = false;
                for (CapstoneProjects cap:capstoneProjects) {
                    if(cap.getId().toString().equals(postId.substring(postId.indexOf("_")+1))) {
                        checkAuth = true;
                    }
                }
                if(!checkAuth) {
                    return "error/403Page";
                }
                model.addAttribute("title", capstoneProject.getName());
                model.addAttribute("roomId", postId);
            } else {
                Posts post = postService.findById(Integer.parseInt(postId.substring(3)));
                model.addAttribute("title", post.getTitle());
                model.addAttribute("roomId", post.getId());
            }
            model.addAttribute("chats", chats);
            chatDetailService.updateChatStatusRead(postId, user.getId());
        } catch (Exception ex) {
            System.out.println(ex);
            model.addAttribute("chats", new ArrayList<>());
            model.addAttribute("title", "");
            model.addAttribute("roomId", "");
        }
        return "chatting/chat-page";
    }

    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable String roomId, @Payload Message chatMessage, Principal principal) {
        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
        Users userLogin = userService.findByEmail(principal.getName());
        Chat chat = new Chat();
        chat.setRoomId(roomId);
        chat.setContent(chatMessage.getContent());
        if (roomId.startsWith("pr")) {
            chat.setType("specific");
        } else {
            chat.setType("group");
        }
        if (roomId.equals("gr_tr_dep_heads") || roomId.startsWith("cap")) {
            chat.setType("special");
        }
        if (userLogin != null) {
            chat.setUser(userLogin);
        }
        List<Users> users = chatService.findUsersInRoom(roomId);
        users.add(userLogin);
        users = new ArrayList<>(new HashSet<>(users));
        List<ChatDetails> chatDetails = new ArrayList<>();
        ChatDetails temp;
        for (Users u : users) {
            temp = new ChatDetails();
            temp.setChat(chat);
            temp.setUser(u);
            if (u.getId().equals(userLogin.getId())) {
                temp.setReadStatus(Constant.CHAT_READ);
            } else {
                temp.setReadStatus(Constant.CHAT_UNREAD);
            }
            chatDetails.add(temp);
        }
        if (!chatDetails.isEmpty()) {
            chat.setChatDetail(chatDetails);
        }
        chatService.save(chat);
        Chat chat1 = chat;
        List<ChatDetails> chatDetails1 = new ArrayList<>();
        ChatDetails cd = new ChatDetails();
        cd.setUser(userLogin);
        cd.setChat(chat1);
        cd.setReadStatus(Constant.CHAT_READ);
        chatDetails1.add(cd);
        chat.setChatDetail(chatDetails1);
        for (Users u : users) {
            if (u.getId() != userLogin.getId()) {
                chat1.setUser(u);
                break;
            }
        }
        if (roomId.startsWith("pr")) {
            chat1.setContent("");
            chat1.setId(null);
            chatService.save(chat1);
            chatDetailService.updateChatStatusRead(chat1.getId().toString(), chat1.getUser().getId());
        }
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
    public String getNumMessage(Principal principal) {
        if (principal == null) {
            return "errorLogin";
        }
        int num = chatDetailService.findNumberNewMessage(principal.getName());
        return String.valueOf(num);
    }

    @GetMapping("/chat-content")
    public String getChatContent(Model model, Principal principal) {
        Users userLogin = userService.findByEmail(principal.getName());
        List<ChatDTO> chatDTOS = chatService.findChatsByUserId(userLogin.getId());
        List<ChatDTO> chatDTOS2 = chatService.findChatPrivateByUserId(userLogin.getId());
        chatDTOS = Stream.concat(chatDTOS.stream(), chatDTOS2.stream())
                .collect(Collectors.toList());
        Collections.reverse(chatDTOS);
        List<ChatDTO> readList = new ArrayList<>();
        List<ChatDTO> unreadList = new ArrayList<>();
        boolean check;
        for (ChatDTO dto : chatDTOS) {
            if (dto.getReadStatus().equals(Constant.CHAT_UNREAD)) {
                unreadList.add(dto);
            } else {
                check = false;
                for (ChatDTO c : unreadList) {
                    if (c.getId().equals(dto.getId())) {
                        check = true;
                    }
                }
                if (!check) {
                    readList.add(dto);
                }
            }
        }
        chatDTOS = Stream.concat(unreadList.stream(), readList.stream())
                .collect(Collectors.toList());
        boolean checkRole = false;
        for (UserRoles userRoles : userLogin.getRoleUser()) {
            if (userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_HEAD_DB) || userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_TRAINING_DEP_DB)) {
                checkRole = true;
                break;
            }
        }
        boolean checkChatCap = false;
        boolean checkChatCapSupervisor = false;
        if (checkRole) {
            ChatDTO chatDTO = chatService.findChatTrainingDeptAndHeads(userLogin.getId());
            if (chatDTO == null) {
                chatDTO = new ChatDTO() {
                    @Override
                    public String getId() {
                        return "gr_tr_dep_heads";
                    }

                    @Override
                    public String getTitle() {
                        return "Heads And Training Department";
                    }

                    @Override
                    public String getReadStatus() {
                        return "read";
                    }

                    @Override
                    public String getType() {
                        return "special";
                    }
                };
            }
            model.addAttribute("chatTrainingDepHeads", chatDTO);
        } else {
            for (UserRoles userRoles : userLogin.getRoleUser()) {
                if (userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_LEADER_DB) || userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_STUDENT_MEMBER_DB)) {
                    checkRole = true;
                    break;
                }
            }
            if (checkRole) {
                CapstoneProjects capstoneProject = capstoneProjectService.getCapstoneProjectRegistedByUserId(userLogin.getId());
                if (capstoneProject != null && !capstoneProject.getStatus().getName().equals(Constant.STATUS_REGISTERING_CAPSTONE_DB)) {
                    ChatDTO chatDTO = chatService.findChatGroupCap(userLogin.getId(), "cap_" + capstoneProject.getId());
                    if (chatDTO == null) {
                        chatDTO = new ChatDTO() {
                            @Override
                            public String getId() {
                                return "cap_" + capstoneProject.getId();
                            }

                            @Override
                            public String getTitle() {
                                return capstoneProject.getName();
                            }

                            @Override
                            public String getReadStatus() {
                                return "read";
                            }

                            @Override
                            public String getType() {
                                return "special";
                            }
                        };
                    }
                    checkChatCap = true;
                    model.addAttribute("chatTrainingDepHeads", chatDTO);
                }

            } else {
                for (UserRoles userRoles : userLogin.getRoleUser()) {
                    if (userRoles.getUserRoleKey().getRole().getName().equals(Constant.ROLE_LECTURERS_DB)) {
                        checkRole = true;
                        break;
                    }
                }
                if(checkRole) {
                    ChatDTO chatDTO;
                    List<ChatDTO> chatDTOS1 = chatService.findChatGroupCapSupervisor(userLogin.getId());
                    List<CapstoneProjects> capstoneProjects = capstoneProjectService.findCapstoneProjectRegistedBySupervisorId(userLogin.getId());
                    if(chatDTOS1.isEmpty()) {
                        chatDTOS1 = new ArrayList<>();
                        if(!capstoneProjects.isEmpty()) {
                            for (CapstoneProjects cap:capstoneProjects) {
                                chatDTO = new ChatDTO() {
                                    @Override
                                    public String getId() {
                                        return "cap_" + cap.getId();
                                    }
                                    @Override
                                    public String getTitle() {
                                        return cap.getName();
                                    }

                                    @Override
                                    public String getReadStatus() {
                                        return "read";
                                    }

                                    @Override
                                    public String getType() {
                                        return "special";
                                    }
                                };
                                chatDTOS1.add(chatDTO);
                            }
                        }
                    } else {
                        if(!capstoneProjects.isEmpty()) {
                            boolean tempCheck;
                            for (CapstoneProjects cap:capstoneProjects) {
                                tempCheck = false;
                                for (ChatDTO dto:chatDTOS1) {
                                    if(dto.getId().substring(dto.getId().indexOf("_")+1).equals(cap.getId().toString())) {
                                        tempCheck = true;
                                        break;
                                    }
                                }
                                if(!tempCheck) {
                                    chatDTOS1.add(new ChatDTO() {
                                        @Override
                                        public String getId() {
                                            return "cap_" + cap.getId();
                                        }
                                        @Override
                                        public String getTitle() {
                                            return cap.getName();
                                        }

                                        @Override
                                        public String getReadStatus() {
                                            return "read";
                                        }

                                        @Override
                                        public String getType() {
                                            return "special";
                                        }
                                    });
                                }
                            }
                        }
                    }
                    if(!chatDTOS1.isEmpty()) {
                        checkChatCapSupervisor = true;
                    }
                    model.addAttribute("chatCapSupervisor", chatDTOS1);
                }
            }
        }
        if(!checkChatCapSupervisor) {
            model.addAttribute("chatCapSupervisor", new ArrayList<>());
        }
        model.addAttribute("checkChatCap", checkChatCap);
        model.addAttribute("chats", chatDTOS);
        return "chatting/dropdown-chat-content";
    }

    @ResponseBody
    @GetMapping("/find-room")
    public String findRoom(String room1, String room2, Model model, Principal principal) {
        String room = chatService.findRoomChatPrivate(room1, room2);
        return room;
    }

    @ResponseBody
    @GetMapping("/check-username-available/{username}")
    public String checkUsername(@PathVariable String username, Model model, Principal principal) {
        List<Users> users = userService.findByUsername(username);
        if (!users.isEmpty()) {
            return "success";
        }
        return "fail";
    }

    @GetMapping("/create-chat")
    public String getCreateChat(Model model, Principal principal) {
        return "chatting/create-chat";
    }
}
