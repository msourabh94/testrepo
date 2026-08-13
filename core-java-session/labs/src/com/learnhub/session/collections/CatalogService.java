package com.learnhub.session.collections;

import com.learnhub.session.oop.Content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Real catalog operations you would write in a service layer
 * (similar to LearnHubService in this repo).
 */
public class CatalogService {
    private final List<Content> orderedCatalog = new ArrayList<Content>();
    private final Map<Integer, Content> byId = new HashMap<Integer, Content>();
    private final Map<String, Set<Content>> byCategory = new HashMap<String, Set<Content>>();

    public void add(Content content) {
        if (byId.containsKey(content.getId())) {
            throw new IllegalArgumentException("Duplicate id: " + content.getId());
        }
        orderedCatalog.add(content);
        byId.put(content.getId(), content);

        Set<Content> bucket = byCategory.get(content.getCategory());
        if (bucket == null) {
            bucket = new HashSet<Content>();
            byCategory.put(content.getCategory(), bucket);
        }
        bucket.add(content);
    }

    public Content findById(int id) {
        return byId.get(id);
    }

    public List<Content> listAll() {
        return Collections.unmodifiableList(orderedCatalog);
    }

    public Set<Content> byCategory(String category) {
        Set<Content> found = byCategory.get(category);
        if (found == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(found);
    }

    /** Safe removal while iterating — never for-each + remove. */
    public int dropUnenrolled() {
        int removed = 0;
        Iterator<Content> it = orderedCatalog.iterator();
        while (it.hasNext()) {
            Content item = it.next();
            if (item.getEnrollmentCount() == 0) {
                it.remove();
                byId.remove(item.getId());
                Set<Content> bucket = byCategory.get(item.getCategory());
                if (bucket != null) {
                    bucket.remove(item);
                }
                removed++;
            }
        }
        return removed;
    }

    public List<Content> topByTitle(int limit) {
        List<Content> copy = new ArrayList<Content>(orderedCatalog);
        Collections.sort(copy, new Comparator<Content>() {
            @Override
            public int compare(Content a, Content b) {
                return a.getTitle().compareToIgnoreCase(b.getTitle());
            }
        });
        if (limit >= copy.size()) {
            return copy;
        }
        return copy.subList(0, limit);
    }

    /** LinkedHashMap preserves insertion order — useful for "recently added". */
    public Map<Integer, String> recentTitles() {
        Map<Integer, String> recent = new LinkedHashMap<Integer, String>();
        for (Content item : orderedCatalog) {
            recent.put(item.getId(), item.getTitle());
        }
        return recent;
    }
}
