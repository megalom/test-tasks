package ru.megalomaniac.tasks.calculator.validators;

import org.junit.Before;
import org.junit.Test;
import ru.megalomaniac.tasks.calculator.types.NumberType;

import static org.junit.Assert.*;

public class ArabicAndRomanValidatorTest {
    ArabicAndRomanValidator arabicAndRomanValidator;
    @Before
    public void init(){
        arabicAndRomanValidator=new ArabicAndRomanValidator();
    }
    @Test
    public void checkNumbers_Arabic() {
        String input="1+3";
        NumberType numberType = NumberType.ARABIC;
        assertEquals(numberType,arabicAndRomanValidator.checkNumbers(input));
    }

    @Test
    public void checkNumbers_Roman() {
        String input="V+VI";
        NumberType numberType = NumberType.ROMAN;
        assertEquals(numberType,arabicAndRomanValidator.checkNumbers(input));
    }

    @Test
    public void checkNumbers_Unknown() {
        String input="1+V";
        NumberType numberType = NumberType.UNKNOWN;
        assertEquals(numberType,arabicAndRomanValidator.checkNumbers(input));
    }
}