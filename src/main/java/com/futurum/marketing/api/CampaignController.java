package com.futurum.marketing.api;

import com.futurum.marketing.campaign.dto.CampaignDto;
import com.futurum.marketing.campaign.dto.UpsertCampaignRequest;
import com.futurum.marketing.campaign.service.CampaignFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/campaigns")
public class CampaignController {
	private final CampaignFacade campaignService;

	@PostMapping("/products/{productId}/campaigns")
	@ResponseStatus(HttpStatus.CREATED)
	public CampaignDto create(@PathVariable long productId, @Valid @RequestBody UpsertCampaignRequest request) {
		return campaignService.create(productId, request);
	}

	@GetMapping("/products/{productId}/campaigns")
	public List<CampaignDto> listByProduct(@PathVariable long productId) {
		return campaignService.listByProduct(productId);
	}

	@GetMapping("/{campaignId}")
	public CampaignDto get(@PathVariable long campaignId) {
		return campaignService.get(campaignId);
	}

	@PutMapping("/{campaignId}")
	public CampaignDto update(@PathVariable long campaignId, @Valid @RequestBody UpsertCampaignRequest request) {
		return campaignService.update(campaignId, request);
	}

	@DeleteMapping("/{campaignId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable long campaignId) {
		campaignService.delete(campaignId);
	}
}
