package ru.megalomaniac.tasks.calculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void calculateFromString_Arabic() {
        String input="1+2";
        String result="3";
        assertEquals(result,Calculator.calculateFromString(input));
    }

    @Test
    public void calculateFromString_Roman() {
        String input="V+X";
        String result="XV";
        assertEquals(result,Calculator.calculateFromString(input));
    }

    @Test(expected = RuntimeException.class)
    public void calculateFromString_ThrowsIncorrectInputIfArabicRoman() {
        String input="1+V";
        String result="3";
        Calculator.calculateFromString(input);
    }

    @Test(expected = RuntimeException.class)
    public void calculateFromString_ThrowsIncorrectInputIfEmptyString() {
        String input="";
        String result="3";
        Calculator.calculateFromString(input);
    }

    @Test(expected = RuntimeException.class)
    public void calculateFromString_ThrowsIncorrectInputIfZeroOperand() {
        String input="0+5";
        String result="3";
        Calculator.calculateFromString(input);
    }

    @Test(expected = RuntimeException.class)
    public void calculateFromString_ThrowsIncorrectInputIfElevenOperand() {
        String input="V+XI";
        String result="3";
        Calculator.calculateFromString(input);
    }
}