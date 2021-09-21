package com.exhibitions.first;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleTest {
    @Test
    public void test() {
        int x = 2;
        int y = 23;

        Assertions.assertEquals(46, x * y);
        Assertions.assertEquals(25, x + y);
    }

   /* @Test(expected = ArithmeticException.class)
    public void error() {
        int i = 0;
        int j = 1 / i;
    }*/
}
