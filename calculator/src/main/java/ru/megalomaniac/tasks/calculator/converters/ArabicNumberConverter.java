package ru.megalomaniac.tasks.calculator.converters;

// Конвертер для работы с римскими числами
public class ArabicNumberConverter implements NumberConverter {
    // Возвращает строку с арабским числом
    @Override
    public String convertFromInt(int number) {
        return String.valueOf(number);
    }

    // Возвращает число из строки арбаским числом
    @Override
    public int convertFromString(String number) {
        return Integer.valueOf(number);
    }
}
