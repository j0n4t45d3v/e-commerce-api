package br.com.jonatas.ecommerce.adapter.in.controller.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.*;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditProductDTO;
import br.com.jonatas.ecommerce.infra.common.http.ResponseV0;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {
	private final AddProductGateway addProductGateway;
	private final SearchProductGateway searchProductGateway;
	private final EditProductGateway editProductGateway;
	private final RemoveProductGateway removeProductGateway;

	@PostMapping
	public ResponseEntity<ResponseV0<String>> add(
			@RequestBody AddProductDTO addProductDTO,
			UriComponentsBuilder uriComponentsBuilder
	) {
		var productCreated = this.addProductGateway.execute(addProductDTO);
		var uri = uriComponentsBuilder.path("/v1/products/{id}").buildAndExpand(productCreated).toUri();
		var response = ResponseV0.of(201, "Product created successfully");
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping
	public ResponseEntity<ResponseV0<List<ProductDomain>>> searchAll() {
		var products = this.searchProductGateway.searchAllProducts();
		var response = ResponseV0.of(200, products);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseV0<ProductDomain>> searchOne(@PathVariable("id") Long id) {
		var products = this.searchProductGateway.searchProductById(id);
		var response = ResponseV0.of(200, products);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseV0<String>> edit(
			@PathVariable("id") Long id,
			@RequestBody EditProductDTO editProductDTO
	) {
		this.editProductGateway.execute(editProductDTO, id);
		var response = ResponseV0.of(200, "Product edited successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseV0<String>> delete(@PathVariable("id") Long id) {
		this.removeProductGateway.execute(id);
		var response = ResponseV0.of(200, "Product removed successfully");
		return ResponseEntity.ok(response);
	}
}
