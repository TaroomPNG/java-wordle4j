package ru.yandex.practicum.exeptionsService.wordsExceptions;


import ru.yandex.practicum.exeptionsService.Logger;

public class WordleExceptionWordContains extends RuntimeException {

    public WordleExceptionWordContains(String message) {
        super(message);
        Logger.logging(message);
    }

    public WordleExceptionWordContains(String message, Exception e) {
        super(message, e);
        Logger.logging(message, e);
    }

}
