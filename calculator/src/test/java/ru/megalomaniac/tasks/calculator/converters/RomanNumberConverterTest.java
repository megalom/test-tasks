package ru.megalomaniac.tasks.calculator.converters;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RomanNumberConverterTest {
    RomanNumberConverter converter;
    @Before
    public void initObjects(){
        converter=new RomanNumberConverter();
    }

    @Test
    public void convertFromInt() {
        assertEquals("IX",converter.convertFromInt(9));
    }

    @Test
    public void convertFromString() {
        assertEquals(5,converter.convertFromString("V"));
    }

    @Test(expected = RuntimeException.class)
    public void convertFromString_throwRuntimeException(){
        converter.convertFromString("");
    }

    @Test
    public  void convertFromInt_BelowZero(){
        String result="В римской системе счисления отсутствуют отрицательные числа.";
        assertEquals(result,converter.convertFromInt(-5));
    }

}