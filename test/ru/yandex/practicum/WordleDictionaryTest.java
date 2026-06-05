package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.WordleDictionary.formatWord;

public class WordleDictionaryTest {

  @Test
  void testInitDictionaryNotNull() {
    WordleDictionary dictionary = new WordleDictionary();

    assertNotNull(dictionary.getWords());
  }

  @Test
  void testRandomWordNotNull() {
    WordleDictionary dictionary = new WordleDictionary();
    String randomWord = dictionary.getRandomWord();

    assertNotNull(randomWord);
  }

  @Test
  void testRandomWordIsCorrectness() {
    WordleDictionary dictionary = new WordleDictionary();

    for (int i = 0; i < 5; i++) {
      String randomWord = formatWord(dictionary.getRandomWord());

      assertFalse(randomWord.contains("ё"));
      assertFalse(randomWord.contains(" "));
      assertEquals(randomWord, randomWord.toLowerCase());
    }
  }

  @Test
  void testFormatedWord() {
    String word = formatWord(" орёЛк ");

    assertEquals("орелк", word);
    assertFalse(word.startsWith(" "));
    assertFalse(word.endsWith(" "));
  }

  @Test
  void testContainsWord() {
    WordleDictionary dictionary = new WordleDictionary();
    List<String> words = dictionary.getWords();

    assertTrue(words.contains("милые"));
    assertFalse(words.contains("мина"));
  }
}
