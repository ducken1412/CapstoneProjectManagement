package com.fpt.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpt.entity.Comments;
import com.fpt.entity.Files;
import com.fpt.entity.HistoryRecords;
import com.fpt.entity.Users;

import javax.validation.constraints.NotBlank;

public class PostDTO {
	@JsonProperty("id")
	private Integer id;
	@NotBlank(message = "Title must not blank")
	private String title;
	@NotBlank(message = "Description must not blank")
	private String description;
	private List<Comments> comments;
	private List<HistoryRecords> historyRecords;
	private List<String> files;
	private Users author;

	public PostDTO(Integer id, String title, String description, List<Comments> comments,
			List<HistoryRecords> historyRecords, List<String> files, Users author) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.comments = comments;
		this.historyRecords = historyRecords;
		this.files = files;
		this.author = author;
	}

	public PostDTO() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}

	public List<HistoryRecords> getHistoryRecords() {
		return historyRecords;
	}

	public void setHistoryRecords(List<HistoryRecords> historyRecords) {
		this.historyRecords = historyRecords;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public Users getAuthor() {
		return author;
	}

	public void setAuthor(Users author) {
		this.author = author;
	}

}
