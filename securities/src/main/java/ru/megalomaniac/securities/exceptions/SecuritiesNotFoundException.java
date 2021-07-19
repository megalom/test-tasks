package ru.megalomaniac.securities.exceptions;

public class SecuritiesNotFoundException extends RuntimeException{
    // Parameterless Constructor
    public SecuritiesNotFoundException() {}

    // Constructor that accepts a message
    public SecuritiesNotFoundException(String message)
    {
        super(message);
    }
}
