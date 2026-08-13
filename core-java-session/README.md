# Core Java Session Pack — Banking Domain

End-to-end materials for a **3-hour** technical session:

1. Object-Oriented Programming (classes, objects, inheritance, polymorphism, abstraction, interfaces)
2. Collections & Generics (List, Set, Map, iterators, generic types and practices)

Every example uses a **mini banking core**: customers, savings, checking (with overdraft), prepaid wallets, branch registry.

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

Requires JDK 8+. Demos use Java 7-style syntax so they map to older enterprise codebases too.

## Timebox (do not slip)

| Block | Clock | Focus |
|-------|--------|--------|
| Concepts | 0:00–0:30 | Why OOP + collections exist; mental models |
| Hands-on | 0:30–2:30 | Live coding against the banking labs |
| Q&A | 2:30–3:00 | Discussion, recap, homework |

## Success criteria for the session

- Every attendee can explain **is-a vs has-a vs can-do** with an account example.
- Every attendee can pick **List vs Set vs Map** for branch lookup, unique customers, and statement order.
- Every attendee has seen **ConcurrentModificationException** (or the iterator fix) and a **raw-type ClassCastException**.
- Capstone (`BankingApp`) runs once so the pieces feel like one system.
