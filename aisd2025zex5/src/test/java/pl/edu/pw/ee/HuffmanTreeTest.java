package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

import pl.edu.pw.ee.Structures.Node;

public class HuffmanTreeTest 
{
    @Test
    public void testBuildTreeNormal() 
    {
        int[] counts = new int[256];
        counts['A'] = 5;
        counts['B'] = 10;
        
        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildTree(counts);
        
        assertNotNull(root);
        assertEquals(15, root.getOccurrence());
    }

    @Test
    public void testBuildTreeSingleCharacter() 
    {
        int[] counts = new int[256];
        counts['A'] = 10;

        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildTree(counts);

        assertNotNull(root);
        assertEquals(false, root.isLeaf()); 
        assertEquals(10, root.getOccurrence());
        
        String[] codes = huffmanTree.getCodes(root, 1);
        assertNotNull(codes['A']);
    }

    @Test
    public void testGetCodes()
    {
        int[] counts = new int[256];
        counts['A'] = 1;
        counts['B'] = 2;

        HuffmanTree huffmanTree = new HuffmanTree();
        Node root = huffmanTree.buildTree(counts);
        
        String[] codes = huffmanTree.getCodes(root, 1);
        
        assertEquals(256, codes.length);
        assertNotNull(codes['A']);
        assertNotNull(codes['B']);
        assertNull(codes['C']);
    }
}