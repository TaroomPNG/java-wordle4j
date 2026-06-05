package ru.yandex.practicum;

import ru.yandex.practicum.exeptionsService.WordleExceptionDictionary;
import static ru.yandex.practicum.exeptionsService.Logger.logging;
import static ru.yandex.practicum.WordleDictionary.formatWord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

  private String fileName;
  private Path path;

  public WordleDictionaryLoader(String fileName) {
    this.fileName = fileName;
    this.path = Paths.get(System.getProperty("user.dir"), fileName);
  }

  public WordleDictionaryLoader(String fileName, Path path) {
    this.fileName = fileName;
    this.path = path;
  }

  public List<String> loadWordFile() throws WordleExceptionDictionary {
    List<String> words = new ArrayList<>();
    if (Files.notExists(path)) {
      throw new WordleExceptionDictionary("Файл " + fileName + " не найден");
    }

    try (BufferedReader buffer =
        new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {

      logging("Загрузка словаря по адресу " + path.toAbsolutePath());
      String word;
      while ((word = buffer.readLine()) != null) {
        if (word.length() == 5) {
          words.add(formatWord(word));
        }
      }

      if (words.isEmpty()) {
        throw new WordleExceptionDictionary(
            "Файл " + fileName + " загружен, но не содержит нужных слов");
      }

      logging("Файл загружен число слов " + words.size());
    } catch (IOException e) {
      throw new WordleExceptionDictionary("Ошибка загрузки файла " + fileName, e);
    }

    return words;
  }
}
