package ru.yandex.practicum;

import ru.yandex.practicum.exeptionsService.WordleExceptionDictionary;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordCorrectness;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

  private List<String> words;

  public WordleDictionary() throws WordleExceptionDictionary {
    initDictionary();
  }

  private void initDictionary() throws WordleExceptionDictionary {
    WordleDictionaryLoader loader = new WordleDictionaryLoader("words_ru.txt");
    words = loader.loadWordFile();
  }

  public boolean contains(String word) {
    return words.contains(word);
  }

  public String getRandomWord() {
    Random random = new Random();
    int randomIndex = random.nextInt(words.size());
    return words.get(randomIndex);
  }

  public static String formatWord(String word) throws WordleExceptionWordCorrectness {
    if (word == null) {
      throw new WordleExceptionWordCorrectness("В форматирование слова передан null");
    }
    return word.trim().toLowerCase().replace("ё", "е");
  }

  public List<String> getWords() {
    return words;
  }
}
