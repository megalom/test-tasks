package ru.megalomaniac.tasks.calculator;

import ru.megalomaniac.tasks.calculator.converters.ArabicNumberConverter;
import ru.megalomaniac.tasks.calculator.converters.NumberConverter;
import ru.megalomaniac.tasks.calculator.converters.RomanNumberConverter;
import ru.megalomaniac.tasks.calculator.extractors.ArabicOperandsExtractor;
import ru.megalomaniac.tasks.calculator.extractors.OperandsExtractor;
import ru.megalomaniac.tasks.calculator.extractors.RomanOperandsExtractor;
import ru.megalomaniac.tasks.calculator.types.NumberType;
import ru.megalomaniac.tasks.calculator.types.OperationType;
import ru.megalomaniac.tasks.calculator.validators.ArabicAndRomanValidator;
import ru.megalomaniac.tasks.calculator.validators.NumberValidator;

import java.util.ArrayList;
import java.util.List;

// Калькулятор выполняет операции с двумя арабскими или римскими числами
// на вход подаются числа от 1 до 10 включительно, на выходе результат
// в виде арабского или римского числа.
public class Calculator {
    // Выполняет вычисления записанные в строке
    public static String calculateFromString(String input){

        NumberValidator numberValidator = new ArabicAndRomanValidator();
        OperandsExtractor operandsExtractor=null;
        NumberConverter numberConverter=null;
        List<String> operands = new ArrayList<>();

        // Определяем тип чисел и проверяем корректность входных данных
        NumberType numberType = numberValidator.checkNumbers(input);

        // Выбираем режим работы с арабскими или римским числами в зависимости от типа чисел.
        // Если тип числа установить не удалось, значит формат входных данных не соответствует
        // заданным условиям - выбрасываем исключение.
        switch (numberType){
            case ARABIC:
                operandsExtractor=new ArabicOperandsExtractor();
                numberConverter = new ArabicNumberConverter();

                break;
            case ROMAN:
                operandsExtractor=new RomanOperandsExtractor();
                numberConverter = new RomanNumberConverter();
                break;
            default:
                throw new RuntimeException("Некорректный ввод.");
        }

        // Извлекаем операнды из строки
        operands=operandsExtractor.extractOperands(input);

        // Если количество операнд по каким-то причинам не равно 3 то выбрасываем исключение
        if(operands.size()!= 3)
            throw new RuntimeException("Некорректный ввод.");

        // Получаем операнды и оператор
        int op1 = numberConverter.convertFromString(operands.get(0));
        int op2 = numberConverter.convertFromString(operands.get(2));

        if(!numberValidator.isOperandInRange(op1)||!numberValidator.isOperandInRange(op2))
            throw new RuntimeException("Введены числа за пределами допустимого диапазона.");

        OperationType operation=OperationType.getType(operands.get(1).charAt(0));

        // Получаем результат вычисления
        int result = calculate(op1, op2, operation);

        // Возвращаем результат вычисления с конвертированием в исходный формат записи чисел
        return numberConverter.convertFromInt(result);
    }

    // Выполняет арифметические операции в числовом формате и возвращает результат типа int
    // Если задан тип операции не предусмотренный условиями задачи то выбрасываем исключение
    private static int calculate(int a,int b,OperationType operationType){
        switch (operationType){
            case PLUS:
                return a+b;
            case MINUS:
                return a-b;
            case MULTIPLY:
                return a*b;
            case DIVISION:
                return a/b;
            default:
                throw new RuntimeException("Не допустимый тип операции.");
        }
    }
}
