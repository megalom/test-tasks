package ru.megalomaniac.tasks.calculator.extractors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс для извлечения римских чисел из строки
public class RomanOperandsExtractor implements OperandsExtractor {

    @Override
    public List<String> extractOperands(String input) {
        List<String> operands = new ArrayList<>();
        Pattern operandsPattern = Pattern.compile("[IVX]+|[-+*/]");
        Matcher operandsMatcher = operandsPattern.matcher(input);
        while (operandsMatcher.find()){
            operands.add(operandsMatcher.group());
        }
        return operands;
    }
}
