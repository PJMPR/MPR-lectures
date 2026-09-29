package com.example;

/**
 * Narzędzia do manipulacji stringami.
 * 
 * Klasa zawiera metody do manipulacji tekstem, którymi posługujemy się
 * w testach jednostkowych. Metody mają obsługę null i empty strings.
 */
public class StringUtils {

    /**
     * Odwraca string (zmienia kolejność znaków).
     * 
     * Przykłady:
     * - "hello" → "olleh"
     * - "" → ""
     * - null → null
     * 
     * @param text tekst do odwrócenia
     * @return odwrócony tekst, lub null jeśli wejście jest null
     */
    public static String reverse(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()) {
            return "";
        }
        return new StringBuilder(text).reverse().toString();
    }

    /**
     * Sprawdza, czy string jest palindromem.
     * 
     * Ignoruje spacje, wielkość liter i znaki specjalne.
     * Przykłady:
     * - "racecar" → true
     * - "A man a plan a canal Panama" → true
     * - "hello" → false
     * - null → false
     * - "" → true
     * 
     * @param text tekst do sprawdzenia
     * @return true jeśli palindrom, false w przeciwnym wypadku
     */
    public static boolean isPalindrome(String text) {
        if (text == null || text.isEmpty()) {
            return text == null ? false : true;
        }
        
        // Usuwamy spacje i konwertujemy na lowercase
        String cleaned = text.replaceAll("\\s+", "").toLowerCase();
        
        // Porównujemy z odwrotnością
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    /**
     * Kapitalizuje pierwszy znak stringa (zmienia na wielką literę).
     * 
     * Przykłady:
     * - "hello" → "Hello"
     * - "world" → "World"
     * - "" → ""
     * - null → null
     * 
     * @param text tekst do kapitalizacji
     * @return tekst z pierwszą wielką literą, lub null jeśli wejście jest null
     */
    public static String capitalize(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()) {
            return "";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    /**
     * Sprawdza, czy string zawiera tylko białe znaki (spacje, tabulacje, new lines).
     * 
     * Przykłady:
     * - "   " → true
     * - "\t\n" → true
     * - "hello" → false
     * - "" → true
     * - null → false
     * 
     * @param text tekst do sprawdzenia
     * @return true jeśli string zawiera tylko białe znaki lub jest pusty
     */
    public static boolean isBlank(String text) {
        if (text == null) {
            return false;
        }
        return text.trim().isEmpty();
    }

    /**
     * Powtarza string n razy.
     * 
     * Przykłady:
     * - repeat("ab", 3) → "ababab"
     * - repeat("x", 0) → ""
     * - repeat("test", 1) → "test"
     * 
     * @param text tekst do powtórzenia
     * @param count liczba powtórzeń (musi być >= 0)
     * @return powtórzony tekst
     * @throws IllegalArgumentException jeśli count < 0
     */
    public static String repeat(String text, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative!");
        }
        if (text == null) {
            return null;
        }
        if (count == 0 || text.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(text);
        }
        return sb.toString();
    }

    /**
     * Sprawdza, czy string zaczyna się od podanego prefiksu (case-sensitive).
     * 
     * @param text tekst do sprawdzenia
     * @param prefix prefiks
     * @return true jeśli string zaczyna się od prefiksu
     */
    public static boolean startsWith(String text, String prefix) {
        if (text == null || prefix == null) {
            return false;
        }
        return text.startsWith(prefix);
    }

    /**
     * Sprawdza, czy string kończy się na podanym sufiksie (case-sensitive).
     * 
     * @param text tekst do sprawdzenia
     * @param suffix sufiks
     * @return true jeśli string kończy się na sufiksie
     */
    public static boolean endsWith(String text, String suffix) {
        if (text == null || suffix == null) {
            return false;
        }
        return text.endsWith(suffix);
    }
}
