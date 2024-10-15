package br.com.jonatas.ecommerce.integration.inventory;

import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.in.inventory.AddProductDTO;
import br.com.jonatas.ecommerce.gateway.in.inventory.dto.EditProductDTO;
import br.com.jonatas.ecommerce.infra.common.http.ResponseErrorV0;
import br.com.jonatas.ecommerce.infra.common.http.ResponseV0;
import br.com.jonatas.ecommerce.integration.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

class ProductControllerV1IT extends BaseIntegrationTest {

	@Test
	@Order(1)
	@DisplayName("Should register a new product")
	void registerNewProduct() {
		var newProduct = new AddProductDTO("Product Test", "Description Test product ", 10.0);
		var requestBody = new HttpEntity<>(newProduct);
		var typeResponse = new ParameterizedTypeReference<ResponseV0<String>>() {
		};
		var response = this.restTemplate.exchange("/v1/products", POST, requestBody, typeResponse);
		assertEquals(CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Product created successfully", response.getBody().data());
		assertNotNull(response.getHeaders().getLocation());
	}

	@Test
	@Order(2)
	@DisplayName("Should return a list of all products registered")
	void findAllProductsRegistered() {
		var typeResponse = new ParameterizedTypeReference<ResponseV0<List<ProductDomain>>>() {
		};
		var response = this.restTemplate.exchange("/v1/products", GET, null, typeResponse);
		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody().data())
				.isNotNull()
				.hasSize(1);
		assertEquals("Product Test", response.getBody().data().getFirst().getName());
		assertEquals("Description Test product ", response.getBody().data().getFirst().getDescription());
		assertEquals(10.0, response.getBody().data().getFirst().getPrice());
		assertTrue(response.getBody().data().getFirst().isActive());
	}

	@Test
	@Order(3)
	@DisplayName("Should return a product by id")
	void findProductById() {
		var typeResponse = new ParameterizedTypeReference<ResponseV0<ProductDomain>>() {
		};
		var response = this.restTemplate.exchange("/v1/products/1", GET, null, typeResponse);
		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Product Test", response.getBody().data().getName());
		assertEquals("Description Test product ", response.getBody().data().getDescription());
		assertEquals(10.0, response.getBody().data().getPrice());
		assertTrue(response.getBody().data().isActive());
	}

	@Test
	@Order(4)
	@DisplayName("Should return not found when find a product not exist")
	void findProductByIdError() {
		var response = this.restTemplate.exchange("/v1/products/2", GET, null, ResponseErrorV0.class);
		assertEquals(NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Product not found", response.getBody().error());
	}

	@Test
	@Order(5)
	@DisplayName("Should edit product by id")
	void editProductById() {
		var productEdited = new EditProductDTO("Product Test Edited", "Description Test product edited", 5.0, false);
		var requestBody = new HttpEntity<>(productEdited);
		var typeResponse = new ParameterizedTypeReference<ResponseV0<String>>() {
		};
		var response = this.restTemplate.exchange("/v1/products/1", PUT, requestBody, typeResponse);
		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Product edited successfully", response.getBody().data());
	}

	@Test
	@Order(6)
	@DisplayName("Should remove product by id")
	void removeProductById() {
		var typeResponse = new ParameterizedTypeReference<ResponseV0<String>>() {
		};
		var response = this.restTemplate.exchange("/v1/products/1", DELETE, null, typeResponse);
		assertEquals(OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals("Product removed successfully", response.getBody().data());
	}


}
