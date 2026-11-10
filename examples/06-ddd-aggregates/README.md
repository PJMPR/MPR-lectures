# 06 — DDD Aggregates: Convention Schedule

Przykład pokazuje `DaySchedule` jako Aggregate Root. Chroni brak konfliktów sal, ukrywa `AgendaEntry`, publikuje zdarzenie i jest zapisywany przez repozytorium korzenia.

Uruchom: `mvn test`.

- `DaySchedule` — root i granica transakcji dnia,
- `AgendaEntry` — encja wewnętrzna bez repozytorium,
- `TimeSlot` — Value Object,
- `DayScheduleRepository` — port repozytorium root,
- `SchedulePublished` — fakt używany poza agregatem.

Ćwiczenie: dodaj przeplanowanie oraz repozytorium in-memory z kontrolą wersji.
