package com.futurum.marketing.campaign.service;

import com.futurum.marketing.campaign.Campaign;
import com.futurum.marketing.campaign.CampaignRepository;
import com.futurum.marketing.campaign.dto.CampaignDto;
import com.futurum.marketing.campaign.dto.UpsertCampaignRequest;
import com.futurum.marketing.dictionary.dto.TownDto;
import com.futurum.marketing.dictionary.service.DictionaryFacade;
import com.futurum.marketing.exception.InsufficientFundsException;
import com.futurum.marketing.exception.ResourceNotFoundException;
import com.futurum.marketing.product.Product;
import com.futurum.marketing.product.service.ProductFacade;
import com.futurum.marketing.seller.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.futurum.marketing.util.MoneyUtils.ZERO;
import static com.futurum.marketing.util.MoneyUtils.normalizeMoney;

@Service
@RequiredArgsConstructor
class CampaignService implements CampaignFacade {

	private final CampaignRepository campaignRepository;
	private final ProductFacade productFacade;
	private final DictionaryFacade dictionaryFacade;

	@Override
	@Transactional
	public CampaignDto create(long productId, UpsertCampaignRequest request) {
		Product product = productFacade.getEntity(productId);
		Seller seller = product.getSeller();

		BigDecimal fund = normalizeMoney(request.campaignFund());
		deductFromSeller(seller, fund);

		Campaign campaign = Campaign.builder()
				.product(product)
				.name(request.campaignName())
				.keywords(dictionaryFacade.resolveKeywords(request.keywords()))
				.bidAmount(normalizeMoney(request.bidAmount()))
				.campaignFund(fund)
				.status(request.status())
				.town(dictionaryFacade.resolveTown(request.townId()))
				.radiusKm(request.radiusKm())
				.build();

		Campaign saved = campaignRepository.save(campaign);
		return toDto(campaignRepository.findById(saved.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Campaign %d not found".formatted(saved.getId()))));
	}

	@Override
	@Transactional(readOnly = true)
	public CampaignDto get(long campaignId) {
		return toDto(getEntity(campaignId));
	}

	@Override
	@Transactional(readOnly = true)
	public Campaign getEntity(long campaignId) {
		return campaignRepository.findById(campaignId)
				.orElseThrow(() -> new ResourceNotFoundException("Campaign %d not found".formatted(campaignId)));
	}

	@Override
	@Transactional(readOnly = true)
	public List<CampaignDto> listByProduct(long productId) {
		productFacade.getEntity(productId);
		return campaignRepository.findByProductIdOrderByIdAsc(productId).stream()
				.map(this::toDto)
				.toList();
	}

	@Override
	@Transactional
	public CampaignDto update(long campaignId, UpsertCampaignRequest request) {
		Campaign campaign = getEntity(campaignId);
		Seller seller = campaign.getProduct().getSeller();

		BigDecimal newFund = normalizeMoney(request.campaignFund());
		BigDecimal oldFund = normalizeMoney(campaign.getCampaignFund());
		BigDecimal delta = newFund.subtract(oldFund);
		//logika do ustalenia, polecenie mało precyzyjne
		if (delta.compareTo(ZERO) > 0) {
			deductFromSeller(seller, delta);
		} else if (delta.compareTo(ZERO) < 0) {
			refundToSeller(seller, delta.abs());
		}

		campaign.setName(request.campaignName());
		campaign.setKeywords(dictionaryFacade.resolveKeywords(request.keywords()));
		campaign.setBidAmount(normalizeMoney(request.bidAmount()));
		campaign.setCampaignFund(newFund);
		campaign.setStatus(request.status());
		campaign.setTown(dictionaryFacade.resolveTown(request.townId()));
		campaign.setRadiusKm(request.radiusKm());

		return toDto(campaignRepository.findById(campaign.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Campaign %d not found".formatted(campaign.getId()))));
	}

	@Override
	@Transactional
	public void delete(long campaignId) {
		Campaign campaign = getEntity(campaignId);
		Seller seller = campaign.getProduct().getSeller();
		//czy powinno zwracać?//logika do ustalenia, polecenie mało precyzyjne
		refundToSeller(seller, normalizeMoney(campaign.getCampaignFund()));
		campaignRepository.delete(campaign);
	}

	private CampaignDto toDto(Campaign campaign) {
		TownDto town = campaign.getTown() == null ? null : new TownDto(campaign.getTown().getId(), campaign.getTown().getName());
		List<String> keywords = campaign.getKeywords().stream().map(k -> k.getValue()).sorted().toList();
		return new CampaignDto(
				campaign.getId(),
				campaign.getProduct().getId(),
				campaign.getProduct().getSeller().getId(),
				campaign.getName(),
				keywords,
				campaign.getBidAmount(),
				campaign.getCampaignFund(),
				campaign.getStatus(),
				town,
				campaign.getRadiusKm(),
				campaign.getProduct().getSeller().getEmeraldBalance()
		);
	}

	private static void deductFromSeller(Seller seller, BigDecimal amount) {
		Objects.requireNonNull(seller, "seller");
		BigDecimal newBalance = normalizeMoney(seller.getEmeraldBalance()).subtract(normalizeMoney(amount));
		if (newBalance.compareTo(ZERO) < 0) {
			throw new InsufficientFundsException(
					"Insufficient Emerald funds. Required=%s, Available=%s".formatted(amount, seller.getEmeraldBalance())
			);
		}
		seller.setEmeraldBalance(newBalance);
	}

	private static void refundToSeller(Seller seller, BigDecimal amount) {
		Objects.requireNonNull(seller, "seller");
		seller.setEmeraldBalance(normalizeMoney(seller.getEmeraldBalance()).add(normalizeMoney(amount)));
	}
}
