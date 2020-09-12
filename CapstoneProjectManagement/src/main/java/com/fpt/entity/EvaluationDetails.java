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
}
