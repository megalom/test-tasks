package ru.megalomaniac.tasks.calculator.extractors;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArabicOperandsExtractorTest {
    ArabicOperandsExtractor arabicOperandsExtractor;
    List<String> correctOperands;
    String input;
    @Before
    public void init(){
        arabicOperandsExtractor=new ArabicOperandsExtractor();
        correctOperands=new ArrayList<>();
        correctOperands.add("1");
        correctOperands.add("+");
        correctOperands.add("2");
        input="1+2";
    }
    @Test
    public void extractOperands() {
        assertTrue(correctOperands.equals(arabicOperandsExtractor.extractOperands(input)));
    }
}