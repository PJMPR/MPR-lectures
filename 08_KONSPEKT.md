# Wykład 08 — Bounded Context

## Informacje organizacyjne

**Temat:** Bounded Context w systemie konwentowym  
**Główny przykład:** system organizacji konwentu rozwijany od wykładu 04  
**Materiał na stronie:** `site/lectures/08-ddd-strategic-design.html`  
**Kod:** `examples/08-bounded-context`  
**Orientacyjny czas:** 75–90 minut  
**Liczba slajdów w trybie prezentacji:** 21

### Główna myśl wykładu

Bounded Context nie jest przede wszystkim podziałem technicznym. Jest granicą, wewnątrz której model i język mają jedno, precyzyjne znaczenie. Ta sama osoba może być `Attendee`, `Speaker` oraz `BadgeHolder`, ponieważ każdy z tych modeli odpowiada na inne pytania i chroni inne reguły.

### Co studenci powinni już znać

- Ubiquitous Language i Event Storming z wykładu 04,
- encje, Value Objects i agregaty z wykładów 05–06,
- porty, adaptery i kierunek zależności z wykładu 07.

### Przygotowanie przed zajęciami

1. Otwórz wykład 08 w przeglądarce.
2. Otwórz projekt `examples/08-bounded-context` w IDE.
3. Uruchom wcześniej `mvn test` w katalogu przykładu.
4. Przygotuj widok czterech pakietów:
   - `registration.api`,
   - `program.domain`,
   - `program.integration`,
   - test `RegistrationEventTranslatorTest`.
5. Nie zaczynaj od definicji. Najpierw pokaż problem z jednym modelem `Participant`.

---

## Slajd 1 — Bounded Context w systemie konwentowym

**Cel:** ustawić temat i połączyć go z wcześniejszymi wykładami.

### Co powiedzieć

Na poprzednich wykładach skupialiśmy się na wnętrzu jednego modelu: encjach, Value Objects, agregatach i przypadkach użycia. Teraz system konwentowy rośnie. Oprócz programu obsługuje sprzedaż biletów, kontrolę dostępu i rozliczenia. Pojawia się pytanie, czy cały system powinien używać jednego wspólnego modelu.

Bounded Context odpowiada na inne pytanie niż agregat:

- agregat wyznacza granicę spójności i transakcji wewnątrz modelu,
- Bounded Context wyznacza granicę znaczenia całego modelu i języka.

Zapowiedz, że podczas wykładu nie będziemy szukać idealnego podziału na mikroserwisy. Będziemy szukać miejsc, w których słowa i reguły zmieniają znaczenie.

**Przejście:** „Zacznijmy od sytuacji, w której jedno pozornie oczywiste słowo przestaje być oczywiste”.

---

## Slajd 2 — Plan spotkania

**Cel:** pokazać drogę od problemu do implementacji.

### Co powiedzieć

Krótko omów plan. Najpierw zobaczymy problem jednego modelu. Następnie zdefiniujemy Bounded Context, nauczymy się rozpoznawać granice i narysujemy Context Map. Na końcu przejdziemy do kodu oraz testów warstwy antykorupcyjnej.

Podkreśl dwie części wykładu:

- część strategiczna: gdzie przebiegają granice i kto jest właścicielem modelu,
- część implementacyjna: jak nie dopuścić, by model jednego kontekstu wyciekł do drugiego.

Nie omawiaj jeszcze szczegółowo nazw wzorców relacji. Wrócą później.

---

## Slajd 3 — Cele wykładu

**Cel:** określić oczekiwane umiejętności.

### Co powiedzieć

Po wykładzie student powinien umieć:

- zauważyć, że jedno słowo ma kilka biznesowych znaczeń,
- nazwać granicę, w której dane znaczenie obowiązuje,
- narysować mapę zależności pomiędzy kontekstami,
- świadomie dobrać sposób współpracy,
- napisać tłumaczenie chroniące lokalny model.

Zaznacz, że samo narysowanie kilku prostokątów nie wystarcza. Granica ma sens dopiero wtedy, gdy potrafimy powiedzieć, jakie decyzje znajdują się po każdej stronie.

