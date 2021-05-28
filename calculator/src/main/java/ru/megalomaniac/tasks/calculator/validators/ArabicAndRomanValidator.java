package ru.megalomaniac.tasks.calculator.validators;

import ru.megalomaniac.tasks.calculator.types.NumberType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Валидатор проверяет наличие арабских или римских чисел в строке
public class ArabicAndRomanValidator implements NumberValidator {

    // Возвращает тип числа в строке
    @Override
    public NumberType checkNumbers(String input) {
        NumberType numberType=NumberType.UNKNOWN;

        if(isArabicNumbers(input))
            numberType = NumberType.ARABIC;

        if(isRomanNumbers(input))
            numberType = NumberType.ROMAN;

        return numberType;
    }

    @Override
    public boolean isOperandInRange(int operand) {
        boolean result=true;
        if(operand<=0)
            result=false;
        if(operand>10)
            result=false;
        return result;
    }

    // Проверяет строку на наличе арабских чисел
    private boolean isArabicNumbers(String input){
        boolean result = true;

        Pattern patternMatch = Pattern.compile("[0-9]+\\s*[-+/*]\\s*[0-9]+");
        Matcher matcher = patternMatch.matcher(input);
        if(!matcher.matches())
            result = false;

        return result;
    }

    // Проверяет строку на наличе римских чисел
    private boolean isRomanNumbers(String input){
        boolean result = true;

        Pattern patternMatch = Pattern.compile("[XIV]+\\s*[-+/*]\\s*[XIV]+");
        Matcher matcher = patternMatch.matcher(input);
        if(!matcher.matches())
            result = false;

        return result;
    }
}
