package ru.yandex.practicum.exeptionsService;

public class WordleExceptionDictionary extends WordleException {
    public WordleExceptionDictionary(String message) {
        super(message);
    }

    public WordleExceptionDictionary(String message, Exception e) {
        super(message, e);
    }
}
