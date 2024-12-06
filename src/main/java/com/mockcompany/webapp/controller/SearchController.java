package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * This class is the entry point for the /api/products/search API. It allows clients
 * to search for products by a given query string. The search logic is delegated to
 * the SearchService, which performs the actual product lookup.
 */
@RestController
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final SearchService searchService;

    /**
     * Constructor to inject the SearchService dependency.
     *
     * @param searchService the service handling product search logic
     */
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Handles GET requests to search for products by query string.
     *
     * @param query the search query provided by the user
     * @return a collection of ProductItem objects that match the query
     */
    @GetMapping("/api/products/search")
    public ResponseEntity<Collection<ProductItem>> search(@RequestParam("query") String query) {
        logger.info("Received search request with query: {}", query);

        // Validate the input
        if (query == null || query.trim().isEmpty()) {
            logger.warn("Search query is invalid: {}", query);
            return ResponseEntity.badRequest().body(null);
        }

        try {
            // Perform the search using the SearchService
            Collection<ProductItem> results = searchService.search(query);

            if (results.isEmpty()) {
                logger.info("No products found for query: {}", query);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(results);
            }

            logger.info("Search completed successfully for query: {}", query);
            return ResponseEntity.ok(results);

        } catch (Exception e) {
            logger.error("Error occurred while processing search request: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
