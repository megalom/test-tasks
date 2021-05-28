package ru.megalomaniac.tasks.calculator.extractors;

import java.util.List;
// Интерфейс извлечения операндов из строки
public interface OperandsExtractor {
    List<String> extractOperands(String input);
}
