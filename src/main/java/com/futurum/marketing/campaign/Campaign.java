package com.futurum.marketing.campaign;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import com.futurum.marketing.dictionary.model.Keyword;
import com.futurum.marketing.dictionary.model.Town;
import com.futurum.marketing.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "campaign")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campaign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "campaign_keyword",
			joinColumns = @JoinColumn(name = "campaign_id"),
			inverseJoinColumns = @JoinColumn(name = "keyword_id")
	)
	private Set<Keyword> keywords = new LinkedHashSet<>();

	@Column(name = "bid_amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal bidAmount;

	@Column(name = "campaign_fund", nullable = false, precision = 19, scale = 2)
	private BigDecimal campaignFund;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private CampaignStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "town_id")
	private Town town;

	@Column(name = "radius_km", nullable = false)
	private Integer radiusKm;
}
