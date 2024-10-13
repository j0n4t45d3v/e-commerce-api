package br.com.jonatas.ecommerce.adapter.out.impl;

import br.com.jonatas.ecommerce.adapter.out.ProductRepository;
import br.com.jonatas.ecommerce.adapter.out.entity.ProductEntity;
import br.com.jonatas.ecommerce.core.domain.inventory.ProductDomain;
import br.com.jonatas.ecommerce.gateway.out.inventory.ProductRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryGatewayImpl implements ProductRepositoryGateway {
	private final ProductRepository productRepository;

	@Override
	public Long save(ProductDomain productDomain) {
		return this.productRepository.save(this.toEntity(productDomain)).getId();
	}

	@Override
	public boolean existsById(Long id) {
		return this.productRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		this.productRepository.deleteById(id);
	}

	@Override
	public List<ProductDomain> findAll() {
		return this.productRepository.findAll().stream()
				.map(this::toDomain)
				.toList();
	}

	@Override
	public Optional<ProductDomain> findById(Long id) {
		return this.productRepository.findById(id)
				.map(this::toDomain);
	}

	private ProductEntity toEntity(ProductDomain productDomain) {
		return ProductEntity.builder()
				.id(productDomain.getId())
				.name(productDomain.getName())
				.description(productDomain.getDescription())
				.price(productDomain.getPrice())
				.active(productDomain.isActive())
				.build();
	}

	private ProductDomain toDomain(ProductEntity productEntity) {
		return new ProductDomain(
				productEntity.getId(),
				productEntity.getName(),
				productEntity.getDescription(),
				productEntity.getPrice(),
				productEntity.isActive()
		);
	}
}
