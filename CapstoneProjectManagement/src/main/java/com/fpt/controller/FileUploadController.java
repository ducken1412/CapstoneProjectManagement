package com.fpt.controller;

import com.fpt.entity.Files;
import com.fpt.service.FilesService;
import com.fpt.service.FilesServiceImpl;
import com.fpt.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	 @Autowired
	 private FilesService filesService;
	 
	 @Autowired
     private HttpServletRequest request;



    @PostMapping("/uploadMultipleFiles")
    public List<Files> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @PostMapping("/uploadFile")
	public Files uploadFile(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				String uploadsDir = "/uploads/";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new File(realPathtoUploads).exists()) {
					new File(realPathtoUploads).mkdir();
				}

				logger.info("realPathtoUploads = {}", realPathtoUploads);

				String orgName = file.getOriginalFilename();
				String filePath = realPathtoUploads + orgName;
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Files dbFile = new Files();
				dbFile.setFileName(fileName);
				dbFile.setPath(filePath);
				filesService.saveFiles(dbFile);

				File dest = new File(filePath);
				file.transferTo(dest);
				return dbFile;
			} catch (Exception e) {

			}
		}
		return null;

	}

	@RequestMapping("/file/{filePath:.+}")
	public void downloadResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("filePath") String filePath) throws IOException {

		File file = new File(filePath);
		if (file.exists()) {

			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}

}
