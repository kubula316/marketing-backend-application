package com.futurum.marketing.dictionary.repository;

import java.util.List;

import com.futurum.marketing.dictionary.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TownRepository extends JpaRepository<Town, Long> {
	List<Town> findAllByOrderByNameAsc();
}
