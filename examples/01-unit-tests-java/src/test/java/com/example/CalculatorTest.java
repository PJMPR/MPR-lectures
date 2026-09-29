package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy Calculator.
 * 
 * Demonstruje używanie:
 * - Anotacji @Test, @BeforeEach
 * - AAA Pattern (Arrange, Act, Assert)
 * - Różnych asercji (assertEquals, assertTrue, assertThrows)
 * - Edge cases (dzielenie przez 0, liczby ujemne)
 */
public class CalculatorTest {

    private Calculator calculator;

    /**
     * Metoda uruchamiana PRZED każdym testem.
     * Przygotowuje "fixture" - obiekty do testowania.
     */
    @BeforeEach
    public void setUp() {
        // Arrange
        calculator = new Calculator();
    }

    // ===== TESTY DZIAŁAJĄCE =====

    @Test
    public void testAdd_PositiveNumbers() {
        // Arrange
        int a = 5;
        int b = 3;
        
        // Act
        int result = calculator.add(a, b);
        
        // Assert
        assertEquals(8, result, "5 + 3 powinno równać się 8");
    }

    @Test
    public void testAdd_NegativeNumbers() {
        // Arrange
        int a = -5;
        int b = -3;
        
        // Act
        int result = calculator.add(a, b);
        
        // Assert
        assertEquals(-8, result, "-5 + (-3) powinno równać się -8");
    }

    @Test
    public void testAdd_PositiveAndNegative() {
        // Act & Assert
        assertEquals(-2, calculator.add(5, -7));
        assertEquals(2, calculator.add(-5, 7));
    }

    @Test
    public void testAdd_Zero() {
        // Sprawdzenie zachowania się dodawania z zerem
        assertEquals(5, calculator.add(5, 0));
        assertEquals(5, calculator.add(0, 5));
        assertEquals(0, calculator.add(0, 0));
    }

    @Test
    public void testSubtract() {
        // Odejmowanie
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-2, calculator.subtract(3, 5));
        assertEquals(0, calculator.subtract(5, 5));
    }

    @Test
    public void testMultiply_PositiveNumbers() {
        // Mnożenie liczb dodatnich
        assertEquals(15, calculator.multiply(5, 3));
        assertEquals(20, calculator.multiply(4, 5));
    }

    @Test
    public void testMultiply_WithZero() {
        // Mnożenie przez zero zawsze daje zero
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    public void testMultiply_NegativeNumbers() {
        // Ujemna × ujemna = dodatnia
        assertEquals(15, calculator.multiply(-5, -3));
        // Dodatnia × ujemna = ujemna
        assertEquals(-15, calculator.multiply(5, -3));
    }

    @Test
    public void testDivide_PositiveNumbers() {
        // Działanie dzielenia
        assertEquals(5, calculator.divide(15, 3));
        assertEquals(2, calculator.divide(7, 3)); // Dzielenie całkowite
    }

    /**
     * Test sprawdzający EXCEPTION - jeden z najważniejszych aspektów testowania!
     * 
     * Chcemy się upewnić, że nasz kod rzuca właściwy wyjątek w odpowiednich sytuacjach.
     */
    @Test
    public void testDivide_ByZero_ThrowsException() {
        // Sprawdzamy, czy dzielenie przez zero rzuca ArithmeticException
        assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10, 0);
        }, "Dzielenie przez 0 powinno rzucić ArithmeticException");
    }

    @Test
    public void testModulo() {
        // Operator modulo (reszta z dzielenia)
        assertEquals(1, calculator.modulo(7, 3));
        assertEquals(0, calculator.modulo(10, 5));
        assertEquals(3, calculator.modulo(3, 7));
    }

    @Test
    public void testModulo_ByZero_ThrowsException() {
        // Modulo przez 0 też powinno rzucić exception
        assertThrows(ArithmeticException.class, () -> {
            calculator.modulo(10, 0);
        });
    }

    @Test
    public void testIsEven() {
        // assertTrue - sprawdzenie czy warunek jest true
        assertTrue(calculator.isEven(4), "4 jest parzyste");
        assertTrue(calculator.isEven(0), "0 jest parzyste");
        assertTrue(calculator.isEven(-2), "-2 jest parzyste");
        
        // assertFalse - sprawdzenie czy warunek jest false
        assertFalse(calculator.isEven(3), "3 nie jest parzyste");
        assertFalse(calculator.isEven(-5), "-5 nie jest parzyste");
    }

    @Test
    public void testIsPositive() {
        // Liczby dodatnie
        assertTrue(calculator.isPositive(1));
        assertTrue(calculator.isPositive(100));
        
        // Liczby niedodatnie
        assertFalse(calculator.isPositive(0), "0 nie jest dodatnie");
        assertFalse(calculator.isPositive(-1), "-1 nie jest dodatnie");
    }

    // ===== TODO: ZADANIA DO UZUPEŁNIENIA =====

    // TODO: Napisz test dla testMultiply_NegativeAndPositive
    // Sprawdź mnożenie liczby ujemnej przez dodatnią
    // Oczekiwany wynik: liczba ujemna

    // TODO: Napisz test dla testDivide_NegativeNumbers
    // Sprawdź dzielenie liczb ujemnych
    // Edge case: (-10) / (-2) powinno dać 5, (-10) / 2 powinno dać -5

    // TODO: Napisz test dla testIsEven_WithNegativeNumbers
    // Rozszerz test isEven o dodatkowe przypadki z liczbami ujemnymi

    // TODO: Napisz test dla testAdd_LargeNumbers
    // Sprawdź dodawanie dużych liczb (zbliżających się do Integer.MAX_VALUE)
}
