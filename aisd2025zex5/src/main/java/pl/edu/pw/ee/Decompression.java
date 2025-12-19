package pl.edu.pw.ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import pl.edu.pw.ee.Structures.Node;

public class Decompression 
{
    private int currentBitVal = 0;
    private int currentBitIndex = -1; // -1 == trzeba wczytać nowy bajt

    public void decompress(String inputPath, String outputPath, int wordLength) throws IOException 
    {
        File fileInput = new File(inputPath);
           
        if (!fileInput.exists()) 
        {
            throw new IOException("Input file does not exist: " + inputPath);
        }

        if (fileInput.isDirectory()) 
        {
            throw new IOException("Input Path is a directory, not a file: " + inputPath);
        }

        long fileSize = fileInput.length();
           
        if (fileSize > Integer.MAX_VALUE) 
        {
            throw new IOException("File is too large (above 2GB) to be read with one array!");
        }

        File fileOutput = new File(outputPath);

        if(!fileOutput.exists())
        {
            throw new IOException("Output file does not exist: " + outputPath);
        }

        if (fileOutput.isDirectory()) 
        {
            throw new IOException("Output Path is a directory, not a file: " + outputPath);
        }
            
        try (FileInputStream fis = new FileInputStream(fileInput)) 
        {
            int fileWordLength = fis.read(); 
        
            if (fileWordLength == -1) 
            {
                throw new IOException("File is empty or corrupted.");
            }

            if (wordLength != fileWordLength) 
            {
                System.out.println("Warning: User set -l " + wordLength + ", but file was compressed with -l " + fileWordLength + ". Program wont work properly!");
            }
            
            currentBitIndex = -1;

            int totalSymbols = readInt(fis);

            if (totalSymbols == 0)
            { 
                return;
            }

            Node root = readTree(fis, wordLength);

            currentBitIndex = -1;

            readBytes(fis, root, totalSymbols, wordLength, outputPath);
        }
        
        
    }

    private int readInt(FileInputStream fis) throws IOException 
    {
        int b1 = fis.read();
        int b2 = fis.read();
        int b3 = fis.read();
        int b4 = fis.read();

        if ((b1 | b2 | b3 | b4) < 0) 
        {
            throw new IOException("EOF reached while reading integer!");
        }

        return ((b1 << 24) | (b2 << 16) | (b3 << 8) | b4);
    }

    /*
    private int readSymbol(FileInputStream fis, int wordLength) throws IOException 
    {
        int symbol = 0;
        
        for (int i = 0; i < wordLength; i++) 
        {
            int b = fis.read();
            if (b == -1) 
            {
                throw new IOException("EOF reached while reading symbol!");
            }

            symbol = (symbol << 8) | b;
        }
        
        return symbol;
    }
*/

    private int readBit(FileInputStream fis) throws IOException 
    {
        if (currentBitIndex < 0) 
        {
            currentBitVal = fis.read();
            if (currentBitVal == -1) 
            {
                throw new IOException("Unexpected EOF!");
            }
            currentBitIndex = 7;
        }
        
        int bit = (currentBitVal >> currentBitIndex) & 1;
        currentBitIndex--;
        return bit;
    }

    private int readNBits(FileInputStream fis, int n) throws IOException
    {
        int value = 0;
        for (int i = 0; i < n; i++) 
        {
            value = (value << 1) | readBit(fis);
        }
        return value;
    }

    private Node readTree(FileInputStream fis, int wordLength) throws IOException 
    {
        int bit = readBit(fis);
        
        if (bit == 1) //liść
        {
            int symbol = readNBits(fis, wordLength * 8);
            return new Node(symbol, 0);
        } 
        else //węzeł wewnętrzny
        {
            Node left = readTree(fis, wordLength);
            Node right = readTree(fis, wordLength);
            return new Node(left, right);
        }
    }

    private void readBytes(FileInputStream fis, Node currentNode, int totalSymbols, int wordLength, String outputPath) throws IOException
    {
        int symbolsDecoded = 0;
        Node root = currentNode;

        try(FileOutputStream fos = new FileOutputStream(outputPath))
        {
            while(symbolsDecoded < totalSymbols)
            {
                int bit = readBit(fis);

                if(bit == 1)
                {
                    currentNode = currentNode.getLeft();
                }
                else
                {
                    currentNode = currentNode.getRight();
                }

                if(currentNode.isLeaf())
                {
                    saveSymbol(fos, currentNode.getSymbol(), wordLength);
                    symbolsDecoded++;
                    currentNode = root;
                }
            }
        }
    }

    private void saveSymbol(FileOutputStream fos, int symbol, int wordLength) throws IOException
    {
        for(int i = wordLength - 1; i >= 0; i--)
        {
            int lengthOfByte = 8 * i; //bo dla wordLength == 2, nasze słowo ma 16 bitów zamias 8 jak dla jednego bajta
            int singleByte = (symbol >> lengthOfByte) & 0xFF;

            fos.write(singleByte);
        }
    }
}
