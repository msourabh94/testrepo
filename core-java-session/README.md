# Core Java Session Pack

End-to-end materials for a **3-hour** technical session:

1. Object-Oriented Programming (classes, objects, inheritance, polymorphism, abstraction, interfaces)
2. Collections & Generics (List, Set, Map, iterators, generic types and practices)

Domain for every example: a **LearnHub catalog** (video courses, live workshops, assessments) — the same mental model as this repository’s `Course` entity and service layer.

## What to open tomorrow

| File | Who | Purpose |
|------|-----|---------|
| [presentation/index.html](presentation/index.html) | Facilitator (projector) | Full slide deck — open in a browser |
| [FACILITATOR_GUIDE.md](FACILITATOR_GUIDE.md) | You | Minute-by-minute script, talking points, pitfalls |
| [PARTICIPANT_WORKBOOK.md](PARTICIPANT_WORKBOOK.md) | Audience | Exercises to do live |
| [Q_AND_A.md](Q_AND_A.md) | You | Likely questions with crisp answers |
| [CHEATSHEET.md](CHEATSHEET.md) | Audience (handout) | One-page takeaway |
| [EXERCISE_SOLUTIONS.md](EXERCISE_SOLUTIONS.md) | You only | Do not project until pairs have tried |
| [labs/](labs/) | Everyone | Runnable demos |

## How to present

1. Open `presentation/index.html` in Chrome or Edge (full screen: `F11`).
2. Keys: `←` `→` or `Space` next slide, `Home`/`End`, `N` speaker notes, `P` print/PDF.
3. Keep the facilitator guide on a second screen or printed.
4. Compile labs **before** the room fills:

```bash
cd core-java-session/labs
chmod +x run-labs.sh
./run-labs.sh
```

Requires JDK 8+. Demos use Java 7-style syntax (`<>` diamonds, no records/streams required) so they also teach concepts that map to older codebases (this repo targets Java 6/Spring 3).

## Timebox (do not slip)

| Block | Clock | Focus |
|-------|--------|--------|
| Concepts | 0:00–0:30 | Why OOP + collections exist; mental models; agenda |
| Hands-on | 0:30–2:30 | Live coding against the labs |
| Q&A | 2:30–3:00 | Discussion, recap, homework |

## Success criteria for the session

- Every attendee can explain **is-a vs has-a vs can-do** with a catalog example.
- Every attendee can pick **List vs Set vs Map** for a real LearnHub use case.
- Every attendee has seen a **ConcurrentModificationException** (or the iterator fix) and a **raw-type ClassCastException**.
- Capstone (`CatalogApp`) is run once so the pieces feel like one system.
