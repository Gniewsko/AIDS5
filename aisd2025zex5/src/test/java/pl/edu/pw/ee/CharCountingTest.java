package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CharCountingTest 
{
    @Test
    public void testCountSingleByte() 
    {
        // "ABBA" -> A=65, B=66
        byte[] data = {65, 66, 66, 65}; 
        int wordLength = 1;

        int[] result = CharCounting.count(wordLength, data);

        assertEquals(2, result[65]); // A
        assertEquals(2, result[66]); // B
        assertEquals(0, result[67]); // C
    }

    @Test
    public void testCountDoubleByte() 
    {
        byte[] data = {0, 1, 0, 1}; 
        int wordLength = 2;

        int[] result = CharCounting.count(wordLength, data);

        assertEquals(2, result[1]);
        assertEquals(0, result[2]);
    }
}