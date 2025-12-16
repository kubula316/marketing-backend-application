package com.futurum.marketing.api;

import com.futurum.marketing.product.service.ProductFacade;
import com.futurum.marketing.product.dto.CreateProductRequest;
import com.futurum.marketing.product.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
	private final ProductFacade productService;

	@PostMapping("/sellers/{sellerId}/products")
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDto create(@PathVariable long sellerId, @Valid @RequestBody CreateProductRequest request) {
		return productService.create(sellerId, request);
	}

	@GetMapping("/sellers/{sellerId}/products")
	public List<ProductDto> listBySeller(@PathVariable long sellerId) {
		return productService.listBySeller(sellerId);
	}

	@GetMapping("/{productId}")
	public ProductDto get(@PathVariable long productId) {
		return productService.get(productId);
	}
}
