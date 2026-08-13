package com.banking.session.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type-safe in-memory store. One class, many entity types.
 */
public class Repository<T> {
    private final Map<String, T> store = new HashMap<String, T>();

    public void save(String id, T entity) {
        store.put(id, entity);
    }

    public T find(String id) {
        return store.get(id);
    }

    public List<T> findAll() {
        return Collections.unmodifiableList(new ArrayList<T>(store.values()));
    }

    public int size() {
        return store.size();
    }
}
