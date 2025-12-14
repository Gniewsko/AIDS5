package pl.edu.pw.ee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CompressionAndDecompressionTest 
{
    @Test
    public void testFullFlowSimpleText() throws IOException 
    {
        String content = "Ala ma kota i kompresje";
        runTest(content.getBytes(), 1);
    }

    @Test
    public void testFullFlowSingleChar() throws IOException 
    {
        String content = "AAAAAA";
        runTest(content.getBytes(), 1);
    }

    @Test
    public void testFullFlowWordLength2() throws IOException 
    {
        String content = "ABABABAB12";
        runTest(content.getBytes(), 2);
    }


    @Test(expected = IOException.class)
    public void testCompressNonExistentFile() throws IOException 
    {
        Compression compression = new Compression();
        compression.compressAndSave(1, "nie_istnieje.txt", "wynik.txt");
    }

    @Test(expected = IOException.class)
    public void testCompressDirectory() throws IOException 
    {
        Compression compression = new Compression();
        File dir = new File("."); 
        compression.compressAndSave(1, dir.getAbsolutePath(), "wynik.txt");
    }


    @Test(expected = IOException.class)
    public void testDecompressNonExistentFile() throws IOException 
    {
        Decompression decompression = new Decompression();
        decompression.decompress("nie_istnieje.txt", "wynik.txt", 1);
    }
    
    @Test(expected = IOException.class)
    public void testDecompressEmptyFile() throws IOException
    {
        File empty = File.createTempFile("empty", ".txt");
        empty.deleteOnExit();
        
        Decompression decompression = new Decompression();
        decompression.decompress(empty.getAbsolutePath(), "wynik.txt", 1);
    }

    @Test
    public void testEmptyFile() throws IOException 
    {
        File input = File.createTempFile("empty", ".txt");
        File compressed = File.createTempFile("empty", ".txt");
        File decompressed = File.createTempFile("empty_out", ".txt");
        input.deleteOnExit(); compressed.deleteOnExit(); decompressed.deleteOnExit();

        Compression comp = new Compression();
        comp.compressAndSave(1, input.getAbsolutePath(), compressed.getAbsolutePath());

        Decompression decomp = new Decompression();
        decomp.decompress(compressed.getAbsolutePath(), decompressed.getAbsolutePath(), 1);

        byte[] result = Files.readAllBytes(decompressed.toPath());
        assertEquals(0, result.length);
    }

    @Test
    public void testAllPossibleBytes() throws IOException 
    {
        byte[] allBytes = new byte[256];
        for (int i = 0; i < 256; i++) {
            allBytes[i] = (byte) i;
        }

        runTest(allBytes, 1);
    }

    @Test
    public void testTrickyBytes() throws IOException 
    {

        byte[] tricky = {0, (byte) 0xFF, (byte) 0xFF, 0, 127, (byte) -128};
        
        runTest(tricky, 1);
    }

    @Test
    public void testTruncationForWordLength2() throws IOException 
    {
        byte[] inputData = {'A', 'B', 'C'};
        
        File input = File.createTempFile("trunc", ".txt");
        File compressed = File.createTempFile("trunc", ".txt");
        File decompressed = File.createTempFile("trunc_out", ".txt");
        input.deleteOnExit(); compressed.deleteOnExit(); decompressed.deleteOnExit();

        Files.write(input.toPath(), inputData);

        Compression comp = new Compression();
        comp.compressAndSave(2, input.getAbsolutePath(), compressed.getAbsolutePath());

        Decompression decomp = new Decompression();
        decomp.decompress(compressed.getAbsolutePath(), decompressed.getAbsolutePath(), 2);

        byte[] result = Files.readAllBytes(decompressed.toPath());

        byte[] expected = {'A', 'B'};
        assertArrayEquals(expected, result);
    }

    private void runTest(byte[] content, int wordLength) throws IOException
    {
        File input = File.createTempFile("test_in", ".txt");
        File compressed = File.createTempFile("test_mid", ".txt");
        File decoded = File.createTempFile("test_out", ".txt");

        input.deleteOnExit();
        compressed.deleteOnExit();
        decoded.deleteOnExit();

        Files.write(input.toPath(), content);

        Compression compression = new Compression();
        compression.compressAndSave(wordLength, input.getAbsolutePath(), compressed.getAbsolutePath());


        assertTrue(compressed.exists());
        assertTrue(compressed.length() > 0);

        Decompression decompression = new Decompression();
        decompression.decompress(compressed.getAbsolutePath(), decoded.getAbsolutePath(), wordLength);

        byte[] original = Files.readAllBytes(input.toPath());
        byte[] restored = Files.readAllBytes(decoded.toPath());

        assertArrayEquals(original, restored);
    }
}
