package com.fa.dto;

public class SearchProductDTO {
	private Integer categoryId;
	private String name;
	private Float priceMax;
	private Float priceMin;
	private Integer sortedBy;
	private Integer condition;
	private Integer page;

	public SearchProductDTO(Integer categoryId, String name, Float priceMax, Float priceMin, Integer sortedBy,
			Integer condition, Integer page) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.priceMax = priceMax;
		this.priceMin = priceMin;
		this.sortedBy = sortedBy;
		this.condition = condition;
		this.page = page;
	}

	public SearchProductDTO() {
		super();
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPriceMax() {
		return priceMax;
	}

	public void setPriceMax(Float priceMax) {
		this.priceMax = priceMax;
	}

	public Float getPriceMin() {
		return priceMin;
	}

	public void setPriceMin(Float priceMin) {
		this.priceMin = priceMin;
	}

	public Integer getSortedBy() {
		return sortedBy;
	}

	public void setSortedBy(Integer sortedBy) {
		this.sortedBy = sortedBy;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
