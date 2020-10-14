package com.fpt.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "Notifications")
public class Notifications {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Users sender;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Notification_User", joinColumns = {
			@JoinColumn(name = "notification_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	private List<Users> receivers;
	@Column(name = "content", columnDefinition = "longtext NOT NULL")
	private String content;
	@Column(name = "title", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String title;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;

	public Notifications() {
		super();
	}

	public Notifications(Integer id, Users sender, List<Users> receivers, String content, String title, String type) {
		super();
		this.id = id;
		this.sender = sender;
		this.receivers = receivers;
		this.content = content;
		this.title = title;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Users getSender() {
		return sender;
	}

	public void setSender(Users sender) {
		this.sender = sender;
	}

	public List<Users> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<Users> receivers) {
		this.receivers = receivers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	

}
