# 07 — Architektura heksagonalna

Wykonywalny przykład przypadku użycia `ScheduleProgramEvent` w systemie konwentowym.

```text
adapters/in  →  application/ports/in  →  application  →  domain
adapters/out ←  application/ports/out  ←───────────────┘
```

Uruchomienie: `mvn test`.

- `domain` — model i reguła braku konfliktów sal,
- `application` — orkiestracja przypadku użycia,
- `ports.in` — możliwości systemu,
- `ports.out` — potrzeby systemu,
- `adapters.out` — wymienne implementacje in-memory.

Ćwiczenie: dodaj port wejściowy zmiany sali, zdarzenie `RoomChanged` i adapter REST lub JPA bez zmieniania domeny.
