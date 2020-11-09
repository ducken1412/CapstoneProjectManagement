package com.fa.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadFile implements Serializable {
	private static final long serialVersionUID = 74458L;
	 
    private List<MultipartFile> file;
    

	public List<MultipartFile> getFile() {
		return file;
	}

	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
}
