package ru.yandex.practicum.exeptionsService;


public class WordleException extends RuntimeException {

    public WordleException(String message) {
        super(message);
        Logger.logging(message);
    }

    public WordleException(String message, Exception e) {
        super(message, e);
        Logger.logging(message, e);
    }

}
