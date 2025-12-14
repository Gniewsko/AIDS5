package pl.edu.pw.ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.ee.Structures.Node;
import pl.edu.pw.ee.Structures.Pair;

public class Compression 
{
    private int currentByte = 0;
    private int numBitsFilled = 0;

    private byte[] readFile(String path) throws IOException 
    {
        File file = new File(path);
           
        if (!file.exists()) 
        {
            throw new IOException("Input file does not exist: " + path);
        }

        if (file.isDirectory()) 
        {
            throw new IOException("Input path is a directory, not a file: " + path);
        }

        long fileSize = file.length();
           
        if (fileSize > Integer.MAX_VALUE) 
        {
            throw new IOException("File is too large (above 2GB) to be read with one array!");
        }
            
        byte[] data = new byte[(int) fileSize];

        if (fileSize == 0) 
        {
            return new byte[0];
        }
            
        try (FileInputStream fis = new FileInputStream(file)) 
        {
            int bytesRead = fis.read(data);
                
            if (bytesRead != fileSize) 
            {
                throw new IOException("Failed to read the entire file! Expected " + fileSize + " bytes, got " + bytesRead);
            }
        }
            
        return data;
    }

    public void compressAndSave(int wordLength, String inputPath, String outputPath) throws IOException
    {
        byte[] data = readFile(inputPath);

        int[] occurrences = CharCounting.count(wordLength, data);

        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildTree(occurrences);

        String[] codes = huffmanTree.getCodes(root, wordLength);


        try(FileOutputStream fos = new FileOutputStream(outputPath))
        {
            currentByte = 0;
            numBitsFilled = 0;

            int totalSymbols = data.length / wordLength;

            writeHeader(fos, wordLength, occurrences, totalSymbols);

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

    private void writeSymbol(FileOutputStream fos, int value, int length) throws IOException 
    {
        for (int i = length - 1; i >= 0; i--) 
        {
            fos.write((value >> (8 * i)) & 0xFF);
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
            writeSymbol(fos, pair.getCharacter(), wordLength);
            writeInt(fos, pair.getOccurrence());
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
