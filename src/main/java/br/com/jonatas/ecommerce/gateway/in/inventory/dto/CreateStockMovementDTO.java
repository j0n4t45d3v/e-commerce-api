package br.com.jonatas.ecommerce.gateway.in.inventory.dto;

import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType;
import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType;

import java.time.LocalDateTime;

public record CreateStockMovementDTO(
		Long quantity,
		StockMovementType type,
		StockMovementStatusType statusType,
		LocalDateTime movementDate,
		String motive
) {
}
