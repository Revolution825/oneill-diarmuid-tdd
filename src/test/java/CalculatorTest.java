package test.java;

import main.java.Calculator;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    public void addTest() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
    }
}