package com.futurum.marketing.campaign;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	@EntityGraph(attributePaths = {"product", "product.seller", "keywords", "town"})
	List<Campaign> findByProductIdOrderByIdAsc(Long productId);

	@EntityGraph(attributePaths = {"product", "product.seller", "keywords", "town"})
	Optional<Campaign> findById(Long id);
}
