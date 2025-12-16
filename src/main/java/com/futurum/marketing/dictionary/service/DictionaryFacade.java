package com.futurum.marketing.dictionary.service;

import com.futurum.marketing.dictionary.dto.KeywordDto;
import com.futurum.marketing.dictionary.dto.TownDto;
import com.futurum.marketing.dictionary.model.Keyword;
import com.futurum.marketing.dictionary.model.Town;

import java.util.List;
import java.util.Set;

public interface DictionaryFacade {
    List<TownDto> getTowns();

    List<KeywordDto> searchKeywords(String query);

    Town resolveTown(Long townId);

    Set<Keyword> resolveKeywords(Set<String> keywords);
}
