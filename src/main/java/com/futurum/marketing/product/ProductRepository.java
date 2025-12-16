package com.futurum.marketing.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@EntityGraph(attributePaths = {"seller"})
	List<Product> findBySellerIdOrderByIdAsc(Long sellerId);

	@EntityGraph(attributePaths = {"seller"})
	Optional<Product> findById(Long id);
}
