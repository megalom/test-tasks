package ru.megalomaniac.tasks.calculator.converters;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArabicNumberConverterTest {

    @Test
    public void convertFromInt() {
        ArabicNumberConverter converter= new ArabicNumberConverter();
        assertEquals("9",converter.convertFromInt(9));
    }

    @Test
    public void convertFromString() {
        ArabicNumberConverter converter= new ArabicNumberConverter();
        assertEquals(5,converter.convertFromString("5"));
    }
}