package com.fpt.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[Comments]")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String content;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	private Users sender;
	@Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
	private Date createdDate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "report_detail_id")
	private ReportDetails reportDetail;
}
