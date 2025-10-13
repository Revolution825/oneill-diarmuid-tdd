package test.java;

import main.java.Calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    @Test
    public void addTest() {
        Calculator c = new Calculator();
        assertEquals(5, c.add(2, 3));
    }
    @Test
    public void  multiplyTest() {
        Calculator c = new Calculator();
        assertEquals(9, c.multiply(3, 3));
    }

    @Test
    public void divideTest() {
        Calculator c = new Calculator();
        assertEquals(3, c.divide(3, 9));
    }

    @Test
    public void divideByZeroTest() {
        Calculator c = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> c.divide(42, 0) );
    }

    @ParameterizedTest
    @CsvSource({
      "5, 7, 2",
      "0, 0, 0",
      "-1, 0, 1",
    })
    public void parameterizedSubtractTest(int expected, int a, int b) {
        Calculator c = new Calculator();
        assertEquals(expected, c.subtract(a, b));
    }
}