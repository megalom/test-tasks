package ru.megalomaniac.tasks.calculator.extractors;

import org.junit.Before;
import org.junit.Test;
import ru.megalomaniac.tasks.calculator.converters.RomanNumberConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RomanOperandsExtractorTest {

    RomanOperandsExtractor romanOperandsExtractor;
    List<String> correctOperands;
    String input;
    @Before
    public void init(){
        romanOperandsExtractor=new RomanOperandsExtractor();
        correctOperands=new ArrayList<>();
        correctOperands.add("VI");
        correctOperands.add("-");
        correctOperands.add("V");
        input="VI-V";
    }
    @Test
    public void extractOperands() {
        assertTrue(correctOperands.equals(romanOperandsExtractor.extractOperands(input)));
    }
}