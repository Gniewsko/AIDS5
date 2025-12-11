package pl.edu.pw.ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManagement 
{
    public byte[] readFile(String path) throws IOException 
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

    public void saveToFile(String path, byte[] data) throws IOException
    {
        File file = new File(path);

        try(FileOutputStream fos = new FileOutputStream(file))
        {
            fos.write(data);
        }
        catch(IOException e)
        {
            throw new IOException("Failed to write data to file: " + path, e);
        }
    }
}
