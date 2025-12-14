package pl.edu.pw.ee.Structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class NodeTest 
{
    @Test
    public void testCompareTo() 
    {
        Node n1 = new Node(1, 10);
        Node n2 = new Node(2, 5);
        Node n3 = new Node(3, 10);

        assertTrue(n2.compareTo(n1) < 0);
        
        assertTrue(n1.compareTo(n2) > 0);

        assertEquals(0, n1.compareTo(n3));
    }

    @Test
    public void testInternalNodeCreation()
    {
        Node left = new Node('a', 5);
        Node right = new Node('b', 10);
        Node parent = new Node(left, right);

        assertEquals(15, parent.getOccurrence());
        assertEquals(false, parent.isLeaf());
        assertEquals(-1, parent.getSymbol());
    }
}