# Facilitator Guide — Core Java (3 hours)

Use this as a **second-screen script**. Do not read it aloud. Speak from the slides; use these notes when you stall.

**Audience assumption:** people who have written some Java (or this LearnHub codebase) but have fuzzy OOP/collections mental models.

**Room setup (15 min before start)**
- Projector: `presentation/index.html` full screen
- IDE: labs project compiled; four run configs: `DemoOOP`, `DemoCollections`, `DemoGenerics`, `CatalogApp`
- Whiteboard + markers
- Chat/Slack thread for parking-lot questions
- Water, timer visible to you only

**Tone:** technical, not theatrical. Prefer one tight example over five cute ones. Pause after every demo and ask “what would break if…?”

---

## Block A — Conceptual overview (30 min)

| Min | Slide theme | You do | They do |
|-----|-------------|--------|---------|
| 0–3 | Title + objective | State the contract: *leave able to model catalog items and store them correctly* | Settle |
| 3–6 | Agenda + rules | “Interrupt for confusion, park for rabbit holes.” | — |
| 6–12 | Why OOP exists | Draw: data + behavior together vs scattered if/else on `type` strings (this repo’s `Course.type` is a live hook) | — |
| 12–18 | Four pillars map | Map each pillar to LearnHub: Video / Live / Quiz | Shout examples |
| 18–24 | Collections as data structures | “OOP models *things*. Collections model *groups of things*.” List vs Set vs Map in one sentence each | Pick structure for: catalog order, unique tags, id lookup |
| 24–28 | Generics as a compiler contract | Raw `List` vs `List<Course>` — bugs move from runtime to compile time | — |
| 28–30 | Bridge to labs | “Next two hours we *build* the catalog, we don’t just talk about it.” | Open workbook |

### Talking points (Block A)

**Classes vs objects:** A class is the blueprint (`Instructor`). An object is a specific Ada Lovelace in heap memory. Two objects can share a class and still have different state.

**Inheritance:** `VideoCourse extends Content` is **is-a**. Use it for shared identity and template behavior. Do not inherit just to reuse a helper method — that is the wrong tool.

**Polymorphism:** `List<Content>` holding videos, workshops, quizzes. Call `catalogLine()` / `enroll()` without `instanceof` soup. Runtime picks the override.

**Abstraction:** Hide *how*, expose *what*. Abstract class = shared implementation + holes to fill. Interface = capability (`Enrollable`, `Priced`). Assessment is enrollable but not priced — that is the teaching moment.

**Interfaces vs abstract classes:** Interface = can-do (multiple). Abstract class = is-a family (single). After Java 8, interfaces can have default methods; still prefer abstract class when you have shared *state* (`id`, `title`, enrollment count).

**Collections:**
- List — sequence, index, duplicates OK (catalog display order)
- Set — uniqueness (tags, enrolled emails)
- Map — key → value (id → course, category → bucket)

**Iterators:** The only safe way to remove while walking a collection. For-each uses an iterator under the hood; calling `list.remove` inside for-each throws `ConcurrentModificationException`.

**Generics:** `Repository<T>` is one implementation, many types. PECS: producer extends, consumer super. Mention wildcards; do not drown in `? super T` unless asked.

**LearnHub repo hook (use once, it builds credibility):** `Course` in this project is a JPA entity with String fields including `type`. In production that often becomes a switch on type. Today we replace that with a type hierarchy + collections service, which is the direction you want before the if/else tree grows.

---

## Block B — Hands-on (120 min)

Keep a visible mini-agenda on the whiteboard:

```
0:30–0:50  Classes & objects + inheritance     (20)
0:50–1:10  Polymorphism + abstraction          (20)
1:10–1:30  Interfaces + equals/hashCode        (20)
1:30–1:40  BREAK                               (10)
1:40–2:10  List / Set / Map + iterators        (30)
2:10–2:25  Generics + PECS                     (15)
2:25–2:30  Capstone CatalogApp                 (5)
```

If you run late, **cut PECS depth**, not the iterator demo and not the capstone.

### 0:30–0:50 Classes, objects, inheritance (20)

**Demo:** walk `Instructor`, `Content`, `VideoCourse`.

**Say:**
- Fields private, constructor validates, getters — encapsulation is not “I wrote get/set”.
- `final` fields where mutation is not part of the story.
- `super(...)` must be first; subclasses add *difference*, not copies of parent fields.

