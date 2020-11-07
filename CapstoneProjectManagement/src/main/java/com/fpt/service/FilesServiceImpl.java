package com.fpt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Files;
import com.fpt.repository.FilesRepository;

import java.util.List;


@Service
public class FilesServiceImpl implements FilesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FilesServiceImpl.class);
	
	@Autowired
	private FilesRepository filesRepository;
	
	@Override
	public Files saveFiles(Files file) {
		// Normalize file name
		try {
			return	filesRepository.save(file);
		    
		} catch (Exception ex) {
			LOGGER.error("Could not store file " + file.getPath() + ". Please try again!", ex);
		}
		return null;
	}

	@Override
	public Integer deleteAllByPostId(Integer postId) {
		return filesRepository.deleteAllByPostId(postId);
	}
	public List<Files> getFileByPostId(Integer id) {
		return filesRepository.getFileByPostId(id);
	}
}
