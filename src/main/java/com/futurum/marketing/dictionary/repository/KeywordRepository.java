package com.futurum.marketing.dictionary.repository;

import java.util.Collection;
import java.util.List;

import com.futurum.marketing.dictionary.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	List<Keyword> findTop20ByValueStartingWithIgnoreCaseOrderByValueAsc(String value);

	List<Keyword> findByValueIn(Collection<String> values);
}