---

## Slajd 4 — Cold open: kim jest uczestnik?

**Cel:** zbudować problem przed podaniem definicji.

### Co powiedzieć

Przeczytaj dialog rolami lub poproś cztery osoby o przeczytanie wypowiedzi. Następnie zatrzymaj się przy zdaniu „chcę jeden rekord uczestnika”. To żądanie brzmi rozsądnie na poziomie bazy danych, ale jest niebezpieczne na poziomie modelu.

Omów perspektywy:

- sprzedaż pyta, czy osoba kupiła i opłaciła bilet,
- program pyta, czy osoba prowadzi wydarzenie oraz jakie ma wymagania,
- dostęp pyta, do których stref można ją wpuścić,
- księgowość pyta, jakie dokumenty i płatności są z nią związane.

### Pytanie do studentów

„Czy anulowanie biletu powinno automatycznie usunąć prelegenta z programu?”

Nie szukaj jednej odpowiedzi. Chodzi o ujawnienie, że decyzja zależy od reguł różnych obszarów. Prelegent może mieć osobną akredytację, więc status biletu uczestnika nie musi decydować o statusie wydarzenia.

**Przejście:** „Zobaczmy, co dzieje się z kodem, kiedy próbujemy zmieścić te znaczenia w jednym typie”.

---

## Slajd 5 — Jeden wspólny `Participant`

**Cel:** pokazać symptom pomieszania modeli.

### Co powiedzieć

Omów klasę `Participant`. Nie krytykuj jej tylko dlatego, że ma wiele pól. Duży obiekt może być poprawny, jeśli pola należą do jednego spójnego modelu. Tutaj problemem jest liczba niezależnych powodów do zmiany.

Przejdź po polach:

- `Ticket` zmienia się przez politykę sprzedaży,
- `ProgramEvent` zmienia się przez decyzje redakcji programu,
- `Workshop` ma własne limity miejsc i rezerwacje,
- `Invoice` podlega zasadom rozliczeń,
- `Badge` i uprawnienia wynikają z reguł bezpieczeństwa.

Wyjaśnij konsekwencje:

- każde pole staje się dostępne dla całego systemu,
- reguły zaczynają wywoływać się przypadkowo,
- jedna migracja danych dotyka wielu funkcji,
- testy nie wiedzą, które znaczenie słowa „aktywny” sprawdzają.

Powiedz wprost: Bounded Context nie rozwiązuje problemu przez podział klasy na mniejsze klasy pomocnicze. Rozdziela modele i ich odpowiedzialności.

---

## Slajd 6 — Bounded Context: trzy modele

**Cel:** podać definicję i od razu zakotwiczyć ją w przykładzie.

### Co powiedzieć

Podaj definicję:

> Bounded Context to jawna granica, wewnątrz której konkretny model oraz Ubiquitous Language zachowują spójne znaczenie.

Omów trzy prostokąty:

#### Rejestracja

Model `Attendee` interesuje się biletem, zamówieniem i płatnością. Odpowiada na pytanie, czy osoba ma ważne prawo wejścia wynikające z zakupu.

#### Program

Model `Speaker` interesuje się profilem prelegenta, prowadzonymi wydarzeniami i wymaganiami organizacyjnymi. Odpowiada na pytanie, kto prowadzi dany punkt programu.

#### Dostęp

Model `BadgeHolder` interesuje się identyfikatorem i strefami. Odpowiada na pytanie, gdzie dana osoba może wejść w tej chwili.

Podkreśl, że wszystkie modele mogą opisywać tę samą osobę w świecie rzeczywistym. Nie oznacza to jednak, że powinny używać tej samej encji.

### Pytanie do studentów

„Który kontekst powinien przechowywać biografię prelegenta?”

Oczekiwana odpowiedź: Program, ponieważ biografia jest częścią prezentacji programu, a nie sprzedaży biletu.

---

## Slajd 7 — Ta sama osoba, trzy reprezentacje

**Cel:** doprecyzować notatkę do diagramu.

### Co powiedzieć

