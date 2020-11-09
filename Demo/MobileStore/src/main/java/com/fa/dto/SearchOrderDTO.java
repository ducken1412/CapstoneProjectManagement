package com.fa.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class SearchOrderDTO {
	private Integer orderId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	private Integer status;
	private Integer page;

	public SearchOrderDTO() {
	}

	public SearchOrderDTO(Integer orderId, Date fromDate, Date toDate, Integer status, Integer page) {
		super();
		this.orderId = orderId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.status = status;
		this.page = page;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
