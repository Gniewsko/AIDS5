package pl.edu.pw.ee;

import java.io.IOException;

public class App 
{
    public static void main( String[] args )
    {
        String mode = null;
        String inputPath = null;
        String outputPath = null;
        int wordLength = 1;

        for(int i = 0; i < args.length; i++)
        {
            switch(args[i])
            {
                case "-m":
                    if(i + 1 < args.length)
                    {
                        i++;
                        mode = args[i];
                    }
                    break;

                case "-s":
                    if(i + 1 < args.length)
                    {
                        i++;
                        inputPath = args[i];
                    }
                    break;

                case "-d":
                    if(i + 1 < args.length)
                    {
                        i++;
                        outputPath = args[i];
                    }
                    break;

                case "-l":
                    if(i + 1 < args.length)
                    {
                        i++;
                        try
                        {
                           wordLength = Integer.parseInt(args[i]);

                           if(wordLength >= 3)
                           {
                                System.out.println("Warning: Program may not work properly for wordLength > 2");
                           }
                        } 
                        catch (NumberFormatException e) 
                        {
                            System.out.println("Error: Argument -l must be in Integer format");
                            return;
                        }
                    }
                    break;

                default:
                    System.out.println("Warning: Wrong argument was used!");
                    break;
            }
        }

        if(mode == null || inputPath == null || outputPath == null)
        {
            System.out.println("Error: User put not enough/wrong arguments");
            System.out.println("Usage: java -jar huffman.jar -m <comp|decomp> -s <inputPath> -d <outputPath> -l <1|2>");
            System.out.println("-l <1|2> is not necessary");
            return;
        }

        if(mode.equals("comp")) 
        {
            Compression comp = new Compression();

            try
            {
                comp.compressAndSave(wordLength, inputPath, outputPath);
            }
            catch(IOException e)
            {
                System.out.println("Compression error: " + e.getMessage());
                return;
            }
        }
        else if(mode.equals("decomp"))
        {
            Decompression decomp = new Decompression();

            try
            {
                decomp.decompress(inputPath, outputPath, wordLength);
            }
            catch(IOException e)
            {
                System.out.println("Decompression error: " + e.getMessage());
                return;
            }
        }
        else
        {
            System.out.println("Error: any of acceptable modes were detected!");
            System.out.println("Modes available: comp (compression), decomp (decompression)");
            return;
        }
    }
}
