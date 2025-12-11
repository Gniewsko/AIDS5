package pl.edu.pw.ee;
public class Node implements Comparable<Node> 
{
    
    private final int symbol;
    private final int frequency;
    private final Node left;
    private final Node right;


    public Node(int symbol, int frequency) //liść
    {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public Node(Node left, Node right) //nie liść
    {
        this.symbol = -1;
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    public int getSymbol() 
    { 
        return symbol; 
    }
    
    public int getFrequency() 
    { 
        return frequency; 
    }

    public Node getLeft() 
    { 
        return left; 
    }
    
    public Node getRight() 
    { 
        return right; 
    }

    public boolean isLeaf() 
    {
        return left == null && right == null;
    }

    @Override
    public int compareTo(Node other) // x<0 - wyższy priorytet (mniej wystąpień), x>0 - niższy priorytet (więcej wystąpień)
    {
        return this.frequency - other.frequency;
    }
}
