package test.java;

import main.java.Calculator;

import org.junit.Test;

class CalculatorTest {
    @Test
    void addTest(int a, int b) {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
    }
}