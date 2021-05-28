package ru.megalomaniac.tasks.calculator.converters;

// Интерфейс конвертирования чисел из строкового формата в число типа int
public interface NumberConverter {
    String convertFromInt(int number);
    int convertFromString(String number);
}
