# TDD: sterownik windy

Projekt do 60-minutowego live codingu podczas wykładu 03. Pokazuje krótkie cykle Red–Green–Refactor, asercje Hamcrest i izolowanie urządzeń za pomocą Mockito.

## Katalogi

- `start` — interfejsy, pusta implementacja i wyłączone testy z dokładnymi wskazówkami.
- `solution` — kompletne rozwiązanie awaryjne wraz z działającymi testami.

## Prowadzenie demonstracji

1. Otwórz `start` jako projekt Maven i uruchom `mvn test`.
2. W `ElevatorControllerTest` usuń `@Disabled` tylko z testu oznaczonego `KROK 1`.
3. Uruchom test i upewnij się, że jest czerwony z oczekiwanego powodu.
4. Dopisz najmniejszą implementację wystarczającą do uzyskania zielonego testu.
5. Przy zielonym zestawie uporządkuj kod.
6. Włącz dokładnie jeden kolejny test i powtórz cykl.

Katalog `solution` jest punktem ratunkowym oraz materiałem do porównania po zajęciach.

## Plan 60 minut

| Minuty | Iteracja |
|---:|---|
| 0–5 | Start projektu i lista zachowań |
| 5–13 | Cel jest bieżącym piętrem |
| 13–23 | Jazda w górę i kolejność urządzeń |
| 23–30 | Jazda w dół |
| 30–40 | Niezamknięte drzwi |
| 40–49 | Przeciążenie |
| 49–56 | Kolejka FIFO |
| 56–60 | Refaktoryzacja i podsumowanie |

## Wymagania i uruchomienie rozwiązania

Java 11+, Maven 3.6+.

```bash
cd examples/03-tdd-elevator/solution
mvn test
```
