package br.com.jonatas.ecommerce.adapter.out.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.YesNoConverter;

@Entity
@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private Double price;
	@Convert(converter = YesNoConverter.class)
	@Column(name = "is_active", columnDefinition = "CHAR(1) DEFAULT 'Y'")
	private boolean active;
}
