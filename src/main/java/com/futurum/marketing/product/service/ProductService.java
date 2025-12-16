package com.futurum.marketing.product.service;

import com.futurum.marketing.exception.ResourceNotFoundException;
import com.futurum.marketing.product.Product;
import com.futurum.marketing.product.ProductRepository;
import com.futurum.marketing.product.dto.CreateProductRequest;
import com.futurum.marketing.product.dto.ProductDto;
import com.futurum.marketing.seller.Seller;
import com.futurum.marketing.seller.service.SellerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProductService implements ProductFacade {
	private final ProductRepository productRepository;
	private final SellerFacade sellerFacade;

	@Override
	@Transactional
	public ProductDto create(long sellerId, CreateProductRequest request) {
		Seller seller = sellerFacade.getEntity(sellerId);
		Product product = Product.builder()
				.seller(seller)
				.name(request.name())
				.build();
		return toDto(productRepository.save(product));
	}

	@Override
	@Transactional(readOnly = true)
	public ProductDto get(long productId) {
		return toDto(getEntity(productId));
	}

	@Override
	@Transactional(readOnly = true)
	public Product getEntity(long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product %d not found".formatted(productId)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductDto> listBySeller(long sellerId) {
		sellerFacade.getEntity(sellerId);
		return productRepository.findBySellerIdOrderByIdAsc(sellerId).stream()
				.map(this::toDto)
				.toList();
	}

	private ProductDto toDto(Product product) {
		return new ProductDto(product.getId(), product.getSeller().getId(), product.getName());
	}
}
