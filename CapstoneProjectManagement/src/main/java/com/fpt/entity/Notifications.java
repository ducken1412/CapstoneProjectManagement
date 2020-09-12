package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Notifications")
public class Notifications {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_id", referencedColumnName = "id", columnDefinition = "NVARCHAR(50) NOT NULL")
    private Users sender;
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Notification_User", 
        joinColumns = { @JoinColumn(name = "notification_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
	private List<Users> receivers;
	@Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String content;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;
}
