package ru.megalomaniac.tasks.calculator.converters;

// Конвертер для работы с римскими числами
public class RomanNumberConverter implements NumberConverter{
    String[] units = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
    String[] tens = {"X","XX","XXX","XL","L","LX","LXX","LXXX","XC"};
    String[] hundreds ={"C","CC","CCC","CD","D","DC","DCC","DCCC","CM"};

    public RomanNumberConverter(){
    }

    // Возвращает строку с римским числом
    @Override
    public String convertFromInt(int number) {
        StringBuilder result = new StringBuilder();

        if(number==0) result.append(0);
        if(number<0) result.append("В римской системе счисления отсутствуют отрицательные числа.");

        if(number%10>0) {
            result.insert(0,units[number%10-1]);
        }
        number = number / 10;

        if(number%10>0) {
            result.insert(0,tens[number%10-1]);
            number = number / 10;
        }
        number = number / 10;

        if(number%10>0) {
            result.insert(0,hundreds[number%10-1]);
            number = number / 10;
        }
        return result.toString();
    }

    // Возвращает число типа int из строки с римским числом
    @Override
    public int convertFromString(String number) {
        int result=-1;
        for(int i=0;i< units.length;i++){
            if(units[i].equals(number)) {
                result = i + 1;
                break;
            }
        }
        if (result==-1)
            throw new RuntimeException("Введенное число не соответствует условиям задачи.");
        return result;
    }
}
