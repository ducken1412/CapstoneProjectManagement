package com.fa.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fa.entity.Category;
import com.fa.entity.Product;
import com.fa.entity.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {
	@JsonProperty("id")
	private Integer id;

	@NotBlank
	@Size(min = 3, max = 100)
	private String name;

	@NotNull
	private Category category;

	private String description;

	@NotNull
	@Min(0)
	private Float price;

	@Min(0)
	@Max(100)
	private Integer discount;
	
	@JsonProperty("quantity")
	@NotNull
	@Min(0)
	private Integer quantity;

	@NotBlank
	@Size(min = 3, max = 100)
	private String manufacturer;

	@NotNull
	@Min(1)
	@Max(3)
	private Integer condition;

	private Integer status;

	private String imageMain;
	
	private Date createdDate;

	private List<ProductImage> productImageList;

	public ProductDTO(Integer id, String name, Category category, String description, Float price, Integer discount,
			Integer quantity, String manufacturer, Integer condition, Integer status, String imageMain,
			List<ProductImage> productImageList) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.discount = discount;
		this.quantity = quantity;
		this.manufacturer = manufacturer;
		this.condition = condition;
		this.status = status;
		this.imageMain = imageMain;
		this.productImageList = productImageList;
	}

	public ProductDTO(Product product) {
		super();
		this.id = product.getId();
		this.name = product.getName();
		this.category = product.getCategory();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.discount = product.getDiscount();
		this.quantity = product.getQuantity();
		this.manufacturer = product.getManufacturer();
		this.condition = product.getCondition();
		this.status = product.getStatus();
		this.imageMain = product.getImageMain();
		this.productImageList = product.getProductImageList();
		this.createdDate = product.getCreatedDate();
	}

	public ProductDTO() {
		super();
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImageMain() {
		return imageMain;
	}

	public void setImageMain(String imageMain) {
		this.imageMain = imageMain;
	}

	public List<ProductImage> getProductImageList() {
		return productImageList;
	}

	public void setProductImageList(List<ProductImage> productImageList) {
		this.productImageList = productImageList;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imageMain == null) ? 0 : imageMain.hashCode());
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((productImageList == null) ? 0 : productImageList.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageMain == null) {
			if (other.imageMain != null)
				return false;
		} else if (!imageMain.equals(other.imageMain))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (productImageList == null) {
			if (other.productImageList != null)
				return false;
		} else if (!productImageList.equals(other.productImageList))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", discount=" + discount + ", quantity=" + quantity + ", manufacturer=" + manufacturer
				+ ", condition=" + condition + ", status=" + status + ", imageMain=" + imageMain + ", createdDate="
				+ createdDate + ", productImageList=" + productImageList + "]";
	}
	
	
	
}
