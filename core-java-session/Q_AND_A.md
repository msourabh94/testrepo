# Prepared Q&A — Core Java Session

Use these as **short, correct** answers. If you ramble, you lose the room.

---

### OOP

**Q. Class vs object?**  
A class is the definition (fields + methods). An object is a concrete instance allocated on the heap. `Instructor` vs `new Instructor("Ada", "Foundations")`.

**Q. What is encapsulation, really?**  
Not getters. It is hiding representation and exposing operations that keep invariants true (e.g. enroll count never negative; workshop cannot overfill).

**Q. Inheritance vs composition?**  
Inheritance = is-a, compile-time coupling to a parent. Composition = has-a, usually more flexible. `VideoCourse` *is* `Content`. `Content` *has* an `Instructor`. Prefer composition unless you need polymorphic substitution.

**Q. Multiple inheritance of classes?**  
Java: one superclass. Multiple interfaces. That is why capabilities (`Priced`, `Enrollable`) are interfaces.

**Q. Abstract class vs interface?**  
Shared state + partial implementation → abstract class. Pure capability, possibly many → interface. `Content` has `id`, `title`, enrollment. `Priced` is only `getPrice()`.

**Q. Can an abstract class have a constructor?**  
Yes. Subclasses call `super(...)`. You still cannot `new Content(...)`.

**Q. Can we instantiate an interface?**  
No. You can have an anonymous class or a concrete implementor. (Java 8+ lambdas only for *functional* interfaces — one abstract method.)

**Q. Overload vs override?**  
Overload: same name, different parameter types, resolved at **compile** time. Override: same signature in a subclass, resolved at **runtime** (virtual dispatch). Polymorphism is override, not overload.

**Q. Why `@Override`?**  
If you misspell the method, the compiler fails instead of adding a new method you thought was an override.

**Q. `this` vs `super`?**  
`this` = current object. `super` = parent implementation / constructor.

**Q. Why not put `price` on `Content` as `0` for free items?**  
Magic values leak into every report and discount. `Priced` is optional; `Assessment` simply does not implement it. `instanceof Priced` at a billing boundary is acceptable.

**Q. What is the Liskov Substitution Principle?**  
You must be able to use a subclass wherever the parent is expected without breaking callers. A workshop that *throws* on enroll when the parent promises enroll-always would be a violation if the parent contract said enroll never fails. We documented the workshop as a stricter subtype — mention the tension; seat limits are still a common real design.

**Q. `final` class / method / field?**  
Class: no subclassing. Method: no override (`catalogLine()`). Field: assigned once.

**Q. Why `equals` on id?**  
Catalog identity. Needed for `HashSet`/`HashMap` keys. Must also override `hashCode`. Contract: equal objects → equal hash codes.

**Q. `==` vs `equals`?**  
`==` on objects is reference identity. `equals` is value/domain identity if you override it.

---

### Collections

**Q. Array vs ArrayList?**  
Array: fixed size, covariant (and therefore unsound). ArrayList: resizable, generic, the default List.

**Q. List vs Set vs Map in one line?**  
List = ordered sequence. Set = unique elements. Map = lookup from key to value.

**Q. HashSet vs LinkedHashSet vs TreeSet?**  
Hash: uniqueness, no order. LinkedHash: uniqueness + insertion order. Tree: uniqueness + sorted order (`Comparable` or `Comparator`). Tree is O(log n); hash is expected O(1).

**Q. HashMap vs Hashtable vs ConcurrentHashMap?**  
HashMap: unsynchronized, allows one null key. Hashtable: legacy, synchronized, no nulls — do not use. ConcurrentHashMap: concurrent readers/writers without locking the whole map.

**Q. HashMap vs TreeMap vs LinkedHashMap?**  
Same story as the sets. Default is HashMap.

**Q. Why ConcurrentModificationException?**  
A fail-fast iterator detects structural change it did not make (add/remove on the collection during for-each). Fix: `Iterator.remove()`, or collect ids to delete, or `removeIf` (Java 8).

**Q. fail-fast vs fail-safe?**  
fail-fast: `ArrayList`/`HashMap` iterators. fail-safe-ish: `ConcurrentHashMap` iterators weakly consistent, no CME, may skip/see updates.

**Q. How does HashMap work (interview)?**  
Key → `hashCode` → bucket index. Collisions: linked list, treeified when long (Java 8). `equals` confirms the key. Capacity and load factor (0.75) trigger resize.

**Q. Comparable vs Comparator?**  
Comparable = the class’s natural order (`compareTo`). Comparator = external strategy (sort by title vs by id). We used an anonymous `Comparator<Content>` for title sort.

**Q. When LinkedList?**  
Almost never for application catalogs. Queue/Deque use cases, frequent insert-at-front. Interviewers still ask; be honest.

**Q. Why unmodifiable wrappers in `CatalogService`?**  
Callers must not mutate internal lists/maps or they bypass `byId` indexes. Encapsulation for collections.

---

### Generics & iterators

**Q. Why generics?**  
Type safety at compile time. One `Repository<T>` instead of `InstructorRepository`, `CourseRepository` copies. No random `ClassCastException` from raw lists.

**Q. Type erasure?**  
The compiler checks `T`, then wipes it for the JVM. At runtime `Repository<VideoCourse>` is roughly `Repository`. You cannot `new T()` or inspect `T` without a `Class<T>` token.

**Q. Raw type?**  
`List` without `<...>`. Legacy hole. Demo shows the ClassCastException. Never introduce raw types in new code.

**Q. `T` vs `?`?**  
`T` names a type you will use again (return it, store it). `?` is a wildcard when you only need “some unknown subtype/supertype”.

**Q. PECS?**  
Producer Extends, Consumer Super. `List<? extends Content>` — you can get `Content` out. `List<? super Priced>` — you can put `Priced` in.

**Q. Why isn’t `List<VideoCourse>` a `List<Content>`?**  
If it were, you could `list.add(new Assessment(...))` through a `List<Content>` reference and corrupt a list that was supposed to hold only videos. Generics are **invariant**.

**Q. Bounded type `T extends Content & Priced`?**  
`T` must be a subtype of both. Rare; useful for methods that need catalog line *and* price.

**Q. Iterator vs ListIterator vs for-each vs Stream?**  
Iterator: traverse + safe remove. ListIterator: also previous/set/add on lists. for-each: readable, no remove. Stream: Java 8 declarative pipelines — bonus, not required today.

**Q. Enhanced for-loop internals?**  
For arrays: index. For `Iterable`: `iterator()`. That is why CME still happens.

---

### Planted questions if the room is quiet

1. Why is `Course.price` a `String` in LearnHub? (legacy/scraped data — domain should use `BigDecimal` + currency.)
2. Should JPA entities be your OOP hierarchy? (Prefer not; persistence model ≠ domain model.)
3. Interface default methods vs abstract class? (Defaults cannot hold per-instance state.)
4. What breaks if `hashCode` uses a mutable field? (The object is lost in the map after mutation.)
5. How would you unit-test `LiveWorkshop` seat limits? (Enroll to capacity, assert exception, assert count unchanged.)

---

### Questions to park (framework)

Spring beans, Hibernate proxies, Jackson `JsonAutoDetect`, servlet vs REST — acknowledge, park, offer “office hours”. They are consumers of these core ideas, not replacements.