Wyjaśnij różnicę między tożsamością biznesową a reprezentacją w modelu. Konteksty mogą wymieniać identyfikator zewnętrzny, ale każdy tworzy własny lokalny obiekt.

Nie kopiujemy całej encji pomiędzy kontekstami. Przesyłamy informację potrzebną do współpracy, na przykład:

- identyfikator osoby,
- nazwę wyświetlaną,
- fakt zarejestrowania.

Program nie potrzebuje numeru faktury ani typu biletu, aby utworzyć kandydata na prelegenta. Dostęp nie potrzebuje biografii prelegenta, aby aktywować strefę.

Powiedz, że granica jest także granicą wiedzy. Każdy kontekst powinien wiedzieć tylko tyle o innych obszarach, ile wymaga jego własna decyzja.

---

## Slajd 8 — Granice odkrywamy w języku i zmianach

**Cel:** dać praktyczne heurystyki odkrywania kontekstów.

### Co powiedzieć

Przejdź po czterech sygnałach.

#### Konflikt znaczeń

Jeżeli eksperci używają tego samego słowa inaczej, nie wymuszaj jednej definicji dla całej firmy. „Aktywny uczestnik” dla sprzedaży oznacza opłacony bilet, a dla dostępu może oznaczać niezablokowany identyfikator.

#### Inne reguły

Sprawdzenie ważności biletu wymaga innych danych niż wykrycie konfliktu sal. Jeżeli decyzje operują na innych informacjach i niezmiennikach, prawdopodobnie należą do innych modeli.

#### Inne tempo zmian

Cennik biletów zmienia się z powodów marketingowych. Układ programu zmienia się przez dostępność sal i prowadzących. Wspólny model powoduje, że niezależne zmiany blokują się wzajemnie.

#### Inna odpowiedzialność

Granice często pokrywają się z odpowiedzialnością zespołów i ekspertów, lecz nie należy mechanicznie kopiować struktury organizacyjnej. Najpierw analizujemy decyzje i język.

### Minićwiczenie

Poproś studentów, aby dla kontekstu „Catering” podali:

- główne pytanie biznesowe,
- dwa lokalne pojęcia,
- zdarzenie, które może otrzymać od Rejestracji.

Przykład: liczba posiłków, `DietaryRequirement`, `MealVoucher`, zdarzenie `AttendanceConfirmed`.

---

## Slajd 9 — Język jest lokalny

**Cel:** pokazać, że Ubiquitous Language nie musi być globalny.

### Co powiedzieć

Przejdź wierszami tabeli. Szczególnie zatrzymaj się przy słowie „aktywny”. Wspólna nazwa nie gwarantuje wspólnego znaczenia.

Wyjaśnij, że lokalny język zmniejsza liczbę warunków:

- zamiast `participant.isActive()` każdy kontekst ma operację odpowiadającą własnej decyzji,
- Program może pytać `speaker.isPublishedInProgram()`,
- Rejestracja może pytać `ticket.isValidAt(date)`,
- Dostęp może pytać `badge.canEnter(zone)`.

To nie jest tylko zmiana nazw. Każda operacja korzysta z innych danych i pilnuje innych reguł.

### Pytanie do studentów

„Czy powinniśmy stworzyć centralny słownik wszystkich pojęć w firmie?”

Odpowiedź: warto dokumentować powiązania i różnice, ale nie należy wymuszać jednego modelu. Context Map może pokazać, jak lokalne języki tłumaczą się na siebie.

---

## Slajd 10 — Lokalny model `Speaker`

**Cel:** przejść od języka do struktury kodu.

### Co powiedzieć

Omów fragment `Speaker`. Model Programu przechowuje tylko informacje potrzebne do decyzji o programie. Brak `Ticket`, `Invoice` i `Badge` jest celowy.

Zwróć uwagę na `SpeakerId`. Lokalny identyfikator może być inny niż identyfikator osoby w Rejestracji. Jeśli Program musi zapamiętać powiązanie, może przechować osobny `ExternalPersonId`.

Wyjaśnij korzyści:

- reguły Programu nie mogą przypadkowo zmienić biletu,
- model można testować bez systemu sprzedaży,
- zmiana formatu biletu nie wymusza przebudowy `Speaker`,
- nazwy metod pozostają językiem zespołu programu.

