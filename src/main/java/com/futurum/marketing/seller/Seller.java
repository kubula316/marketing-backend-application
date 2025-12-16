package com.futurum.marketing.seller;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;

@Entity
@Table(name = "seller")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 200)
	private String name;

	@Column(name = "emerald_balance", nullable = false, precision = 19, scale = 2)
	private BigDecimal emeraldBalance;
}
