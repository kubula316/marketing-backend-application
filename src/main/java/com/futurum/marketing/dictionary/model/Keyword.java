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
		name = "dictionary_keyword",
		uniqueConstraints = @UniqueConstraint(name = "uk_dictionary_keyword_value", columnNames = "keyword_value")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Keyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "keyword_value", nullable = false, length = 10)
	private String value;
}
