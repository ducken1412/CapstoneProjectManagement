package com.fpt.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Reports")
public class Reports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@Column(name = "name", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String name;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "report")
	private List<HistoryRecords> historyRecords;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sender_id", referencedColumnName = "id", columnDefinition = "NVARCHAR(50) NOT NULL")
	private Users sender;
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Report_User", joinColumns = { @JoinColumn(name = "report_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	private List<Users> reportRecipients;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;
	@OneToOne(mappedBy = "report")
	private ReportDetails reportDetail;
}
