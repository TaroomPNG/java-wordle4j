package ru.yandex.practicum.exeptionsService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Logger {

    private static final String LOGS_DIRECTORY_NAME = "logs";
    private static final String LOGS_NAME = "logs.name";

    private static final Path PATH = Paths.get(
            System.getProperty("user.dir"),
            LOGS_DIRECTORY_NAME,
            LOGS_NAME);

    public static void logging(String message) {
        initLogs();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                PATH.toFile(),
                StandardCharsets.UTF_8,
                true))) {
            bufferedWriter.write(LocalDateTime.now()
                    + " - "
                    + message
                    + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка записи файла логов! " + e.getMessage());
        }
    }

    public static void logging(String message, Exception exception) {
        initLogs();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
                PATH.toFile(),
                StandardCharsets.UTF_8,
                true))) {
            bufferedWriter.write(LocalDateTime.now()
                    + " - "
                    + message
                    + " "
                    + exception.getMessage()
                    + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка записи файла логов! " + e.getMessage());
        }

    }

    private static void initLogs() {
        if (Files.notExists(PATH.getParent())) {
            try {
                Files.createDirectories(PATH.getParent());
            } catch (IOException e) {
                System.err.println("Ошибка инициализации папки логов " + e.getMessage());
            }
        }
    }
}
