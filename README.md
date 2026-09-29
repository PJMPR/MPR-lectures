# MPR Lectures - Metody Programowania w Java

Kompletna platforma edukacyjna poświęcona programowaniu w Java, testowaniu jednostkowemu, design patternach i best practices.

## 📚 Zawartość

Kurs zawiera **15 wykładów** obejmujących:

1. ✅ **Wstęp do testów jednostkowych w Java** - JUnit 5, AAA Pattern, asercje
2. **Zaawansowane testowanie - Mockito** - Mockowanie, Spy, stubbing
3. **Test-Driven Development (TDD)** - Red-Green-Refactor cykl
4. **Design Patterns - Singleton i Factory** - Wzorce projektowe
5. **Design Patterns - Observer i Decorator** - Event-driven, composition
6. **SOLID Principles - S, O, L** - Zasady SOLID
7. **SOLID Principles - I, D** - Interface Segregation, Dependency Injection
8. **Collections Framework** - List, Set, Map, wybór struktur danych
9. **Streams API i Functional Programming** - Lambda, stream operations
10. **Exception Handling Best Practices** - Try-catch, custom exceptions
11. **Concurrency - Threads i Synchronization** - Thread lifecycle, monitors
12. **Concurrency - ExecutorService i Locks** - Thread pools, ReentrantLock
13. **Reflection API i Annotations** - Metaprogramming, custom annotations
14. **JUnit 5 Advanced Features** - Parameterized tests, extensions
15. **Integration Testing i Best Practices** - Test pyramid, CI/CD

## 🔗 Przejdź do strony

👉 **[Strona główna - Wszystkie wykłady](https://pjmpr.github.io/MPR-lectures/)**

## 📂 Struktura repozytorium

```
docs/                              # GitHub Pages (strona statyczna)
├── index.html                     # Strona główna
├── css/style.css                  # Nowoczesny design Sci-Fi
├── js/main.js                     # Interaktywność
└── lectures/
    ├── 01-unit-tests.html         # ✅ Pierwszy wykład (gotowy)
    ├── 02-mockito.html
    ├── 03-tdd.html
    └── ... (szablony dla 04-15)

examples/                          # Projekty Maven do laboratoriów
└── 01-unit-tests-java/
    ├── pom.xml                    # Konfiguracja Maven + JUnit 5
    ├── README.md                  # Instrukcje dla studentów
    └── src/
        ├── main/java/com/example/
        │   ├── Calculator.java
        │   └── StringUtils.java
        └── test/java/com/example/
            ├── CalculatorTest.java
            └── StringUtilsTest.java

.github/workflows/
└── deploy.yml                     # GitHub Actions - CI/CD

.gitignore                         # Maven, IDE artifacts
```

## 🚀 Szybki start

### 1. Przeglądaj wykłady online

```
https://pjmpr.github.io/MPR-lectures/
```

### 2. Klonuj repozytorium

```bash
git clone https://github.com/PJMPR/MPR-lectures.git
cd MPR-lectures
```

### 3. Otwórz projekt Maven (pierwszy wykład)

```bash
cd examples/01-unit-tests-java
mvn clean install
mvn test
```

### 4. Otwórz w IDE

#### IntelliJ IDEA
- File → Open
- Wybierz folder `01-unit-tests-java`
- IDEA automatycznie rozpozna Maven

#### VS Code
- Open folder: `01-unit-tests-java`
- Zainstaluj: Extension Pack for Java

#### Eclipse
- File → Import → Existing Maven Projects

## 📋 Zawartość pierwszego wykładu (Wstęp do testów jednostkowych)

### Teoria
- ✅ Czym są testy jednostkowe?
- ✅ Dlaczego testy są ważne?
- ✅ AAA Pattern (Arrange, Act, Assert)
- ✅ JUnit 5 - anotacje @Test, @BeforeEach, @AfterEach
- ✅ Asercje - assertEquals, assertTrue, assertThrows
- ✅ Testowanie exceptionsów
- ✅ Best Practices
- ✅ Jak uruchomić testy?

### Praktyka
- ✅ Projekt Maven z JUnit 5
- ✅ Klasy do testowania: Calculator, StringUtils
- ✅ Testy gotowe: CalculatorTest (17 testów)
- ✅ TODO do uzupełnienia: StringUtilsTest

### Uruchamianie testów

```bash
# Wszystkie testy
mvn test

# Tylko CalculatorTest
mvn test -Dtest=CalculatorTest

# Konkretny test
mvn test -Dtest=CalculatorTest#testAdd_PositiveNumbers
```

## 🎯 Technologie

| Technologia | Wersja | Przeznaczenie |
|---|---|---|
| **Java** | 11+ | Główny język programowania |
| **JUnit 5** | 5.10.0 | Framework do testowania |
| **Maven** | 3.6+ | Build automation |
| **GitHub Pages** | - | Hosting strony |
| **GitHub Actions** | - | CI/CD deployment |

## 🛠️ Jak dodać nowy wykład?

1. **Stwórz plik HTML**: `docs/lectures/0X-nazwa-tematu.html`
2. **Dodaj link** w `docs/index.html` do nowego wykładu
3. **Stwórz projekt Maven** (opcjonalnie): `examples/0X-nazwa-projektu/`
4. **Napisz README.md** dla projektu
5. **Wypushuj zmiany** - GitHub Actions automatycznie zdeployuje stronę

## 📖 Zasoby

- [JUnit 5 Official Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Documentation](https://maven.apache.org/guides/)
- [Java Documentation](https://docs.oracle.com/en/java/)
- [GitHub Pages](https://pages.github.com/)

## 👨‍🏫 Prowadzący

**PJATK** - Akademia Polsko-Japońska  
**MPR** - Metody Programowania w Java

## 📄 Licencja

Ten projekt jest dostępny pod licencją MIT.

## 🤝 Kontakt

Jeśli masz pytania lub sugestie:
- Otwórz issue w repozytorium GitHub
- Kontaktuj się z prowadzącym

---

**Zbudowane z ❤️ dla studentów Java**