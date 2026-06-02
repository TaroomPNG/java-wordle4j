package ru.yandex.practicum;

import ru.yandex.practicum.exeptionsService.WordleExceptionDictionary;
import ru.yandex.practicum.exeptionsService.gameExceptions.WordleExceptionGameIsEnd;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordContains;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordCorrectness;
import ru.yandex.practicum.exeptionsService.wordsExceptions.WordleExceptionWordLength;

import java.util.Scanner;

public class Wordle {

  public static void main(String[] args) {
    startGame();
  }

  private static void startGame() {
    printMenu();
    Scanner scanner = new Scanner(System.in);
    String command = scanner.nextLine();

    try {
      int commandInt = Integer.parseInt(command);
      switch (commandInt) {
        case (1):
          runGame();
          break;
        case (0):
          return;
      }

    } catch (WordleExceptionDictionary e) {
      System.err.println(e.getMessage());
    } catch (NumberFormatException e) {
      System.err.println("Введите число!");
      startGame();
    }
  }

  private static void runGame() {
    WordleGame game = new WordleGame(6, 5, new WordleDictionary());

    String guess;

    Scanner scanner = new Scanner(System.in);
    System.out.printf(
        "Слово загадано! Число попыток %d,  длинна слова %d \n",
        game.getAttempts(), game.getWordLength());

    while (!game.isEndGame()) {
      try {
        System.out.printf("Попытка номер %d \n", game.getSteps());
        System.out.print("Введите слово: ");
        guess = scanner.nextLine();
        if (guess.trim().isEmpty()) {
          String answer = game.getHints();
          String result = game.giveGuess(answer);
          System.out.printf("Ваш ответ %s -> %s ", answer, result);
        } else {
          String result = game.giveGuess(guess);
          System.out.printf("Ваш ответ %s -> %s \n", guess, result);
        }
      } catch (WordleExceptionWordLength
          | WordleExceptionGameIsEnd
          | WordleExceptionWordContains
          | WordleExceptionWordCorrectness e) {
        System.out.println(e.getMessage());
      }
    }
    if (game.isWinStatus()) {
      System.out.println("Поздравляем! Вы угадали слово: " + game.getSecretWord());
    } else {
      System.out.println("Вы проиграли. Загаданное слово: " + game.getSecretWord());
    }
    startGame();
  }

  private static void printMenu() {
    System.out.println("\n Меню игры");
    System.out.println("Введите нужное число");
    System.out.println("1. Начать игру");
    System.out.println("0. Выход");
  }
}
