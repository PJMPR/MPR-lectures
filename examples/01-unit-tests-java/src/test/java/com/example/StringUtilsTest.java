package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testy jednostkowe dla klasy StringUtils.
 * 
 * Demonstruje testowanie metod statycznych, obsługę null i edge cases.
 */
public class StringUtilsTest {

    // ===== TESTY DEMONSTRACYJNE =====

    @Test
    public void testReverse_SimpleString() {
        // Arrange
        String input = "hello";
        String expected = "olleh";
        
        // Act
        String result = StringUtils.reverse(input);
        
        // Assert
        assertEquals(expected, result, "Reverse 'hello' powinno dać 'olleh'");
    }

    @Test
    public void testReverse_Null() {
        // Sprawdzamy, że null input daje null output
        assertNull(StringUtils.reverse(null), "Reverse null powinno dać null");
    }

    @Test
    public void testReverse_EmptyString() {
        // Empty string powinno zostać empty stringiem
        assertEquals("", StringUtils.reverse(""), "Reverse '' powinno dać ''");
    }

    @Test
    public void testReverse_SingleCharacter() {
        // Jeden znak pozostaje taki sam
        assertEquals("a", StringUtils.reverse("a"));
    }

    @Test
    public void testReverse_PalindromeWord() {
        // Palindrom po odwróceniu powinien być identyczny
        String palindrome = "racecar";
        assertEquals(palindrome, StringUtils.reverse(palindrome));
    }

    @Test
    public void testCapitalize_LowercaseString() {
        // Capitalize zmienia pierwszą literę na wielką
        assertEquals("Hello", StringUtils.capitalize("hello"));
        assertEquals("World", StringUtils.capitalize("world"));
    }

    @Test
    public void testCapitalize_Null() {
        // Null input daje null
        assertNull(StringUtils.capitalize(null));
    }

    @Test
    public void testCapitalize_EmptyString() {
        // Empty string pozostaje empty
        assertEquals("", StringUtils.capitalize(""));
    }

    @Test
    public void testCapitalize_AlreadyCapitalized() {
        // String już kapitalizowany powinien się nie zmienić
        assertEquals("Hello", StringUtils.capitalize("Hello"));
    }

    @Test
    public void testCapitalize_SingleCharacter() {
        // Pojedynczy znak
        assertEquals("A", StringUtils.capitalize("a"));
    }

    @Test
    public void testCapitalize_MixedCase() {
        // Duży string - только pierwszy znak powinien być zmieniony
        assertEquals("HeLLo WoRLD", StringUtils.capitalize("heLLo WoRLD"));
    }

    @Test
    public void testRepeat_BasicUsage() {
        // Powtarzanie stringa
        assertEquals("ababab", StringUtils.repeat("ab", 3));
        assertEquals("xxx", StringUtils.repeat("x", 3));
    }

    @Test
    public void testRepeat_Zero() {
        // Powtarzanie 0 razy daje pusty string
        assertEquals("", StringUtils.repeat("hello", 0));
    }

    @Test
    public void testRepeat_One() {
        // Powtarzanie 1 raz zwraca oryginalny string
        assertEquals("test", StringUtils.repeat("test", 1));
    }

    @Test
    public void testRepeat_Null() {
        // Null input daje null
        assertNull(StringUtils.repeat(null, 5));
    }

    @Test
    public void testRepeat_NegativeCount_ThrowsException() {
        // Ujemna liczba powtórzeń powinna rzucić exception
        assertThrows(IllegalArgumentException.class, () -> {
            StringUtils.repeat("test", -1);
        }, "Ujemna liczba powtórzeń powinna rzucić IllegalArgumentException");
    }

    @Test
    public void testIsBlank_WithSpaces() {
        // Tylko spacje to blank
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    public void testIsBlank_EmptyString() {
        // Pusty string to też blank
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    public void testIsBlank_WithTabs() {
        // Tabulacje to białe znaki
        assertTrue(StringUtils.isBlank("\t\t"));
    }

    @Test
    public void testIsBlank_WithNewlines() {
        // New lines to białe znaki
        assertTrue(StringUtils.isBlank("\n\n"));
    }

    @Test
    public void testIsBlank_WithContent() {
        // String z zawartością nie jest blank
        assertFalse(StringUtils.isBlank("hello"));
        assertFalse(StringUtils.isBlank(" hello "));
    }

    @Test
    public void testIsBlank_Null() {
        // Null nie jest uważany za blank
        assertFalse(StringUtils.isBlank(null));
    }

    @Test
    public void testStartsWith_Match() {
        // String zaczyna się od danego prefiksu
        assertTrue(StringUtils.startsWith("Hello World", "Hello"));
        assertTrue(StringUtils.startsWith("test", "test"));
    }

    @Test
    public void testStartsWith_NoMatch() {
        // String nie zaczyna się od prefiksu
        assertFalse(StringUtils.startsWith("Hello World", "World"));
        assertFalse(StringUtils.startsWith("test", "xyz"));
    }

    @Test
    public void testStartsWith_Null() {
        // Null string lub prefix daje false
        assertFalse(StringUtils.startsWith(null, "test"));
        assertFalse(StringUtils.startsWith("test", null));
    }

    @Test
    public void testEndsWith_Match() {
        // String kończy się na danym sufiksie
        assertTrue(StringUtils.endsWith("Hello World", "World"));
        assertTrue(StringUtils.endsWith("test", "test"));
    }

    @Test
    public void testEndsWith_NoMatch() {
        // String nie kończy się na sufiksie
        assertFalse(StringUtils.endsWith("Hello World", "Hello"));
    }

    @Test
    public void testEndsWith_Null() {
        // Null string lub suffix daje false
        assertFalse(StringUtils.endsWith(null, "test"));
        assertFalse(StringUtils.endsWith("test", null));
    }

    // ===== TODO: ZADANIA DO UZUPEŁNIENIA =====

    // TODO: Napisz test dla testIsPalindrome_SimplePalindrome
    // Sprawdź metodę isPalindrome dla prostego palindromu "racecar"
    // Oczekiwany wynik: true

    // TODO: Napisz test dla testIsPalindrome_NotPalindrome
    // Sprawdź metodę isPalindrome dla słowa, które nie jest palindromem
    // Np. "hello" powinno dać false

    // TODO: Napisz test dla testIsPalindrome_WithSpaces
    // Sprawdź palindrom z spacjami: "A man a plan a canal Panama"
    // Oczekiwany wynik: true (ignorujemy spacje i wielkość liter)

    // TODO: Napisz test dla testIsPalindrome_EmptyString
    // Sprawdzić czy pusty string jest palindromem
    // Oczekiwany wynik: true

    // TODO: Napisz test dla testIsPalindrome_Null
    // Sprawdzić obsługę null
    // Oczekiwany wynik: false

    // TODO: Napisz test dla testRepeat_EmptyString
    // Sprawdź powtarzanie pustego stringa
    // Oczekiwany wynik: ""

    // TODO: Napisz test dla testCapitalize_WithNumbers
    // Sprawdź kapitalizację stringa zaczynającego się od liczby
    // Np. "1hello" powinno pozostać "1hello"

    // TODO: Napisz test dla testReverse_WithSpecialCharacters
    // Sprawdź odwracanie stringa ze znakami specjalnymi
    // Np. "hello@world!" powinno dać "!dlrow@olleh"
}
