package com.mockcompany.webapp.service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;

    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    /**
     * Searches for products that match the given query in their name or description.
     */
    public Collection<ProductItem> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String lowerCaseQuery = query.toLowerCase();

        // Convert Iterable to Stream and filter
        return StreamSupport.stream(productItemRepository.findAll().spliterator(), false)
                .filter(item -> matchesQuery(item, lowerCaseQuery))
                .toList();
    }

    /**
     * Counts the number of products matching a specific search term.
     */
    public int countProductsMatchingTerm(String term) {
        if (term == null || term.trim().isEmpty()) {
            return 0;
        }

        String lowerTerm = term.toLowerCase();

        return (int) StreamSupport.stream(productItemRepository.findAll().spliterator(), false)
                .filter(item -> matchesQuery(item, lowerTerm))
                .count();
    }

    /**
     * Helper method to check if a product matches a query.
     */
    private boolean matchesQuery(ProductItem item, String query) {
        return (item.getName() != null && item.getName().toLowerCase().contains(query)) ||
                (item.getDescription() != null && item.getDescription().toLowerCase().contains(query));
    }
    }
