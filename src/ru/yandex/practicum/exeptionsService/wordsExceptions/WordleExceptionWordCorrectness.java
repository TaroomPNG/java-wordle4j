package ru.yandex.practicum.exeptionsService.wordsExceptions;


import ru.yandex.practicum.exeptionsService.Logger;

public class WordleExceptionWordCorrectness extends RuntimeException {

    public WordleExceptionWordCorrectness(String message) {
        super(message);
        Logger.logging(message);
    }

    public WordleExceptionWordCorrectness(String message, Exception e) {
        super(message, e);
        Logger.logging(message, e);
    }

}
