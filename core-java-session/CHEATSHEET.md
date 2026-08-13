# Core Java Cheatsheet (session takeaway)

## OOP in one table

| Pillar | Meaning | In our catalog |
|--------|---------|----------------|
| Encapsulation | Hide state; protect invariants | private fields, validating constructors, `enroll()` |
| Inheritance | is-a shared family | `VideoCourse extends Content` |
| Polymorphism | one reference, many behaviors | `Content.catalogLine()` / `enroll()` |
| Abstraction | show what, hide how | `abstract formatLabel()`, `interface Priced` |

**is-a** → extend a class. **has-a** → field (`Instructor`). **can-do** → interface (`Enrollable`).

```
Content (abstract)
  ├── VideoCourse     implements Priced
  ├── LiveWorkshop    implements Priced  (overrides enroll: seat limit)
  └── Assessment      not priced
```

- Override = runtime dispatch. Overload = compile-time.
- Always `@Override`.
- If you override `equals`, override `hashCode`.
- Abstract class: shared state. Interface: capability (multiple).

## Collections — pick in 10 seconds

| Need | Type | Usual impl |
|------|------|------------|
| Ordered catalog, index, duplicates OK | `List` | `ArrayList` |
| Unique tags / emails | `Set` | `HashSet` |
| Unique + insertion order | `Set` | `LinkedHashSet` |
| Unique + sorted | `Set` | `TreeSet` |
| id → course | `Map` | `HashMap` |
| Stable iteration of map | `Map` | `LinkedHashMap` |
| Sorted keys | `Map` | `TreeMap` |
| Concurrent map | `Map` | `ConcurrentHashMap` |

Do **not** start new code with `Vector`, `Hashtable`, `Stack`.

### Iterator rule

```java
Iterator<Content> it = list.iterator();
while (it.hasNext()) {
    if (shouldDrop(it.next())) {
        it.remove();   // OK
    }
}
```

`for (Content c : list) { list.remove(c); }` → `ConcurrentModificationException`.

## Generics

```java
class Repository<T> { void save(int id, T e); T find(int id); }

void print(List<? extends Content> src); // read Content  (producer)
void put  (List<? super Priced> dest, Priced p); // write Priced (consumer)
```

- Raw `List` → runtime `ClassCastException`. Parameterize everything.
- `List<VideoCourse>` is **not** a `List<Content>` (invariance).
- Erasure: no `new T()` without a `Class<T>`.

## Copy-paste decisions

```java
List<Content> catalog = new ArrayList<Content>();
Set<String> tags = new LinkedHashSet<String>();
Map<Integer, Content> byId = new HashMap<Integer, Content>();
```

Return `Collections.unmodifiableList(...)` from services so callers cannot corrupt your indexes.

## Three bugs this session exists to prevent

1. `switch (course.getType())` exploding as new types appear → **polymorphism**
2. Duplicate ids / lost entries in a list → **Map by id + equals/hashCode**
3. “It compiled” then exploded in prod → **generics, not raw types**
