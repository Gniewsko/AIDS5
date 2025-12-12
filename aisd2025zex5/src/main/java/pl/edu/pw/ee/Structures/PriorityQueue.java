package pl.edu.pw.ee.Structures;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue
{
    private final List<Node> heap = new ArrayList<>();
    //lewe dziecko = 2 * i + 1
    //prawe dziecko = 2 * i + 2
    //rodzic = (i - 1) / 2

    public void add(Node node)
    {
        heap.add(node);
        goUp(heap.size() - 1);
    }

    public Node poll()
    {
        if(heap.isEmpty())
        {
            return null;
        }

        Node result = heap.get(0);

        Node last = heap.remove(heap.size() - 1);

        if(!heap.isEmpty())
        {
            heap.set(0, last);
            goDown(0);
        }

        return result;
    }

    public int size()
    {
        return heap.size();
    }

    private void goUp(int index)
    {
        while(index > 0)
        {
            int parentIndex = (index - 1) / 2;
            Node current = heap.get(index);
            Node parent = heap.get(parentIndex);

            if(current.compareTo(parent) < 0)
            {
                swap(index, parentIndex);
                index = parentIndex;
            }
            else
            {
                break;
            }
        }
    }

    private void goDown(int index)
    {
        int size = heap.size();

        while(true)
        {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) 
            {
                smallest = leftChild;
            }

            if (rightChild < size && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) 
            {
                smallest = rightChild;
            }

            if (smallest != index) 
            {
                swap(index, smallest);
                index = smallest;
            } 
            else 
            {
                break;
            }
        }
    }

    private void swap(int i, int j)
    {
        Node tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }
}