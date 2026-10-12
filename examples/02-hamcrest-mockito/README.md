# Hamcrest + Mockito: projekt przykładowy

Mały system składania zamówień używany podczas wykładu 02. Kod produkcyjny ma trzy zależności na granicy systemu: repozytorium, bramkę płatności i usługę powiadomień. Testy pokazują, jak opisywać wynik za pomocą Hamcrest i jak izolować zależności za pomocą Mockito.

## Wymagania

- Java 11 lub nowsza
- Maven 3.6 lub nowszy

## Uruchomienie

```bash
cd examples/02-hamcrest-mockito
mvn test
```

## Najważniejsze pliki

- `OrderTest` pokazuje matchery wartości, obiektów i kolekcji.
- `OrderServiceTest` pokazuje `@Mock`, `@InjectMocks`, stubbing, `verify`, `ArgumentCaptor` i `InOrder`.
- `PaidOrderMatcher` jest własnym matcherem Hamcrest z komunikatem o niedopasowaniu.
- `SpyExampleTest` pokazuje zachowanie obiektu częściowo prawdziwego.

## Zadania dla studentów

1. Dodaj rabat i sprawdź kwotę płatności matcherem `comparesEqualTo`.
2. Dodaj walidację adresu e-mail. Sprawdź, że przy błędzie żadna zależność nie została wywołana.
3. Dodaj `InventoryService`. Rezerwacja ma nastąpić przed płatnością, a przy odrzuconej płatności stan magazynu ma zostać zwolniony.
4. Napisz własny matcher `orderWithTotal(...)` z czytelnym opisem błędu.

## Wersje bibliotek

- JUnit Jupiter 5.14.0
- Hamcrest 3.0
- Mockito 5.24.0

Wersje są zapisane we właściwościach w `pom.xml`, więc można je zaktualizować w jednym miejscu.
