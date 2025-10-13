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
    @Test
    public void subtractTest() {
        Calculator c = new Calculator();
        assertEquals(5, c.subtract(7,2));
    }
    @Test
    public void  multiplyTest() {
        Calculator c = new Calculator();
        assertEquals(9, c.multiply(3, 3));
    }
}