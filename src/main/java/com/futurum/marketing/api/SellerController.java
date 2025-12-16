package com.futurum.marketing.api;

import com.futurum.marketing.seller.service.SellerFacade;
import com.futurum.marketing.seller.dto.CreateSellerRequest;
import com.futurum.marketing.seller.dto.SellerDto;
import com.futurum.marketing.seller.dto.TopUpRequest;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
	private final SellerFacade sellerService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SellerDto create(@Valid @RequestBody CreateSellerRequest request) {
		return sellerService.create(request);
	}

	@GetMapping("/{sellerId}")
	public SellerDto get(@PathVariable long sellerId) {
		return sellerService.get(sellerId);
	}

	@PostMapping("/{sellerId}/topup")
	public SellerDto topUp(@PathVariable long sellerId, @Valid @RequestBody TopUpRequest request) {
		return sellerService.topUp(sellerId, request);
	}
}
