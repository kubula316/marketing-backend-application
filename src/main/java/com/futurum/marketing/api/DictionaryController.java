package com.futurum.marketing.api;

import com.futurum.marketing.dictionary.service.DictionaryFacade;
import com.futurum.marketing.dictionary.dto.KeywordDto;
import com.futurum.marketing.dictionary.dto.TownDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryFacade dictionaryService;

    @GetMapping("/towns")
    public List<TownDto> towns() {
        return dictionaryService.getTowns();
    }

    @GetMapping("/keywords")
    public List<KeywordDto> keywords(@RequestParam(value = "query", required = false) String query) {
        return dictionaryService.searchKeywords(query);
    }
}
