# 08 — Bounded Context

Wykonywalny przykład dwóch modeli systemu konwentowego oraz warstwy antykorupcyjnej.

```text
Registration Context                Program Context
AttendeeRegistered  ── kontrakt ──> RegistrationEventTranslator ──> SpeakerCandidate
```

Uruchomienie: `mvn test`.

- `registration.api` — publiczny kontrakt kontekstu rejestracji,
- `program.domain` — lokalny model programu,
- `program.integration` — tłumaczenie obcego kontraktu na język programu.

`ticketType` należy do Rejestracji. Translator świadomie nie przenosi go do modelu Programu.

Ćwiczenie: dodaj kontekst Dostępu, zdarzenie `TicketActivated` i translator tworzący `BadgeHolderCandidate`.
