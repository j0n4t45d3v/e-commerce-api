package br.com.jonatas.ecommerce.gateway.in.inventory.dto;

import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementStatusType;
import br.com.jonatas.ecommerce.core.domain.inventory.enums.StockMovementType;

public record EditStockMovementDTO(
		Long quantity,
		StockMovementType type,
		StockMovementStatusType statusType,
		String motive
) {
}
