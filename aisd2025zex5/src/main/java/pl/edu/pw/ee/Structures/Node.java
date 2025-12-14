package pl.edu.pw.ee.Structures;
public class Node implements Comparable<Node> 
{
    
    private final int symbol;
    private final int occurrence;
    private final Node left;
    private final Node right;


    public Node(int symbol, int occurrence) //liść
    {
        this.symbol = symbol;
        this.occurrence = occurrence;
        this.left = null;
        this.right = null;
    }

    public Node(Node left, Node right) //nie liść
    {
        this.symbol = -1;
        this.left = left;
        this.right = right;

        int rightOccurrence;

        if(right == null) //jeżeli tworzymy sztucznego rodzica (plik będzie z jednym znakiem, np. "AAAAAAA") to będzie on lewym dzieckiem sztucznego rodzica
        {
            rightOccurrence = 0;
        }
        else
        {
            rightOccurrence = right.occurrence;
        }

        this.occurrence = left.occurrence + rightOccurrence;
    }

    public int getSymbol() 
    { 
        return symbol; 
    }
    
    public int getOccurrence()  
    { 
        return occurrence; 
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
        return this.occurrence - other.occurrence;
    }
}
