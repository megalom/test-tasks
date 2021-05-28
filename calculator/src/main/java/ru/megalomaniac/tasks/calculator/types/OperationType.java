package ru.megalomaniac.tasks.calculator.types;

// Операции с числами, поддерживаемые калькулятором
public enum OperationType {
    PLUS('+'),MINUS('-'),DIVISION('/'),MULTIPLY('*'),UNKNOWN('?');

    private char sign;

    OperationType(char sign){
        this.sign=sign;
    }

    public char getSign(){
        return this.sign;
    }
    public static OperationType getType(char operation){
        switch (operation){
            case '+':
                return PLUS;
            case '-':
                return MINUS;
            case '*':
                return MULTIPLY;
            case '/':
                return DIVISION;
            default:
                return UNKNOWN;
        }
    }
}
