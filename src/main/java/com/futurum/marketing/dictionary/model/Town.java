package com.futurum.marketing.dictionary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(
		name = "town",
		uniqueConstraints = @UniqueConstraint(name = "uk_town_name", columnNames = "name")
)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Town {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 10)
	private String name;
}
