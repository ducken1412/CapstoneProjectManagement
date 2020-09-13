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

	public Reports() {
		super();
	}

	public Reports(String name, List<HistoryRecords> historyRecords, Users sender, List<Users> reportRecipients,
			String type, ReportDetails reportDetail) {
		super();
		this.name = name;
		this.historyRecords = historyRecords;
		this.sender = sender;
		this.reportRecipients = reportRecipients;
		this.type = type;
		this.reportDetail = reportDetail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HistoryRecords> getHistoryRecords() {
		return historyRecords;
	}

	public void setHistoryRecords(List<HistoryRecords> historyRecords) {
		this.historyRecords = historyRecords;
	}

	public Users getSender() {
		return sender;
	}

	public void setSender(Users sender) {
		this.sender = sender;
	}

	public List<Users> getReportRecipients() {
		return reportRecipients;
	}

	public void setReportRecipients(List<Users> reportRecipients) {
		this.reportRecipients = reportRecipients;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ReportDetails getReportDetail() {
		return reportDetail;
	}

	public void setReportDetail(ReportDetails reportDetail) {
		this.reportDetail = reportDetail;
	}

}
