package br.com.jonatas.ecommerce.core.domain.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType;
import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType;

import java.time.LocalDateTime;

public class StockMovementDomain {
	private Long id;
	private Long quantity;
	private StockMovementType type;
	private StockMovementStatusType movementStatus;
	private LocalDateTime movementDate;
	private String motive;
	private InventoryDomain inventory;

	public StockMovementDomain(
			Long id,
			Long quantity,
			StockMovementType type,
			StockMovementStatusType movementStatus,
			LocalDateTime movementDate,
			String motive,
			InventoryDomain inventory
	) {
		this.id = id;
		this.quantity = quantity;
		this.type = type;
		this.movementStatus = movementStatus;
		this.movementDate = movementDate;
		this.motive = motive;
		this.inventory = inventory;
	}

	public StockMovementDomain() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public StockMovementType getType() {
		return type;
	}

	public void setType(StockMovementType type) {
		this.type = type;
	}

	public StockMovementStatusType getMovementStatus() {
		return movementStatus;
	}

	public void setMovementStatus(StockMovementStatusType movementStatus) {
		this.movementStatus = movementStatus;
	}

	public LocalDateTime getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(LocalDateTime movementDate) {
		this.movementDate = movementDate;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public InventoryDomain getInventory() {
		return inventory;
	}

	public void setInventory(InventoryDomain inventory) {
		this.inventory = inventory;
	}
}
