package com.futurum.marketing.campaign.service;

import com.futurum.marketing.campaign.Campaign;
import com.futurum.marketing.campaign.dto.CampaignDto;
import com.futurum.marketing.campaign.dto.UpsertCampaignRequest;

import java.util.List;

public interface CampaignFacade {
    CampaignDto create(long productId, UpsertCampaignRequest request);

    CampaignDto get(long campaignId);

    Campaign getEntity(long campaignId);

    List<CampaignDto> listByProduct(long productId);

    CampaignDto update(long campaignId, UpsertCampaignRequest request);

    void delete(long campaignId);
}
