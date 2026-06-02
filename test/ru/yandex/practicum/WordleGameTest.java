package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordContains;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordCorrectness;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordLength;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

  @Test
  void testGetterAndSetter() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary());

    assertEquals(6, game.getAttempts());
    assertEquals(5, game.getWordLength());
    assertEquals(0, game.getSteps());
    assertFalse(game.isEndGame());
    assertFalse(game.isWinStatus());
  }

  @Test
  void testGiveGuessCorrectWord() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary(), "аббат");

    String answer = game.giveGuess("аббат");

    assertEquals("+++++", answer);
    assertTrue(game.isWinStatus());
    assertTrue(game.isEndGame());
  }

  @Test
  void testGiveGuessWrongWord() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary(), "аббат");

    String answer = game.giveGuess("абвер");

    assertEquals("++---", answer);
    assertEquals(1, game.getSteps());
    assertFalse(game.isEndGame());
    assertFalse(game.isWinStatus());
  }

  @Test
  void testGameLostByAttempts() {
    WordleGame game = new WordleGame(2, 5, new WordleDictionary(), "аббат");

    game.giveGuess("абвер");
    game.giveGuess("абвер");
    assertTrue(game.isEndGame());
    assertFalse(game.isWinStatus());
  }

  @Test
  void testGiveGuessWrongLength() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary(), "аббат");

    WordleExceptionWordLength shortEx =
        assertThrows(WordleExceptionWordLength.class, () -> game.giveGuess("зуб"));

    WordleExceptionWordLength longEx =
        assertThrows(WordleExceptionWordLength.class, () -> game.giveGuess("зрелищность"));

    assertEquals("Введено слишком короткое слово", shortEx.getMessage());
    assertEquals("Введено слишком длинное слово", longEx.getMessage());
  }

  @Test
  void testGiveGuessWordNotInDictionary() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary());

    WordleExceptionWordContains exceptionWordContains =
        assertThrows(WordleExceptionWordContains.class, () -> game.giveGuess("абвгд"));

    assertEquals("Слова абвгд нет в словаре", exceptionWordContains.getMessage());
  }

  @Test
  void testGiveGuessInvalidCharacters() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary());

    WordleExceptionWordCorrectness exceptionCorrectnessCyrillic =
        assertThrows(WordleExceptionWordCorrectness.class, () -> game.giveGuess("hello"));

    WordleExceptionWordCorrectness exceptionWordCorrectnessDigit =
        assertThrows(WordleExceptionWordCorrectness.class, () -> game.giveGuess("сл1во"));

    assertEquals("Вы ввели не кириллический символ h", exceptionCorrectnessCyrillic.getMessage());
    assertEquals("Вы ввели цифру 1 в слове", exceptionWordCorrectnessDigit.getMessage());
  }
}
