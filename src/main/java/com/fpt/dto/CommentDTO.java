package com.fpt.dto;

public class CommentDTO {
	private Integer postId;
	private String content;

	public CommentDTO(Integer postId, String content) {
		super();
		this.postId = postId;
		this.content = content;
	}

	public CommentDTO() {
		super();
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
