# Unit Tests - Java Example Project

Projekt przykładowy do laboratoriów: **Wstęp do testów jednostkowych w Java**

## 📌 O projekcie

Ten projekt zawiera kod demonstracyjny i testy jednostkowe napisane w **JUnit 5**. 
Jest przeznaczony dla studentów, którzy chcą nauczyć się podstaw testowania w Java.

## 🏗️ Struktura projektu

```
src/
├── main/java/com/example/
│   ├── Calculator.java        # Prosta klasa kalkulator do testowania
│   └── StringUtils.java       # Narzędzia do manipulacji stringami
└── test/java/com/example/
    ├── CalculatorTest.java    # Testy dla Calculator
    └── StringUtilsTest.java   # Testy dla StringUtils (TODO do uzupełnienia)

pom.xml                        # Konfiguracja Maven (JUnit 5, Surefire)
README.md                      # Ten plik
```

## 🚀 Jak zacząć?

### Wymagania
- **Java 11+** (minimum)
- **Maven 3.6+**
- **IDE**: IntelliJ IDEA, Eclipse, czy VS Code

### 1. Klonowanie repozytorium

```bash
git clone https://github.com/PJMPR/MPR-lectures.git
cd MPR-lectures/examples/01-unit-tests-java
```

### 2. Otwieranie w IDE

#### **IntelliJ IDEA**
1. File → Open
2. Wybierz folder `01-unit-tests-java`
3. IDEA automatycznie wykryje projekt Maven
4. Czekaj aż IntelliJ pobierze dependencies

#### **Eclipse**
1. File → Import → Existing Maven Projects
2. Root Directory: `/examples/01-unit-tests-java`
3. Click Finish

#### **VS Code**
1. Open folder: `/examples/01-unit-tests-java`
2. Zainstaluj extension: "Extension Pack for Java"
3. VS Code automatycznie rozpozna projekt

## 🧪 Uruchamianie testów

### Z wiersza poleceń (CLI)

```bash
# Uruchom wszystkie testy
mvn test

# Uruchom tylko testy z klasy CalculatorTest
mvn test -Dtest=CalculatorTest

# Uruchom tylko test o nazwie testAdd_PositiveNumbers
mvn test -Dtest=CalculatorTest#testAdd_PositiveNumbers

# Uruchom z wyższą werbalizacją
mvn test -X
```

### Z IDE (IntelliJ IDEA)

1. Otwórz plik `CalculatorTest.java`
2. Kliknij zielony play ▶️ obok nazwy klasy
3. Lub: Right-click → Run 'CalculatorTest'

### Z IDE (VS Code)

1. Zainstaluj "Test Runner for Java"
2. Nad metodą testową pojawi się "Run" i "Debug"
3. Kliknij na "Run"

## 📚 Zawartość kodu

### Calculator.java

Zawiera metody:
- `add(a, b)` - dodawanie
- `subtract(a, b)` - odejmowanie
- `multiply(a, b)` - mnożenie
- `divide(a, b)` - dzielenie (edge case: dzielenie przez 0)
- `modulo(a, b)` - reszta z dzielenia
- `isEven(n)` - sprawdzenie czy liczba jest parzysta
- `isPositive(n)` - sprawdzenie czy liczba jest dodatnia

### StringUtils.java

Zawiera metody:
- `reverse(text)` - odwrócenie stringa
- `capitalize(text)` - kapitalizacja (wielka litera na początku)
- `isPalindrome(text)` - sprawdzenie czy jest palindromem
- `repeat(text, count)` - powtórzenie stringa
- `isBlank(text)` - czy string zawiera tylko białe znaki
- `startsWith(text, prefix)` - czy zaczyna się na prefiks
- `endsWith(text, suffix)` - czy kończy się na sufiks

## 📋 Zadania dla studentów

### Poziom 1: Uzupełnienie brakujących testów

W obu klasach testowych (`CalculatorTest.java` i `StringUtilsTest.java`) 
znajdują się komentarze `// TODO:` wskazujące, które testy należy napisać.

Przyk​łady TODO:
```java
// TODO: Napisz test dla testDivide_NegativeNumbers
// Sprawdź dzielenie liczb ujemnych
// Edge case: (-10) / (-2) powinno dać 5, (-10) / 2 powinno dać -5
```

### Poziom 2: Dodanie nowych metod i testów

1. Dodaj nową metodę do `Calculator`:
   - `square(n)` - potęgowanie do kwadratu
   - `sqrt(n)` - pierwiastek kwadratowy
   - `power(base, exp)` - potęgowanie

2. Napisz dla niej testy (co najmniej 3 testy na metodę):
   - Przypadek normalny
   - Edge case (np. sqrt(-1) → exception?)
   - Graniczna wartość

### Poziom 3: Nowa klasa z testami

Stwórz nową klasę `ArrayUtils.java` w `src/main/java/com/example/`:
- `sum(array)` - suma elementów
- `max(array)` - maksimum
- `min(array)` - minimum
- `reverse(array)` - odwrócenie tablicy

Napisz odpowiadające testy w `ArrayUtilsTest.java` z przynajmniej 4 testami na metodę.

## 🎓 Koncepty testowania zawarte w projekcie

### 1. **AAA Pattern** (Arrange, Act, Assert)
```java
@Test
public void testAdd_PositiveNumbers() {
    // Arrange - przygotowanie danych
    int a = 5;
    int b = 3;
    
    // Act - wykonanie testu
    int result = calculator.add(a, b);
    
    // Assert - sprawdzenie wyniku
    assertEquals(8, result);
}
```

### 2. **Asercje (Assertions)**
```java
assertEquals(expected, actual)        // Równość
assertTrue(condition)                 // Warunek true
assertFalse(condition)                // Warunek false
assertNull(object)                    // Null
assertNotNull(object)                 // Not null
assertThrows(Exception.class, () -> ...)  // Rzucanie exception
```

### 3. **Obsługa exceptionsów**
```java
@Test
public void testDivide_ByZero_ThrowsException() {
    assertThrows(ArithmeticException.class, () -> {
        calculator.divide(10, 0);
    });
}
```

### 4. **Fixture Setup**
```java
@BeforeEach
public void setUp() {
    calculator = new Calculator();  // Uruchamia się przed każdym testem
}
```

### 5. **Edge Cases**
- Liczby ujemne
- Zero
- Null
- Puste stringi
- Bardzo duże liczby

## 📖 Linki do dokumentacji

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Effective Java Testing](https://www.oracle.com/technical-resources/articles/java/junit-tutorial.html)

## 🔗 Powrót do strony wykładu

👉 [Wstęp do testów jednostkowych w Java](../../docs/lectures/01-unit-tests.html)

## 📞 Kontakt / Pytania

Jeśli masz pytania dotyczące tego projektu, 
skontaktuj się z prowadzącym wykład lub stwórz issue w repozytorium GitHub.

---

**Powodzenia w nauce testowania! 🚀**