**Live mutation (optional, 3 min):** add `String level` to `Content`. Show every subclass compiles without touching them. That is the point of a base type.

**Exercise 1** from workbook (5 min pair). Debrief one pair only.

### 0:50–1:10 Polymorphism + abstraction (20)

**Demo:** `DemoOOP` sections 2 and 4. Loop `List<Content>` and print `catalogLine()`.

**Whiteboard:** compile-time type vs runtime type.

```
Content item = new LiveWorkshop(...);
item.catalogLine();   // compile: Content
                      // runtime: LiveWorkshop.formatLabel()
```

**Template method:** `catalogLine()` is `final` on the parent; `formatLabel()` is abstract. Name this pattern out loud.

**Trap to show:** if you forget `@Override` and misspell `formatLabel`, you silently get a new method. Always `@Override`.

### 1:10–1:30 Interfaces + equals (20)

**Demo:** `Enrollable` / `Priced`. Workshop overrides `enroll` to enforce seat limit. Third enroll fails.

**Say:** interfaces let you mix capabilities. `Assessment` is `Content` + `Enrollable` but not `Priced`. That is better than a `price = 0` magic value.

**equals/hashCode:** two courses with the same id are the same catalog row. If you put `Content` in a `HashSet` without `equals`/`hashCode`, duplicates appear. Point at the implementations.

**Exercise 2** (5 min).

### 1:30–1:40 Break

Tell them the next hour is collections. Ask them to skim the workbook table (List/Set/Map).

### 1:40–2:10 Collections + iterators (30)

**Demo:** `DemoCollections` top to bottom.

**Cadence:**
1. List = ordered catalog (ArrayList default)
2. Set uniqueness: HashSet vs LinkedHashSet vs TreeSet on tags `java, oop, java, collections, oop`
3. Map: `findById` and `byCategory`
4. Iterator: `dropUnenrolled` — then **break it** by commenting iterator and using for-each + remove. Catch `ConcurrentModificationException`. This is the session’s most memorable moment. Restore the iterator.

**When to use which List:** ArrayList (random access, append). LinkedList almost never in application code. Mention Vector is legacy.

**When to use which Map:** HashMap default. LinkedHashMap for stable iteration. TreeMap for sorted keys. ConcurrentHashMap only if you say the word “threads”.

**Exercise 3** (8 min). Walk one solution.

### 2:10–2:25 Generics (15)

**Demo:** `DemoGenerics`. Let the ClassCastException happen. Pause. Then show `Repository<VideoCourse>`.

**PECS in one breath:** if the method only *reads* `T`, `<? extends T>`. If it only *writes* `T`, `<? super T>`. If it does both, use `T` exactly.

**Do not** spend time on type erasure internals unless asked (answer is in Q_AND_A).

**Exercise 4** if time; otherwise assign as homework.

### 2:25–2:30 Capstone (5)

Run `CatalogApp`. Trace: faculty repository → catalog service → polymorphism in the loop → instanceof only at the *boundary* (pricing). Say: “instanceof in a tight domain loop is a smell; at an anti-corruption / reporting boundary it is OK.”

---

## Block C — Q&A (30 min)

Structure:
1. Recap 5 bullets (2 min) — see last slides
2. Open floor (20 min) — use [Q_AND_A.md](Q_AND_A.md)
3. Homework + how this maps to LearnHub `Course` (5 min)
4. Close (3 min)

If the room is silent, plant:
- “Why not make `price` a field on `Content` and use 0 for free?”
- “Why is `Course.price` a String in our app?”
- “HashMap vs Hashtable?”
- “Can an abstract class have a constructor?”

Parking lot: dump unanswered items into chat with owners.

---

## Failure modes (and how you recover)

| What happens | Recover |
|--------------|---------|
| Compile error in live edit | Revert to committed lab; explain the error; do not debug for 8 minutes |
| Too many Spring/Hibernate questions | “Those are frameworks *on top* of this. Park until last 10 minutes.” |
| Advanced attendees bored | Give them Exercise 5 (wildcard + custom Comparator) |
| Beginners lost | Pair them; you stay on the happy path demo |
| Time overrun | Skip PECS details; still run capstone and iterator CME |

## After the session (you)

- Post slides HTML + cheatsheet + homework
- Post `run-labs.sh` output as “expected console”
- Offer 15-min office hours the next day for Exercise 4–5
