package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ReportController {

    private final SearchService searchService;

    @Autowired
    public ReportController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        SearchReportResponse response = new SearchReportResponse();

        List<String> searchTerms = Arrays.asList("Cool", "Kids", "Amazing", "Perfect");
        Map<String, Integer> hits = searchService.getSearchTermHits(searchTerms);

        response.setSearchTermHits(hits);
        response.setProductCount(searchService.getTotalProductCount());

        return response;
    }
}
