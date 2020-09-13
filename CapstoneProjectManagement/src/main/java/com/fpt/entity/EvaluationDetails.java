package com.fpt.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EvaluationDetails")
public class EvaluationDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "INT")
	private Integer id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "evaluation_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
	private Evaluations evaluation;
	@Column(name = "grade", columnDefinition = "FLOAT NOT NULL")
	private Integer grade;
	@Column(name = "content", columnDefinition = "NVARCHAR(256) NOT NULL")
	private String content;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "assessor_id", referencedColumnName = "id", columnDefinition = "NVARCHAR(50) NOT NULL")
	private Users assessor;
	@Column(name = "type", columnDefinition = "NVARCHAR(50) NOT NULL")
	private String type;
	
	public EvaluationDetails() {
		super();
	}
	
	public EvaluationDetails(Evaluations evaluation, Integer grade, String content, Users assessor,
			String type) {
		super();
		this.evaluation = evaluation;
		this.grade = grade;
		this.content = content;
		this.assessor = assessor;
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Evaluations getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluations evaluation) {
		this.evaluation = evaluation;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Users getAssessor() {
		return assessor;
	}
	public void setAssessor(Users assessor) {
		this.assessor = assessor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
