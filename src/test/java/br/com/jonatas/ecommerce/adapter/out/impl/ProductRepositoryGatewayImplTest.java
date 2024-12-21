package br.com.jonatas.ecommerce.adapter.out.impl;

import br.com.jonatas.ecommerce.adapter.out.entity.ProductEntity;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@Import(ProductRepositoryGatewayImpl.class)
class ProductRepositoryGatewayImplTest {

	@Autowired
	private ProductRepositoryGateway productRepository;
	@Autowired
	private EntityManager entityManager;
	private long persistenceProductId;

	@BeforeEach
	void setUp() {
		var productEntity = new ProductEntity();
		productEntity.setName("Product Test");
		productEntity.setDescription("Product Test Description");
		productEntity.setPrice(10.0);
		productEntity.setActive(true);
		entityManager.persist(productEntity);
		entityManager.flush();
		this.persistenceProductId = productEntity.getId();
	}

	@Test
	@DisplayName("Should save product in database")
	void save() {
		ProductDomain productDomain = new ProductDomain();
		productDomain.setName("Product Test");
		productDomain.setDescription("Product Test Description");
		productDomain.setPrice(10.0);
		productDomain.setActive(true);
		var productId = this.productRepository.save(productDomain);
		var resultQueryNative = this.productInDatabase(productId);
		assertNotNull(productId);
		assertThat(resultQueryNative.active()).isEqualTo('Y');
	}

	@Test
	@DisplayName("Should verify if product exists in database")
	void existsById() {
		var existProduct = this.productRepository.existsById(this.persistenceProductId);
		assertThat(existProduct)
				.isEqualTo(this.productExistsInDatabase())
				.isTrue();
	}

	@Test
	@DisplayName("Should delete product record in database")
	void deleteById() {
		this.productRepository.deleteById(this.persistenceProductId);
		assertThat(this.productExistsInDatabase()).isFalse();
	}

	@Test
	@DisplayName("Should return all products")
	void findAll() {
		var result = this.productRepository.findAll();
		var queryString = "SELECT COUNT(p.id) FROM products p";
		var query = entityManager.createNativeQuery(queryString);
		var resultQueryNative = (Long) query.getSingleResult();
		assertThat(result)
				.isNotEmpty()
				.hasSize(resultQueryNative.intValue());
	}

	@Test
	@DisplayName("Should find product by ID")
	void findById() {
		var result = this.productRepository.findById(this.persistenceProductId);
		var resultExpected = this.productInDatabase(this.persistenceProductId);
		assertThat(result)
				.isNotEmpty()
				.hasValueSatisfying(productDomain -> {
					assertEquals(resultExpected.name(), productDomain.getName());
					assertEquals(resultExpected.description(), productDomain.getDescription());
					assertEquals(resultExpected.price(), productDomain.getPrice());
					assertThat(productDomain.isActive()).isTrue();
				});
	}

	private ProductQueryNative productInDatabase(long productId) {
		var queryString = "SELECT p.id, p.name, p.description, p.price, p.is_active FROM products p WHERE p.id = :id";
		var query = entityManager.createNativeQuery(queryString, ProductQueryNative.class);
		query.setParameter("id", productId);
		return (ProductQueryNative) query.getSingleResult();
	}

	private boolean productExistsInDatabase() {
		var queryString = "SELECT COUNT(p.id) FROM products p WHERE p.id = :id";
		var query = entityManager.createNativeQuery(queryString);
		query.setParameter("id", this.persistenceProductId);
		var resultQueryNative = (long) query.getSingleResult();
		return resultQueryNative > 0;
	}

	record ProductQueryNative(Long id, String name, String description, Double price, Character active) {}
}