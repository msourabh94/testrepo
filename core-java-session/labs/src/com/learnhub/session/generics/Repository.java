package com.learnhub.session.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Type-safe in-memory store. One class, many entity types.
 * T is a type parameter — decided by the caller, checked by the compiler.
 */
public class Repository<T> {
    private final Map<Integer, T> store = new HashMap<Integer, T>();

    public void save(int id, T entity) {
        store.put(id, entity);
    }

    public T find(int id) {
        return store.get(id);
    }

    public List<T> findAll() {
        return Collections.unmodifiableList(new ArrayList<T>(store.values()));
    }

    public int size() {
        return store.size();
    }
}
