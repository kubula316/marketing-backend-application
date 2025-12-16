package com.futurum.marketing.campaign.dto;

import com.futurum.marketing.campaign.CampaignStatus;
import com.futurum.marketing.dictionary.dto.TownDto;

import java.math.BigDecimal;
import java.util.List;

public record CampaignDto(
        Long id,
        Long productId,
        Long sellerId,
        String campaignName,
        List<String> keywords,
        BigDecimal bidAmount,
        BigDecimal campaignFund,
        CampaignStatus status,
        TownDto town,
        Integer radiusKm,
        BigDecimal sellerEmeraldBalance
) {
}
