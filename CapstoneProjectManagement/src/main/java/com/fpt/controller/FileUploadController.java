package com.fpt.controller;

import com.fpt.dto.CommentDTO;
import com.fpt.entity.Comments;
import com.fpt.entity.Files;
import com.fpt.entity.Posts;
import com.fpt.entity.Users;
import com.fpt.service.FilesService;
import com.fpt.service.FilesStorageService;
import com.fpt.service.PostService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin("http://localhost:8080")
@Controller
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	 @Autowired
	 private FilesService filesService;

	@Autowired
	FilesStorageService storageService;

	 @GetMapping(value = "/uploadMultipleFiles")
	public String upload(Model model) {
			model.addAttribute("upload", new Files());
			return "common/upload";
	}
	 
	 	

    @PostMapping("/uploadMultipleFiles")
    public String uploadMultipleFiles(@RequestParam("files[]") MultipartFile[] files) {
    	/*logger.info("uploadMultipleFiles");
    	String uploadsDir ="src/main/resources/static/uploads/";
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,uploadsDir))
                .collect(Collectors.toList());*/

		String message = "";
		try {
			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {
				storageService.save(file);
				fileNames.add(file.getOriginalFilename());
				/*Files dbFile = new Files();
				dbFile.setFileName(file.getOriginalFilename());
				dbFile.setPath(file.);
				filesService.saveFiles(dbFile);*/
			});
			message = "Uploaded the files successfully: " + fileNames;
			return message;
		} catch (Exception e) {
			message = "Fail to upload files!";
			return message;
		}
    }


    @PostMapping(value = "/uploadFile",consumes = { "multipart/form-data" })
	public Files uploadFile(@RequestParam("file") MultipartFile file,String uploadsDir) {
		if (!file.isEmpty()) {
			try {

				String orgName = file.getOriginalFilename();
				String filePath = uploadsDir + orgName;
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				Files dbFile = new Files();
				dbFile.setFileName(fileName);
				logger.info("filename = {}", fileName);
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
    
   

	@RequestMapping("/file/{filename:.+}")
	public void downloadResource(HttpServletRequest request, HttpServletResponse response,
								 @PathVariable String filename) throws IOException {

		Resource file = storageService.load(filename);
		if (file.exists()) {

			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getFilename());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			//response.setHeader("Content-Disposition", String.format("inline; filename=" + file.getFilename()));

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file.getFile()));

			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.getOutputStream().flush();

		}
	}

}
