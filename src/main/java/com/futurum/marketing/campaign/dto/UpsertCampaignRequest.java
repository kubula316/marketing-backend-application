package com.futurum.marketing.campaign.dto;

import com.futurum.marketing.campaign.CampaignStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record UpsertCampaignRequest(
        @NotBlank String campaignName,
        @NotEmpty Set<@NotBlank String> keywords,
        @NotNull @DecimalMin(value = "0.01") BigDecimal bidAmount,
        @NotNull @DecimalMin(value = "0.01") BigDecimal campaignFund,
        @NotNull CampaignStatus status,
        Long townId,
        @NotNull @Min(1) Integer radiusKm
) {
}
