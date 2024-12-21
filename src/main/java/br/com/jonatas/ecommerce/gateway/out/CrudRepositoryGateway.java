package br.com.jonatas.ecommerce.gateway.out;

import java.util.List;
import java.util.Optional;

public interface CrudRepositoryGateway<T, I> {
	I save(T entity);
	boolean existsById(I id);
	Optional<T> findById(I id);
	List<T> findAll();
	void deleteById(I id);

}
