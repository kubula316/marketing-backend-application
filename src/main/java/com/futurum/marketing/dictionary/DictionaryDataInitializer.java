package com.futurum.marketing.dictionary;

import java.util.List;

import com.futurum.marketing.dictionary.model.Keyword;
import com.futurum.marketing.dictionary.model.Town;
import com.futurum.marketing.dictionary.repository.KeywordRepository;
import com.futurum.marketing.dictionary.repository.TownRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DictionaryDataInitializer implements CommandLineRunner {

	//Klasa do mockowania danych, normalnie mozna utworzyc oeprcacje crudowe dla słonika.
	private final TownRepository townRepository;
	private final KeywordRepository keywordRepository;

	@Override
	@Transactional
	public void run(String... args) {
		if (townRepository.count() == 0) {
			townRepository.saveAll(List.of(
					Town.builder().name("Warszawa").build(),
					Town.builder().name("Kraków").build(),
					Town.builder().name("Wrocław").build(),
					Town.builder().name("Poznań").build(),
					Town.builder().name("Gdańsk").build(),
					Town.builder().name("Szczecin").build(),
					Town.builder().name("Łódź").build(),
					Town.builder().name("Katowice").build(),
					Town.builder().name("Lublin").build(),
					Town.builder().name("Białystok").build()
			));
		}

		if (keywordRepository.count() == 0) {
			keywordRepository.saveAll(List.of(
					Keyword.builder().value("shoes").build(),
					Keyword.builder().value("sneakers").build(),
					Keyword.builder().value("boots").build(),
					Keyword.builder().value("jacket").build(),
					Keyword.builder().value("tshirt").build(),
					Keyword.builder().value("hoodie").build(),
					Keyword.builder().value("dress").build(),
					Keyword.builder().value("bag").build(),
					Keyword.builder().value("watch").build(),
					Keyword.builder().value("laptop").build(),
					Keyword.builder().value("phone").build(),
					Keyword.builder().value("headphones").build(),
					Keyword.builder().value("gaming").build(),
					Keyword.builder().value("gift").build(),
					Keyword.builder().value("sale").build()
			));
		}
	}
}
