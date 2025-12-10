package pl.edu.pw.ee;

public class charCounting 
{
    public int[] count(int size, String data)
    {
        int[] occurrences = new int[65_535];
        char[] character = data.toCharArray();

        for(int i = 0; i < size; i++)
        {
            occurrences[character[i]]++;    
        }

        return occurrences;
    }
}