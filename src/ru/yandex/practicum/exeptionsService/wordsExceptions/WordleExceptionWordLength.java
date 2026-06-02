package ru.yandex.practicum.exeptionsService.wordsExceptions;


import ru.yandex.practicum.exeptionsService.Logger;

public class WordleExceptionWordLength extends RuntimeException {

    public WordleExceptionWordLength(String message) {
        super(message);
        Logger.logging(message);
    }

    public WordleExceptionWordLength(String message, Exception e) {
        super(message, e);
        Logger.logging(message, e);
    }

}
