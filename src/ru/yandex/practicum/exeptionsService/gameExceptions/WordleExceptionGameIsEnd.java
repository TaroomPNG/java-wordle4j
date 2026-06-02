package ru.yandex.practicum.exeptionsService.gameExceptions;


import ru.yandex.practicum.exeptionsService.Logger;

public class WordleExceptionGameIsEnd extends RuntimeException {

    public WordleExceptionGameIsEnd(String message) {
        super(message);
        Logger.logging(message);
    }

    public WordleExceptionGameIsEnd(String message, Exception e) {
        super(message, e);
        Logger.logging(message, e);
    }

}
