package pl.edu.pw.ee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.ee.Structures.Pair;

public class Compression 
{
    private int currentByte = 0;
    private int numBitsFilled = 0;

    public void compressAndSave(byte[] data, int wordLength, String[] codes, String path, int[] occurrances) throws IOException
    {
        try(FileOutputStream fos = new FileOutputStream(path))
        {
            currentByte = 0;
            numBitsFilled = 0;

            int totalSymbols = data.length / wordLength;

            writeHeader(fos, wordLength, occurrances, totalSymbols);

            for(int i = 0; i <= data.length - wordLength; i += wordLength)
            {
                int index = 0;
                
                for(int j = 0; j < wordLength; j++)
                {
                    index = (index << 8) | (data[i + j] & 0xFF);
                }

                String code = codes[index];

                if(code == null)
                {
                    throw new IOException("No Huffman code found for symbol" + index);
                }

                separateToBytes(code, fos);
            }

            restOfBits(fos);
        }
    }

    private void separateToBytes(String code, FileOutputStream fos) throws IOException
    {
        for(char bit : code.toCharArray())
        {
            if(bit =='1')
            {
                currentByte = (currentByte << 1) | 1;
            }
            else
            {
                currentByte = (currentByte << 1);
            }

            numBitsFilled++;

            if(numBitsFilled == 8)
            {
                fos.write(currentByte);
                currentByte = 0;
                numBitsFilled = 0;
            }
        }
    }

    private void restOfBits(FileOutputStream fos)  throws IOException
    {
        if(numBitsFilled > 0)
        {
            currentByte = (currentByte << (8 - numBitsFilled));
            fos.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    private void writeInt(FileOutputStream fos, int value) throws IOException 
    {
        fos.write((value >> 24) & 0xFF);
        fos.write((value >> 16) & 0xFF);
        fos.write((value >> 8) & 0xFF);
        fos.write(value & 0xFF);
    }

    private void writeHeader(FileOutputStream fos, int wordLength, int[] occurrences, int totalSymbols) throws IOException 
    {
        List<Pair> pairsList = convertToPairs(occurrences);

        fos.write(wordLength);

        writeInt(fos, totalSymbols);
        writeInt(fos, pairsList.size());

        for (Pair pair : pairsList) 
        {
            writeInt(fos, pair.getCharacter());
            writeInt(fos, pair.getOccurance());
        }
    }

    private List<Pair> convertToPairs(int[] occurrences) 
    {
        List<Pair> pairs = new ArrayList<>();
    
        for (int i = 0; i < occurrences.length; i++) 
        {
            if (occurrences[i] > 0) 
            {
                pairs.add(new Pair(i, occurrences[i]));
            }
        }

        return pairs;
    }
}
