package ru.megalomaniac.tasks.calculator.extractors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Класс для извлечения арабских чисел из строки
public class ArabicOperandsExtractor implements OperandsExtractor{
    @Override
    public List<String> extractOperands(String input) {
        List<String> operands = new ArrayList<>();
        Pattern operandsPattern = Pattern.compile("[0-9]+|[-+*/]");
        Matcher operandsMatcher = operandsPattern.matcher(input);
        while (operandsMatcher.find()){
            operands.add(operandsMatcher.group());
        }
        return operands;
    }
}
