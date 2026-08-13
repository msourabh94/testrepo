# Exercise solutions (facilitator only — do not project first)

Share after pairs have tried, or as a gist after the session.

## Exercise 1 — CertificateTrack

```java
package com.learnhub.session.oop;

public class CertificateTrack extends Content {
    private final int modules;

    public CertificateTrack(int id, String title, String category,
                            Instructor instructor, int modules) {
        super(id, title, category, instructor);
        this.modules = modules;
    }

    @Override
    protected String formatLabel() {
        return "CERT modules=" + modules;
    }
}
```

Add to `DemoOOP` catalog list and re-run.

## Exercise 2 — polymorphic print

```java
for (Content item : catalog) {
    System.out.println(item.getTitle() + " | enrollments=" + item.getEnrollmentCount());
}
```

Dispatch: `getEnrollmentCount()` is on `Enrollable` / `Content`. No instanceof.

Better home for `summary()`: `Content`, because it needs `title` (state). Default methods cannot hold per-instance fields.

## Exercise 3 — CatalogService methods

```java
public Set<String> uniqueInstructors() {
    Set<String> names = new HashSet<String>();
    for (Content item : orderedCatalog) {
        if (item.getInstructor() != null) {
            names.add(item.getInstructor().getName());
        }
    }
    return names;
}

public Map<String, Integer> enrollmentByCategory() {
    Map<String, Integer> totals = new HashMap<String, Integer>();
    for (Content item : orderedCatalog) {
        Integer soFar = totals.get(item.getCategory());
        if (soFar == null) {
            soFar = 0;
        }
        totals.put(item.getCategory(), soFar + item.getEnrollmentCount());
    }
    return totals;
}
```

## Exercise 4 — Pager

```java
package com.learnhub.session.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Pager<T> {
    public List<T> page(List<T> items, int offset, int limit) {
        if (offset < 0 || limit <= 0) {
            throw new IllegalArgumentException("offset >= 0 and limit > 0 required");
        }
        if (items == null || items.isEmpty() || offset >= items.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(items.size(), offset + limit);
        return new ArrayList<T>(items.subList(offset, end));
    }
}
```

## Exercise 5 — notes

1. Id equality matches catalog PK. Duplicate ids with different titles mean a data bug; title equality would treat relists as different products and break HashSet.
2. `copyPriced`: `for (Priced p : src) dest.add(p);`
3. Invariance: if `List<VideoCourse>` were a `List<Content>`, you could add an Assessment through the parent-typed reference and the original list would contain a non-video.

## Exercise 6 — talking points

- `type` → enum or subclasses; `price`/`rating` → numeric types
- `Map<Integer, Course>` as a cache in the service, keyed by `pnum`
- Keep JPA `Course` as persistence; introduce domain types when behavior (enroll, seats) appears