Nie otwieraj jeszcze kodu przykładu. Najpierw pokaż mapę współpracy.

---

## Slajd 11 — Context Map

**Cel:** pokazać relacje i kierunek zależności.

### Co powiedzieć

Wyjaśnij, że Context Map nie jest diagramem klas ani architekturą wdrożenia. Pokazuje:

- jakie konteksty istnieją,
- który kontekst jest źródłem danego kontraktu,
- kto zależy od czyjego modelu lub API,
- jak zespoły uzgodniły współpracę.

Omów mapę:

- Rejestracja publikuje `AttendeeRegistered`, `TicketActivated` oraz `PaymentCaptured`,
- Program tłumaczy zdarzenie przez ACL,
- Dostęp może negocjować kontrakt z Rejestracją,
- Rozliczenia mogą świadomie zaakceptować format Rejestracji.

Wyjaśnij pojęcia:

- **upstream** jest właścicielem informacji lub kontraktu,
- **downstream** korzysta z tej informacji i odczuwa zmiany upstreamu.

Nie utożsamiaj upstreamu z „ważniejszym systemem”. To opis kierunku zależności.

---

## Slajd 12 — Jak czytać strzałki na mapie

**Cel:** uniknąć typowego nieporozumienia dotyczącego kierunku zależności.

### Co powiedzieć

Strzałka nie musi pokazywać fizycznego przepływu każdego wywołania sieciowego. Ma pokazać, kto jest zależny od czyjego kontraktu i znaczenia.

Przykład:

- Rejestracja jest właścicielem faktu `AttendeeRegistered`,
- Program odbiera ten fakt,
- Program jest downstreamem, ale nie musi przyjmować całego modelu Rejestracji,
- ACL ogranicza zależność do małego adaptera.

Zwróć uwagę, że komunikacja asynchroniczna nie usuwa zależności semantycznej. Konsument nadal musi rozumieć format i znaczenie wiadomości.

### Pytanie do studentów

„Czy użycie Kafki automatycznie rozdziela konteksty?”

Odpowiedź: nie. Można przesyłać całą encję i stworzyć silne sprzężenie pomimo komunikacji asynchronicznej.

---

## Slajd 13 — Wzorce relacji między kontekstami

**Cel:** przedstawić katalog relacji jako decyzje organizacyjne i modelowe.

### Co powiedzieć

Omów każdy wzorzec krótko, zawsze z przykładem.

#### Partnership

Dwa zespoły wspólnie planują zmianę. Dobre, gdy funkcja nie ma wartości bez jednoczesnej pracy obu stron. Kosztem jest koordynacja wydań.

#### Customer / Supplier

Downstream jest klientem i może zgłaszać potrzeby do roadmapy upstreamu. Dostęp może wymagać od Rejestracji stabilnego zdarzenia aktywacji biletu.

#### Conformist

Downstream przyjmuje model dostawcy bez tłumaczenia. To świadomy kompromis, gdy koszt ochrony jest większy niż ryzyko. Nie przedstawiaj go jako zawsze błędnego.

#### Anti-Corruption Layer

Downstream tłumaczy obcy kontrakt na własne pojęcia. Używamy go, gdy lokalny model jest ważny i powinien pozostać niezależny.

#### Open Host Service

Upstream udostępnia stabilny protokół dla wielu odbiorców, na przykład publiczne API rejestracji.

#### Published Language

Strony integracji korzystają z opisanego formatu, na przykład wersjonowanego schematu zdarzenia.

Podkreśl, że wzorce można łączyć. Open Host Service często używa Published Language.

---

## Slajd 14 — Anti-Corruption Layer w kodzie

**Cel:** pokazać konkretne tłumaczenie pomiędzy językami.

### Co powiedzieć

Najpierw omów publiczny kontrakt Rejestracji: `AttendeeRegistered`. Z punktu widzenia Rejestracji wszystkie trzy pola są uzasadnione.

Następnie omów `RegistrationEventTranslator`. Translator tworzy pojęcia Programu:

- `attendeeId` staje się `ExternalPersonId`,
- `displayName` staje się `DisplayName`,
- `ticketType` nie przechodzi przez granicę modelu.

To pominięcie jest najważniejszą częścią przykładu. ACL nie jest mechanicznym kopiowaniem pól. Podejmuje świadomą decyzję, które informacje mają znaczenie w lokalnym modelu.

### Zajrzyj do kodu

Otwórz kolejno:

1. `examples/08-bounded-context/src/main/java/pl/edu/pjatk/mpr/convention/registration/api/AttendeeRegistered.java`
2. `examples/08-bounded-context/src/main/java/pl/edu/pjatk/mpr/convention/program/integration/RegistrationEventTranslator.java`
3. `examples/08-bounded-context/src/main/java/pl/edu/pjatk/mpr/convention/program/domain/ExternalPersonId.java`
4. `examples/08-bounded-context/src/main/java/pl/edu/pjatk/mpr/convention/program/domain/DisplayName.java`
5. `examples/08-bounded-context/src/main/java/pl/edu/pjatk/mpr/convention/program/domain/SpeakerCandidate.java`

### Demonstracja

W IDE pokaż, że pakiet `program.domain` nie importuje niczego z `registration`. Zależność od kontraktu zewnętrznego znajduje się wyłącznie w `program.integration`.

Zapytaj: „Co musielibyśmy zmienić, gdyby Rejestracja zastąpiła `displayName` polami `firstName` i `lastName`?”

Odpowiedź: translator oraz ewentualnie test kontraktowy. Model `SpeakerCandidate` nie musi się zmienić.

---

## Slajd 15 — Sposoby przekazania informacji

**Cel:** odróżnić granicę modelu od mechanizmu komunikacji.

### Co powiedzieć

Omów trzy warianty.

#### Event-Carried State Transfer

Zdarzenie zawiera dane potrzebne odbiorcy. Odbiorca nie musi natychmiast pytać upstreamu. Cena to większy i wersjonowany kontrakt oraz lokalna kopia danych.

#### Event Notification

Zdarzenie mówi tylko, że coś się zmieniło. Odbiorca pobiera szczegóły przez API. Wiadomość jest mała, ale awaria lub niedostępność upstreamu blokuje pobranie danych.

#### Jawne API

Downstream pyta o bieżący stan. Dobre dla zapytań wymagających świeżych danych. Tworzy zależność czasową pomiędzy systemami.

Podkreśl, że żaden wariant nie jest zawsze najlepszy. Decyzja zależy od wymaganej świeżości, dostępności, wolumenu i odpowiedzialności za dane.

### Krótka dyskusja

„Czy Program musi wiedzieć natychmiast, że uczestnik zmienił typ biletu?”

Najczęściej nie, ponieważ ta informacja w ogóle nie należy do modelu Programu.

---

## Slajd 16 — Pakiet, moduł czy osobny proces?

**Cel:** oddzielić Bounded Context od mikroserwisów.

### Co powiedzieć

Przejdź przez trzy poziomy izolacji.

#### Pakiety

Najtańszy początek. Konteksty mogą istnieć w jednym repozytorium i procesie. Potrzebna jest dyscyplina zależności.

#### Moduły

Moduł pozwala jawnie określić publiczne API i ukryć implementację. To dobry wybór dla modularnego monolitu.

#### Osobne procesy

Dają niezależne wdrożenie, osobne dane i izolację awarii, ale wprowadzają sieć, opóźnienia, obserwowalność oraz spójność ostateczną.

Powiedz wyraźnie: prawidłowo odkryte konteksty mogą później pomóc wydzielić usługę, ale osobny serwis nie tworzy automatycznie dobrego Bounded Context.

---

## Slajd 17 — Granice w strukturze projektu

**Cel:** pokazać możliwą strukturę modularnego monolitu.

### Co powiedzieć

Omów strukturę katalogów. Każdy katalog najwyższego poziomu reprezentuje model i odpowiedzialność, a nie warstwę techniczną całego systemu.

Porównaj dwa układy:

```text
controller / service / repository / entity
```

