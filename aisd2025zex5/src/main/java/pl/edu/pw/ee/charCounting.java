package pl.edu.pw.ee;

public class CharCounting 
{
    public int[] count(int wordLength, byte[] data)
    {
        int arraySize = (int) Math.pow(256, wordLength);
        int[] occurrences = new int[arraySize];

        for(int i = 0; i <= data.length - wordLength; i += wordLength)
        {
            int index = 0;
            
            for(int j = 0; j < wordLength; j++)
            {
                index = (index << 8) | (data[i + j] & 0xFF);
            }

            occurrences[index]++;
        }

        return occurrences;
    }
}