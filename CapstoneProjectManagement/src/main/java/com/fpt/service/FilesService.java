package com.fpt.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.entity.Files;

public interface FilesService {
	Files saveFiles(Files file);
	Integer deleteAllByPostId(Integer postId);
}