oraz:

```text
registration / program / access / billing
```

W drugim układzie zmiana w Programie skupia się w jednym obszarze. Wewnątrz kontekstu nadal mogą istnieć warstwy domeny, aplikacji i adapterów znane z wykładu 07.

Podkreśl zagnieżdżenie pojęć:

- system zawiera kilka Bounded Contexts,
- każdy kontekst może mieć własną architekturę heksagonalną,
- wewnątrz domeny kontekstu znajdują się agregaty.

To dobry moment na połączenie wykładów 06, 07 i 08.

---

## Slajd 18 — Testy kontraktów i autonomii

**Cel:** pokazać, co należy testować na granicy.

### Co powiedzieć

Omów cztery rodzaje testów:

- test domeny sprawdza reguły bez innych kontekstów,
- test translatora sprawdza zachowanie znaczenia podczas mapowania,
- test kontraktowy sprawdza zgodność formatu producenta i konsumenta,
- test architektury blokuje niedozwolone importy.

### Zajrzyj do kodu

Otwórz:

`examples/08-bounded-context/src/test/java/pl/edu/pjatk/mpr/convention/program/integration/RegistrationEventTranslatorTest.java`

Pokaż dwa testy.

#### `translates_identity_into_the_program_language`

Test sprawdza mapowanie identyfikatora oraz normalizację nazwy przez `DisplayName`. Wytłumacz, że wynik składa się z typów Programu.

#### `ticket_type_does_not_change_the_program_model`

To ważniejszy test architektonicznie. Dwa różne typy biletu tworzą ten sam model Programu. Test dokumentuje, że `ticketType` nie ma znaczenia w tym kontekście.

### Demonstracja

Uruchom:

```bash
cd examples/08-bounded-context
mvn test
```

Następnie, jeśli czas pozwala, wykonaj krótką zmianę na żywo: dodaj pole `marketingConsent` do `AttendeeRegistered`. Pokaż, że translator może je zignorować, a model Programu pozostaje bez zmian. Cofnij zmianę po demonstracji.

---

## Slajd 19 — Typowe błędy

**Cel:** utrwalić granice przez antyprzykłady.

### Co powiedzieć

#### Wspólny model dla całej firmy

Prowadzi do klas o sprzecznych znaczeniach i bardzo wielu powodach do zmiany.

#### Kontekst wyznaczony z tabeli lub ekranu

Tabela i ekran są szczegółami rozwiązania. Kontekst powinien wynikać z decyzji biznesowych i języka.

#### Wspólna biblioteka domenowa

Biblioteka z `Participant`, `Ticket` i `Speaker` wygląda jak oszczędność, ale zmusza wszystkie zespoły do wspólnego wersjonowania modelu. Współdziel tylko małe, naprawdę stabilne elementy lub kontrakty.

#### Zdarzenie jako kopia encji

Zdarzenie powinno opisywać fakt biznesowy. Kopia encji ujawnia pola techniczne i zwiększa liczbę konsumentów zależnych od struktury wewnętrznej.

#### Mikroserwis na każde pojęcie

Granice zbyt małe tworzą rozproszony monolit. Zacznij od modularnego monolitu, jeżeli nie potrzebujesz niezależnego wdrażania i skalowania.

### Pytanie podsumowujące

„Który z tych błędów można popełnić także w systemie z mikroserwisami?”

Odpowiedź: wszystkie.

---

## Slajd 20 — Projekt przykładowy

**Cel:** zebrać strukturę przykładu przed zakończeniem.

### Co powiedzieć

Pokaż drzewo projektu i nazwij role plików:

- `AttendeeRegistered` jest publicznym kontraktem Rejestracji,
- `SpeakerCandidate`, `DisplayName` i `ExternalPersonId` należą do Programu,
- `RegistrationEventTranslator` jest adapterem na granicy,
- test opisuje dozwolone znaczenie mapowania.

### Zajrzyj do kodu

Otwórz `examples/08-bounded-context/README.md`. Pokaż diagram tekstowy przepływu:

```text
Registration Context
AttendeeRegistered
        ↓
RegistrationEventTranslator
        ↓
SpeakerCandidate
Program Context
```

