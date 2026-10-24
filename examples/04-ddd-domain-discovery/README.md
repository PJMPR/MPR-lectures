# Wykład 04 — odkrywanie domeny konwentu

Projekt pokazuje przejście od technicznego API do języka planowania programu konwentu science-fiction.

- `start` — punkt startowy i sześć testów odblokowywanych kolejno,
- `solution` — przykładowy rezultat implementacji,
- `domain-brief.md` — opis domeny oraz hot spoty.

```bash
cd start
mvn test
cd ../solution
mvn test
```

## Testy krok po kroku

`SchedulingServiceIncrementalTest` prowadzi przez:

1. odrzucenie punktu bez sali,
2. zdarzenie `ProgramEventScheduled`,
3. konflikt sali,
4. konflikt prowadzącego,
5. stabilność komendy i defensywną kopię wymagań,
6. unikalną tożsamość punktu programu.

Usuń lub zakomentuj `@Disabled` tylko przy jednym teście, uruchom `mvn test`, zaimplementuj najmniejszą zmianę i dopiero przejdź dalej. Pierwszy test jest już odblokowany, zgodnie ze stanem rozpoczętego ćwiczenia.

Projekt celowo nie zawiera bazy, REST, Springa ani JPA. Pełne modelowanie encji, Value Objects i agregatów jest tematem kolejnych wykładów.
