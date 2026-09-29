package com.example;

/**
 * Prosty kalkulator do demonstracji testów jednostkowych.
 * 
 * Klasa zawiera podstawowe operacje arytmetyczne i edge cases
 * (np. dzielenie przez zero), które będą testowane.
 */
public class Calculator {

    /**
     * Dodawanie dwóch liczb całkowitych.
     * 
     * @param a pierwsza liczba
     * @param b druga liczba
     * @return suma a + b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Odejmowanie dwóch liczb całkowitych.
     * 
     * @param a liczba, od której odejmujemy
     * @param b liczba do odjęcia
     * @return różnica a - b
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Mnożenie dwóch liczb całkowitych.
     * 
     * @param a pierwsza liczba
     * @param b druga liczba
     * @return iloczyn a * b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Dzielenie dwóch liczb całkowitych.
     * 
     * UWAGA: Jeśli dzielnik jest równy 0, rzucana jest exception.
     * 
     * @param a dzielna
     * @param b dzielnik (nie może być 0)
     * @return iloraz a / b
     * @throws ArithmeticException jeśli b == 0
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }
        return a / b;
    }

    /**
     * Moduł (reszta z dzielenia) dwóch liczb.
     * 
     * @param a liczba, z której bierzemy resztę
     * @param b dzielnik (nie może być 0)
     * @return reszta z dzielenia a % b
     * @throws ArithmeticException jeśli b == 0
     */
    public int modulo(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot calculate modulo by zero!");
        }
        return a % b;
    }

    /**
     * Sprawdza, czy liczba jest parzysta.
     * 
     * @param number liczba do sprawdzenia
     * @return true jeśli liczba jest parzysta, false w przeciwnym wypadku
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Sprawdza, czy liczba jest dodatnia.
     * 
     * @param number liczba do sprawdzenia
     * @return true jeśli liczba > 0, false w przeciwnym wypadku
     */
    public boolean isPositive(int number) {
        return number > 0;
    }
}
