package com.fa.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fa.common.UploadConstant;
import com.fa.entity.Users;
import com.google.gson.Gson;

@Controller
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
	
	private final String LOCATION_FILE = "C:/";

	@Autowired
	ServletContext context;

	@ResponseBody
	@RequestMapping(value = "main-upload/{index}", method = RequestMethod.POST)
	public String singleUpload(@RequestParam("file") MultipartFile uploadingFiles,
			@PathVariable(name = "index") Integer index, Principal principal) throws IOException {
		return singleUploadFile("uploads/" + UploadConstant.FOLDERS[index] + "/", uploadingFiles, principal);
	}

	public String singleUploadFile(String folder, MultipartFile uploadingFile, Principal principal) {
		if (uploadingFile.isEmpty()) {
			return null;
		}
		long date = new Date().getTime();
		String typeFile = null;
		Path path = null;
		String realPath = null;
		String username = null;
		try {
			byte[] bytes = uploadingFile.getBytes();
			String uploadRootPath = LOCATION_FILE + folder;
			File uploadRootDir = new File(uploadRootPath);
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}
			typeFile = uploadingFile.getOriginalFilename().substring(uploadingFile.getOriginalFilename().lastIndexOf("."));
			if (principal != null) {
				UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
				username = loginedUser.getUsername();
			}
			if (Arrays.stream(UploadConstant.TYPE_FILE).anyMatch(typeFile::equalsIgnoreCase)) {
				path = Paths.get(uploadRootPath + "/" + date + "_" + username + typeFile);
				Files.write(path, bytes);
			} else {
				return null;
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		return "/" + folder + date + "_" + username + typeFile;
	}

	@ResponseBody
	@RequestMapping(value = "upload/{index}", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile[] uploadingFiles,
			@PathVariable(name = "index") Integer index,  Principal principal) throws IOException {
		return new Gson().toJson(uploadFile("uploads/" + UploadConstant.FOLDERS[index] + "/", uploadingFiles, principal));
	}

	public Map<String, Object> uploadFile(String folder, MultipartFile[] uploadingFiles, Principal principal) {
		Map<String, Object> output = new HashMap<>();
		boolean result = false;
		List<String> paths = new ArrayList<>();
		String uploadRootPath = LOCATION_FILE + folder;
		File uploadRootDir = new File(uploadRootPath);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		long date = new Date().getTime();
		File file = null;
		String typeFile = null;
		String username = null;
		if (principal != null) {
			UserDetails loginedUser = (UserDetails) ((Authentication) principal).getPrincipal();
			username = loginedUser.getUsername();
		}
		
		try {
			for (MultipartFile uploadedFile : uploadingFiles) {
				typeFile = uploadedFile.getOriginalFilename()
						.substring(uploadedFile.getOriginalFilename().lastIndexOf("."));
				if (Arrays.stream(UploadConstant.TYPE_FILE).anyMatch(typeFile::equalsIgnoreCase)) {
					file = new File(uploadRootPath + "/" + date + "_" + username + typeFile);
					paths.add("/" + folder + (date++) + "_" + username + typeFile);
					uploadedFile.transferTo(file);
				}
			}
			result = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			result = false;
		}
		output.put("result", result);
		output.put("paths", paths);
		return output;
	}
}
