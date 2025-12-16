package com.futurum.marketing.dictionary.service;

import com.futurum.marketing.dictionary.dto.KeywordDto;
import com.futurum.marketing.dictionary.dto.TownDto;
import com.futurum.marketing.dictionary.model.Keyword;
import com.futurum.marketing.dictionary.model.Town;
import com.futurum.marketing.dictionary.repository.KeywordRepository;
import com.futurum.marketing.dictionary.repository.TownRepository;
import com.futurum.marketing.exception.InvalidDictionaryValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class DictionaryService implements DictionaryFacade {
    private final TownRepository townRepository;
    private final KeywordRepository keywordRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TownDto> getTowns() {
        return townRepository.findAllByOrderByNameAsc()
                .stream()
                .map(town -> new TownDto(town.getId(), town.getName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeywordDto> searchKeywords(String query) {
        String q = query == null ? "" : query.trim();
        return keywordRepository.findTop20ByValueStartingWithIgnoreCaseOrderByValueAsc(q)
                .stream()
                .map(keyword -> new KeywordDto(keyword.getId(), keyword.getValue()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Town resolveTown(Long townId) {
        if (townId == null) {
            return null;
        }
        return townRepository.findById(townId)
                .orElseThrow(() -> new InvalidDictionaryValueException("Town %d not found".formatted(townId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Keyword> resolveKeywords(Set<String> keywords) {
        Set<String> normalized = normalizeKeywords(keywords);
        if (normalized.isEmpty()) {
            throw new InvalidDictionaryValueException("Keywords must not be empty");
        }
        List<Keyword> found = keywordRepository.findByValueIn(normalized);
        if (found.size() != normalized.size()) {
            Set<String> foundValues = found.stream().map(Keyword::getValue).collect(Collectors.toSet());
            Set<String> missing = new LinkedHashSet<>(normalized);
            missing.removeAll(foundValues);
            throw new InvalidDictionaryValueException("Unknown keywords: %s".formatted(missing));
        }
        return new LinkedHashSet<>(found);
    }

    private Set<String> normalizeKeywords(Set<String> keywords) {
        if (keywords == null) {
            return Set.of();
        }
        LinkedHashSet<String> normalized = new LinkedHashSet<>();
        for (String k : keywords) {
            if (k == null) {
                continue;
            }
            String v = k.trim().toLowerCase();
            if (!v.isBlank()) {
                normalized.add(v);
            }
        }
        return normalized;
    }
}
