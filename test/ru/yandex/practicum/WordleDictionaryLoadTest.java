package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.yandex.practicum.exeptionsService.WordleExceptionDictionary;

public class WordleDictionaryLoadTest {

    @Test
    void testLoaderFileNotFoudException(@TempDir Path tempDir) {
        Path path = tempDir.resolve("missing.txt");
        WordleDictionaryLoader loader = new WordleDictionaryLoader("missing.txt", path);

        WordleExceptionDictionary exceptionDictionary = assertThrows(
                WordleExceptionDictionary.class, loader::loadWordFile
        );

        assertEquals("Файл missing.txt не найден", exceptionDictionary.getMessage());
    }

    @Test
    void testLoaderFileWithNoWords(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("empty.txt");

        Files.createFile(path);

        WordleDictionaryLoader loader = new WordleDictionaryLoader("empty.txt", path);

        WordleExceptionDictionary exceptionDictionary = assertThrows(
                WordleExceptionDictionary.class, loader::loadWordFile
        );

        assertEquals("Файл empty.txt загружен, но не содержит нужных слов",
                exceptionDictionary.getMessage());
    }

    @Test
    void testLoaderIsSuccessful(@TempDir Path tempDir) throws IOException {
        Path path = tempDir.resolve("successful.txt");

        Files.write(path, List.of("СЛОВО", "деревО", "Книга"), StandardCharsets.UTF_8);

        WordleDictionaryLoader loader = new WordleDictionaryLoader("successful.txt", path);

        List<String> wordList = loader.loadWordFile();

        assertEquals(2, wordList.size());

        assertTrue(wordList.contains("слово"));
        assertTrue(wordList.contains("книга"));
    }

}
