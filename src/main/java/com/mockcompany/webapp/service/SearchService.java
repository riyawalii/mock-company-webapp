package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class SearchService {

    private final ProductItemRepository productItemRepository;
    private final EntityManager entityManager;

    public SearchService(ProductItemRepository productItemRepository, EntityManager entityManager) {
        this.productItemRepository = productItemRepository;
        this.entityManager = entityManager;
    }

    public Collection<ProductItem> search(String query) {
        String lowerCaseQuery = query.toLowerCase();
        List<ProductItem> itemList = new ArrayList<>();

        for (ProductItem item : productItemRepository.findAll()) {
            if (item.getName().toLowerCase().contains(lowerCaseQuery) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerCaseQuery))) {
                itemList.add(item);
            }
        }

        return itemList;
    }

    public int countProductsMatchingTerm(String term) {
        String lowerTerm = term.toLowerCase();
        Iterable<ProductItem> items = productItemRepository.findAll();
        int count = 0;

        for (ProductItem item : items) {
            if (item.getName().toLowerCase().contains(lowerTerm) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerTerm))) {
                count++;
            }
        }

        return count;
    }

    public Map<String, Integer> getSearchTermHits(List<String> terms) {
        Map<String, Integer> hits = new HashMap<>();
        for (String term : terms) {
            hits.put(term, countProductsMatchingTerm(term));
        }
        return hits;
    }

    public int getTotalProductCount() {
        return (int) productItemRepository.count();
    }
}
