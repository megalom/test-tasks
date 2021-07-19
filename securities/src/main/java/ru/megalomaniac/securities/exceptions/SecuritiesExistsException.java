package ru.megalomaniac.securities.exceptions;

public class SecuritiesExistsException extends RuntimeException{
    // Parameterless Constructor
    public SecuritiesExistsException() {}

    // Constructor that accepts a message
    public SecuritiesExistsException(String message)
    {
        super(message);
    }
}
