package com.fpt.service;

import com.fpt.entity.Chat;
import com.fpt.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatRepository chatRepository;

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

}