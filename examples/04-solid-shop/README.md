# SOLID: refaktoryzacja systemu zamówień

Projekt do live codingu podczas wykładu 04. Jedna domena pokazuje kolejno SRP, OCP, LSP, ISP i DIP.

## Katalogi

- `start` — działający, celowo źle zaprojektowany kod oraz test regresyjny.
- `solution` — kompletne rozwiązanie po refaktoryzacji wraz z testami.

## Pakiety

| Pakiet | Odpowiedzialność |
|---|---|
| `domain` | Encje i obiekty wartościowe: zamówienie, klient, potwierdzenie |
| `application` | Orkiestracja przypadku użycia checkout |
| `pricing` | Rozszerzalne polityki cenowe i rabatowe |
| `ports` | Kontrakty infrastruktury wymagane przez aplikację |
| `shipping` | Kontrakty oraz implementacje sposobów dostawy |
| `devices` | Małe interfejsy ról demonstrujące ISP |

W projekcie `start` przy komentarzach `KROK 1`–`KROK 5` znajdują się zakomentowane propozycje rozwiązań. Można je kolejno odkomentowywać i uzupełniać podczas live codingu.

## Scenariusz live codingu (około 90 minut)

| Minuty | Etap |
|---:|---|
| 0–10 | Uruchomienie projektu, charakterystyka testem |
| 10–25 | SRP: wydzielenie obliczania ceny i efektów ubocznych |
| 25–40 | OCP: strategie rabatowe |
| 40–55 | LSP: naprawa modelu dostawy |
| 55–65 | ISP: rozdzielenie ról urządzenia |
| 65–80 | DIP: porty, adaptery i constructor injection |
| 80–90 | Test końcowy, checklista, pytania |

## Uruchomienie

Wymagane są Java 11+ i Maven 3.6+.

```bash
cd examples/04-solid-shop/start
mvn test

cd ../solution
mvn test
```

## Kolejność pracy

1. Uruchom test przed zmianą.
2. Przeczytaj komentarze `KROK 1`–`KROK 5` w `LegacyCheckoutService`.
3. Po każdej ekstrakcji uruchom `mvn test`.
4. Porównuj zachowanie, a nie liczbę klas, z katalogiem `solution`.
5. Na końcu wykonaj zadania z wykładu bez zaglądania do rozwiązania.
