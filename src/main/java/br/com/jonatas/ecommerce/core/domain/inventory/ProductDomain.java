package br.com.jonatas.ecommerce.core.domain.inventory;

public class ProductDomain {
	private Long id;
	private String name;
	private String description;
	private Double price;
	private boolean active;

	public ProductDomain(Long id, String name, String description, Double price, boolean active) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.active = active;
	}

	public ProductDomain() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