Zaproponuj ćwiczenie po wykładzie: dodać kontekst Dostępu, zdarzenie `TicketActivated` oraz translator tworzący `BadgeHolderCandidate`.

Warunek ćwiczenia: `BadgeHolderCandidate` nie może importować modelu Rejestracji.

---

## Slajd 21 — Podsumowanie

**Cel:** zamknąć wykład pięcioma tezami.

### Co powiedzieć

Powtórz kolejno:

1. Bounded Context określa, gdzie model i język mają konkretne znaczenie.
2. Ta sama osoba może mieć różne reprezentacje, ponieważ konteksty podejmują różne decyzje.
3. Context Map pokazuje relacje, właścicieli kontraktów i kierunek zależności.
4. Anti-Corruption Layer tłumaczy obcy kontrakt na lokalne pojęcia.
5. Bounded Context może działać w modularnym monolicie. Mikroserwis jest decyzją wdrożeniową, a nie definicją granicy modelu.

### Ostatnie pytanie do studentów

„Jeśli jutro dodamy aplikację mobilną, czy tworzymy nowy Bounded Context?”

Oczekiwana odpowiedź: nie na podstawie samego kanału. Aplikacja mobilna może być nowym adapterem do istniejących przypadków użycia. Nowy kontekst powstaje wtedy, gdy pojawia się odrębny model, język i odpowiedzialność za decyzje.

Zakończ zdaniem:

> Najważniejszą umiejętnością w projektowaniu strategicznym nie jest rysowanie granic. Jest nią rozpoznawanie, gdzie kończy się jedno znaczenie, a zaczyna drugie.

---

## Proponowany rozkład czasu

| Część | Slajdy | Czas |
|---|---:|---:|
| Wprowadzenie i problem | 1–5 | 15 min |
| Definicja i odkrywanie granic | 6–10 | 20 min |
| Context Map i relacje | 11–13 | 15 min |
| ACL, komunikacja i architektura | 14–17 | 20 min |
| Testy i kod | 18–20 | 15 min |
| Podsumowanie | 21 | 5 min |

## Skrócona ścieżka na 60 minut

Jeżeli zajęcia są krótsze:

- skróć slajdy 2–3 do dwóch minut łącznie,
- na slajdzie 8 omów tylko konflikt znaczeń i tempo zmian,
- na slajdzie 13 skup się na Customer / Supplier, Conformist i ACL,
- na slajdzie 15 porównaj tylko Event-Carried State z jawnym API,
- demonstrację kodu ogranicz do translatora i drugiego testu,
- nie wykonuj zmiany `marketingConsent` na żywo.

## Pytania kontrolne po wykładzie

1. Czym Bounded Context różni się od agregatu?
2. Dlaczego wspólny identyfikator osoby nie wymaga wspólnej encji?
3. Co na Context Map oznaczają upstream i downstream?
4. Kiedy Conformist może być rozsądną decyzją?
5. Jaką odpowiedzialność ma Anti-Corruption Layer?
6. Dlaczego zdarzenie nie powinno być automatycznie kopią encji?
7. Czy Bounded Context wymaga osobnej bazy danych?
8. Co w przykładzie potwierdza, że `ticketType` nie należy do Programu?

## Odpowiedzi w skrócie

1. Agregat jest granicą spójności i transakcji, a Bounded Context granicą modelu oraz języka.
2. Każdy kontekst reprezentuje osobę na potrzeby własnych decyzji.
3. Upstream jest właścicielem kontraktu, downstream od niego zależy.
4. Gdy model dostawcy jest wystarczający, stabilny, a koszt tłumaczenia nie daje wartości.
5. Tłumaczy obcy kontrakt na lokalne pojęcia i zatrzymuje wpływ zmian na granicy.
6. Kopia encji ujawnia szczegóły wewnętrzne i zwiększa sprzężenie konsumentów.
7. Nie. Granicę można egzekwować w pakietach lub modułach jednego procesu.
8. Translator pomija pole, a test pokazuje identyczny wynik dla biletów `STANDARD` i `VIP`.
