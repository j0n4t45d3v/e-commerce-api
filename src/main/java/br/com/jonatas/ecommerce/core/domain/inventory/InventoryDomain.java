package br.com.jonatas.ecommerce.core.domain.inventory;

import java.util.List;

public class InventoryDomain {
	private Long id;
	private ProductDomain product;
	private Long quantity;
	private Long reservedQuantity;
	private Long minimalStock;
	private List<StockMovementDomain> stockMovements;

	public InventoryDomain(
			Long id,
			ProductDomain product,
			Long quantity,
			Long reservedQuantity,
			Long minimalStock,
			List<StockMovementDomain> stockMovements
	) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.reservedQuantity = reservedQuantity;
		this.minimalStock = minimalStock;
		this.stockMovements = stockMovements;
	}

	public InventoryDomain() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(Long reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public Long getMinimalStock() {
		return minimalStock;
	}

	public void setMinimalStock(Long minimalStock) {
		this.minimalStock = minimalStock;
	}

	public List<StockMovementDomain> getStockMovements() {
		return stockMovements;
	}

	public void setStockMovements(List<StockMovementDomain> stockMovements) {
		this.stockMovements = stockMovements;
	}
}
