package com.futurum.marketing.seller.service;

import com.futurum.marketing.exception.ResourceNotFoundException;
import com.futurum.marketing.seller.Seller;
import com.futurum.marketing.seller.service.SellerFacade;
import com.futurum.marketing.seller.SellerRepository;
import com.futurum.marketing.seller.dto.CreateSellerRequest;
import com.futurum.marketing.seller.dto.SellerDto;
import com.futurum.marketing.seller.dto.TopUpRequest;
import com.futurum.marketing.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class SellerService implements SellerFacade {
	private final SellerRepository sellerRepository;

	@Override
	@Transactional
	public SellerDto create(CreateSellerRequest request) {
		Seller seller = Seller.builder()
				.name(request.name())
				.emeraldBalance(MoneyUtils.normalizeMoney(request.initialEmeraldBalance()))
				.build();
		return toDto(sellerRepository.save(seller));
	}

	@Override
	@Transactional(readOnly = true)
	public SellerDto get(long sellerId) {
		return toDto(getEntity(sellerId));
	}

	@Override
	@Transactional(readOnly = true)
	public Seller getEntity(long sellerId) {
		return sellerRepository.findById(sellerId)
				.orElseThrow(() -> new ResourceNotFoundException("Seller %d not found".formatted(sellerId)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<SellerDto> list() {
		return sellerRepository.findAll().stream()
				.map(this::toDto)
				.toList();
	}

	@Override
	@Transactional
	public SellerDto topUp(long sellerId, TopUpRequest request) {
		Seller seller = getEntity(sellerId);
		seller.setEmeraldBalance(MoneyUtils.normalizeMoney(seller.getEmeraldBalance().add(MoneyUtils.normalizeMoney(request.amount()))));
		return toDto(seller);
	}

	private SellerDto toDto(Seller seller) {
		return new SellerDto(seller.getId(), seller.getName(), seller.getEmeraldBalance());
	}
}
