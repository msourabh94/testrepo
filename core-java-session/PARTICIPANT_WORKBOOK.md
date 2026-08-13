# Participant Workbook — Core Java Session

Keep this file open. Type in the labs under `core-java-session/labs/src`. Do not paste from slides until you have tried.

Baseline: JDK 8+. From `core-java-session/labs`:

```bash
./run-labs.sh
```

Or compile in your IDE with source root = `labs/src`.

---

## Mental model (fill during the first 30 minutes)

| Idea | One sentence in your words | LearnHub example |
|------|----------------------------|------------------|
| Class | | |
| Object | | |
| Encapsulation | | |
| Inheritance | | |
| Polymorphism | | |
| Abstraction | | |
| Interface | | |
| List | | |
| Set | | |
| Map | | |
| Generic type | | |

---

## Exercise 1 — Classes & inheritance (pairs, 5 min)

Add `CertificateTrack extends Content`.

Requirements:
- Constructor takes `id`, `title`, `category`, `instructor`, `modules` (int)
- `formatLabel()` returns `CERT modules=N`
- It is enrollable via the parent
- It is **not** `Priced` (included in subscription)

Hint: copy `Assessment` and change the label.

**Check:** `DemoOOP` still compiles after you add a `CertificateTrack` to the catalog list.

---

## Exercise 2 — Polymorphism (solo, 5 min)

Without using `instanceof`, print a line for every catalog item:

```
TITLE | enrollments=N
```

Then answer: which method dispatch made this possible?

**Stretch:** add `default String summary()` on `Enrollable` (Java 8+) *or* a method on `Content`. Which is the better home, and why?

---

## Exercise 3 — Collections (pairs, 8 min)

In `CatalogService`, add:

```java
public Set<String> uniqueInstructors()
```

Rules:
- Return instructor **names**
- No duplicates
- Order does not matter (`HashSet` is fine)

Then add:

```java
public Map<String, Integer> enrollmentByCategory()
```

Rules:
- Key = category
- Value = **sum** of enrollment counts in that category

**Check:** after enrolling two Backend items, Backend’s total is 2 (or whatever you enrolled).

---

## Exercise 4 — Generics (homework if not live)

Write:

```java
public final class Pager<T> {
    public List<T> page(List<T> items, int offset, int limit) { ... }
}
```

Constraints:
- Empty list if offset is past the end
- Never throw if `items` is empty
- Throw `IllegalArgumentException` if offset < 0 or limit <= 0

Write a tiny `main` that pages the catalog `listAll()`.

---

## Exercise 5 — Stretch (bored / senior)

1. Why is `Content.equals` based on `id` only? What breaks if two different titles share an id? What breaks if you compare by title?
2. Implement `void copyPriced(List<? extends Priced> src, List<? super Priced> dest)`.
3. Explain why `List<VideoCourse>` is **not** a `List<Content>` (invariance). Draw the heap if it were allowed.

---

## Exercise 6 — Map this repo

Open `src/main/java/com/quantiphi/learnhub/model/Course.java`.

Discuss with your pair:
- Which fields would become **types** instead of Strings? (`type`, `price`, `rating`)
- Where would a `Map<Integer, Course>` live in `LearnHubServiceImpl`?
- If `Course` is a JPA entity, should it also be your polymorphism root? (Usually: no — keep a domain type separate from the persistence DTO/entity.)

Write 5 bullets. This is the “we work here” close.

---

## During demos — watch for

1. `LiveWorkshop` rejecting the third enroll
2. `HashSet` scrambling tag order vs `LinkedHashSet`
3. `ConcurrentModificationException` if you remove inside for-each
4. `ClassCastException` from a raw `List`

If you cannot explain those four after the session, you missed the plot. Ask in Q&A.
