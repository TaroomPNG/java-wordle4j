package ru.yandex.practicum;

import ru.yandex.practicum.exeptionsService.gameExceptions.WordleExceptionGameIsEnd;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordContains;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordLength;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordCorrectness;

import static ru.yandex.practicum.WordleDictionary.formatWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.yandex.practicum.exeptionsService.Logger.logging;

public class WordleGame {

  private final WordleDictionary dictionary;
  private final String secretWord;
  private int steps;
  private final int attempts; // 6 шагов
  private final int wordLength; // 5 букв
  private boolean winStatus;
  private boolean endGame;

  private final List<String> guesses = new ArrayList<>();
  private final List<String> results = new ArrayList<>();

  public WordleGame(int attempts, int wordLength, WordleDictionary dictionary) {
    this.attempts = attempts;
    this.wordLength = wordLength;
    this.dictionary = dictionary;
    this.secretWord = dictionary.getRandomWord();

    this.winStatus = false;
    this.endGame = false;

    logging(
        String.format(
            "Игра инициализирована без ошибок: шагов %d, букв %d, слово загадано %s",
            this.attempts, this.wordLength, this.secretWord));
  }

  public WordleGame(int attempts, int wordLength, WordleDictionary dictionary, String secretWord) {
    this.attempts = attempts;
    this.wordLength = wordLength;
    this.dictionary = dictionary;
    this.secretWord = secretWord;

    this.winStatus = false;
    this.endGame = false;
  }

  public String giveGuess(String guess)
      throws WordleExceptionGameIsEnd,
          WordleExceptionWordLength,
          WordleExceptionWordContains,
          WordleExceptionWordCorrectness {

    if (endGame) {
      String message =
          winStatus
              ? "Игра завершена, вы угадали слово " + secretWord
              : "Игра завершена, вы утратили количество попыток. Загаданное слово: " + secretWord;
      logging(message);
      throw new WordleExceptionGameIsEnd(message);
    }

    String answer = formatWord(guess);
    checkAnswerWord(answer, wordLength); // Проверка на корректность с автологингом и отловом.

    String hint = compare(answer, secretWord);
    guesses.add(answer);
    results.add(hint);
    steps++;
    checkGameStatus(answer);

    logging("Ход номер " + steps + ": ответ " + answer + " -> " + hint);
    return hint;
  }

  public String getHints() throws WordleExceptionGameIsEnd {
    if (endGame) {
      throw new WordleExceptionGameIsEnd("Игра завершена!");
    }

    List<String> words = new ArrayList<>(dictionary.getWords());
    // Знаю только две вещи, первая если оставить как List<String> words = dictionary.getWords();
    // то мы передаем ссылку на оригинальный словарь. Но я читал в книге, Что даже при таком условии
    // мы передаем туда не саму ссылку а копию? Короче, чтобы не попортить все, на всякий случай
    // создаю новый объект

    if (results.isEmpty()) {
      words.remove(secretWord);
      return words.get(new Random().nextInt(words.size()));
    }

    for (int i = 0; i < results.size(); i++) {
      String guess = guesses.get(i);
      String result = results.get(i);
      words = filterWordsForHint(guess, result, words);
    }

    words.removeAll(guesses);
    words.remove(secretWord);
    logging("Запрошена подсказка число подходящих слов " + words.size());

    if (words.isEmpty()) {
      logging("Подсказки закончились");
      return secretWord;
    }

    return words.get(new Random().nextInt(words.size()));
  }

  private List<String> filterWordsForHint(String guess, String result, List<String> words) {

    List<String> hintsOptions = new ArrayList<>();
    for (String word : words) {
      if (compare(guess, word).equals(result)) {
        hintsOptions.add(word);
      }
    }
    return hintsOptions;
  }

  private void checkGameStatus(String answer) {
    if (answer.equals(secretWord)) {
      winStatus = true;
      endGame = true;
      logging("Победа! Загаданное слово: " + secretWord);
    } else if (steps == attempts) {
      endGame = true;
      logging("Поражение. Попытки исчерпаны. Загаданное слово: " + secretWord);
    }
  }

  private String compare(String answer, String secretWord) {

    char[] hint = new char[wordLength];
    ArrayList<Character> secretWordList = new ArrayList<>();

    for (char c : secretWord.toCharArray()) {
      secretWordList.add(c);
    }

    for (int i = 0; i < answer.length(); i++) {
      if (answer.charAt(i) == secretWord.charAt(i)) {
        hint[i] = '+';
        secretWordList.remove(Character.valueOf(answer.charAt(i)));
      }
    }

    for (int i = 0; i < answer.length(); i++) {
      if (hint[i] == '+') {
        continue;
      }
      if (secretWordList.contains(answer.charAt(i))) {
        hint[i] = '^';
        secretWordList.remove(Character.valueOf(answer.charAt(i)));
      } else {
        hint[i] = '-';
      }
    }

    return new String(hint);
  }

  private void checkAnswerWord(String word, int specifiedWordLength)
      throws WordleExceptionWordLength,
          WordleExceptionWordContains,
          WordleExceptionWordCorrectness {

    char[] chars = word.toCharArray();
    for (char wordChar : chars) {
      if (Character.isDigit(wordChar)) {
        throw new WordleExceptionWordCorrectness("Вы ввели цифру " + wordChar + " в слове");
      }
      if (!Character.UnicodeBlock.of(wordChar).equals(Character.UnicodeBlock.CYRILLIC)) {
        throw new WordleExceptionWordCorrectness("Вы ввели не кириллический символ " + wordChar);
      }
    }

    if (word.length() != specifiedWordLength) {
      if (word.length() < specifiedWordLength) {
        throw new WordleExceptionWordLength("Введено слишком короткое слово");
      } else {
        throw new WordleExceptionWordLength("Введено слишком длинное слово");
      }
    }

    if (!dictionary.contains(word)) {
      throw new WordleExceptionWordContains("Слова " + word + " нет в словаре");
    }
  }

  public boolean isWinStatus() {
    return winStatus;
  }

  public int getWordLength() {
    return wordLength;
  }

  public int getAttempts() {
    return attempts;
  }

  public boolean isEndGame() {
    return endGame;
  }

  public int getSteps() {
    return steps;
  }

  public String getSecretWord() {
    return secretWord;
  }
}
