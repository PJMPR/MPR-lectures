# Wykład 05 — taktyczne building blocks DDD

Przykład modeluje fragment planowania programu konwentu. Kod pokazuje, jak przełożyć język i reguły domeny na mały model obiektowy bez Springa, JPA i bazy danych.

## Uruchomienie

Wymagane są Java 11+ i Maven 3.6+.

```bash
cd examples/05-ddd-building-blocks
mvn test
```

## Gdzie są building blocks?

| Building block | Klasy | Odpowiedzialność |
|---|---|---|
| Entity | `ProgramEvent` | Zachowuje tożsamość i pilnuje cyklu życia punktu programu. |
| Value Object | `ProgramEventId`, `EventTitle`, `RoomId`, `TimeSlot`, `Schedule`, `RoomRequirements` | Reprezentuje znaczącą wartość, waliduje ją przy tworzeniu i nie ma setterów. |
| Domain Service | `RoomSuitabilityPolicy` | Podejmuje decyzję wymagającą jednocześnie danych punktu i sali. |
| Repository | `ProgramEventRepository` | Udostępnia kolekcjopodobny interfejs do odtwarzania i zapisywania encji. |
| Application Service | `ScheduleProgramEventHandler` | Orkiestruje przypadek użycia: wczytaj, wywołaj model, zapisz, opublikuj zdarzenie. |
| Domain Event | `ProgramEventScheduled` | Opisuje istotny fakt, który już zaszedł. |

Diagram zależności znajduje się w pliku [class-diagram.md](class-diagram.md).

## Struktura pakietów

```text
pl.edu.pjatk.mpr.buildingblocks
├── application                 przypadki użycia i komendy
│   ├── ScheduleProgramEvent
│   └── ScheduleProgramEventHandler
├── domain
│   ├── event                   fakty domenowe
│   │   └── ProgramEventScheduled
│   ├── exception               błędy reguł domenowych
│   │   └── DomainRuleViolation
│   ├── model                   encje i Value Objects
│   │   ├── ProgramEvent
│   │   ├── ProgramEventId
│   │   ├── EventTitle
│   │   ├── Room, RoomId, RoomRequirements
│   │   └── Schedule, TimeSlot
│   └── service                 decyzje bez naturalnego właściciela
│       └── RoomSuitabilityPolicy
└── ports                       interfejsy prowadzące poza model
    ├── ProgramEventRepository
    └── DomainEventPublisher
```

Pakiety opisują rolę kodu, a nie użyty framework. Dzięki temu podczas prezentacji można najpierw omówić sam `domain.model`, potem decyzję przekrojową z `domain.service`, a dopiero na końcu orkiestrację w `application` i porty infrastruktury.

## Najważniejsze reguły modelu

- tytuł nie może być pusty,
- przedział czasu musi trwać co najmniej 15 minut,
- punkt można zaplanować dopiero po potwierdzeniu prowadzącego,
- pierwsze planowanie i przeplanowanie są osobnymi operacjami,
- publikacja wymaga zaplanowanego punktu,
- wycofanie nie usuwa encji ani jej tożsamości,
- dopasowanie sali uwzględnia pojemność, projektor i scenę.

## Sugerowana kolejność czytania

1. Zacznij od testów `TimeSlotTest` i `ProgramEventTest`.
2. Zobacz, jak Value Objects usuwają walidację z setterów i handlerów.
3. Prześledź przejścia stanu w `ProgramEvent`.
4. Porównaj zachowanie encji z `RoomSuitabilityPolicy`, która nie ma naturalnego właściciela.
5. Prześledź `ScheduleProgramEventHandlerTest`: handler nie podejmuje decyzji domenowych, tylko koordynuje porty.

## Pomysły na ćwiczenia

1. Dodaj powód wymagany przy przeplanowaniu opublikowanego punktu.
2. Zmień konflikt sali z błędu na ostrzeżenie szkicu i blokadę publikacji.
3. Dodaj `ProgramEventWithdrawn` oraz test publikacji tego zdarzenia.
4. Zaimplementuj repozytorium w pamięci bez zmiany modelu domenowego.
5. Zaproponuj agregat `ConventionSchedule`, który bezpiecznie sprawdza konflikty wielu punktów.

## Celowe uproszczenia

Model obejmuje jeden punkt programu. Nie rozwiązuje jeszcze transakcyjności publikacji całego harmonogramu ani równoległych zmian wielu punktów — te problemy prowadzą do granic agregatu omawianych w kolejnym wykładzie.
