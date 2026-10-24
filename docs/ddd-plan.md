# DDD — plan wykładów i warsztatów

Pięć wykładów i pięć warsztatów rozwija system obsługi konwentu science-fiction: tworzenie programu, przydzielanie sal i stoisk, publikację harmonogramu oraz osobiste plany uczestników.

## Wykład 4: Odkrywanie domeny konwentu

Tematy: domena i model, Ubiquitous Language, eksperci, Event Storming, hot spoty i pierwsze granice.

Warsztat: proces od zgłoszenia punktu programu do publikacji harmonogramu; aktorzy, komendy, zdarzenia i polityki; rozróżnienie konwentu, punktu programu i zdarzenia domenowego; słownik oraz pierwsza implementacja planowania punktu programu.

Rezultat: model procesu i przykłady wykonawcze bez projektowania bazy.

## Wykład 5: Encje i Value Objects programu

Tematy: Entity, Value Object, tożsamość, niemutowalność, niezmienniki, Rich Domain Model i testowanie modelu.

Warsztat: `ProgramEvent`, `ProgramEventId`, `RoomId`, `SpeakerId`, `TimeSlot`; panel, prelekcja, warsztat i turniej; wymagania techniczne, pojemność sali oraz konflikty czasu.

Rezultat: czysty model programu niezależny od Springa i bazy danych.

## Wykład 6: Agregaty i repozytoria harmonogramu

Tematy: Aggregate, Aggregate Root, granice spójności, transakcje, repozytoria i Domain Service.

Warsztat: granice agregatu harmonogramu; konflikty sali i prowadzącego; port `ConventionScheduleRepository`; repozytorium in-memory; analiza, dlaczego cały konwent nie powinien być jednym agregatem.

Rezultat: model chroniący reguły planowania bez ogromnego agregatu.

## Wykład 7: Architektura heksagonalna systemu konwentowego

Tematy: domena, warstwa aplikacyjna, porty, adaptery, kierunek zależności i testowanie wnętrza heksagonu.

Warsztat: `ScheduleProgramEvent`; porty repozytorium, powiadomień i publikacji; adapter in-memory, REST i opcjonalnie JPA; powiadomienie uczestników po zmianie sali.

Rezultat: przypadki użycia oddzielone od infrastruktury.

## Wykład 8: Strategiczne DDD konwentu

Tematy: subdomeny, Bounded Contexts, Context Map, relacje, Anti-Corruption Layer, Domain Events i eventual consistency.

Warsztat: podział na `Program Management`, `Scheduling`, `Attendee Experience`, `Exhibitor Management` i `Venue Operations`; właściciele danych; zdarzenia `SchedulePublished`, `RoomChanged` i `ProgramEventCancelled`; reakcje osobistego planu uczestnika.

Rezultat: model granic systemu i przepływ informacji między kontekstami.

## Organizacja spotkań

1. Wykład przedstawia problem i narzędzia.
2. Prowadzący prezentuje przykład z domeny konwentu.
3. Warsztat rozpoczyna się od przygotowanego kodu startowego.
4. Studenci rozwijają ten sam system i podejmują decyzje projektowe.
5. Spotkanie kończy się code review oraz porównaniem modeli.

SOLID nie stanowi osobnego wykładu. Zasady pojawiają się jako uzasadnienie decyzji podczas modelowania i budowy architektury heksagonalnej.
