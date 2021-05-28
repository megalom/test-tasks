package ru.megalomaniac.tasks.calculator.validators;

import ru.megalomaniac.tasks.calculator.types.NumberType;

// Интерфейс для проверки типа чисел в строке
public interface NumberValidator {
    NumberType checkNumbers(String input);
    boolean isOperandInRange(int operand);
}
