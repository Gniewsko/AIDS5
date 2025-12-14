package pl.edu.pw.ee.Structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class PriorityQueueTest 
{
    @Test
    public void testPollOrder() 
    {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(new Node(1, 20));
        priorityQueue.add(new Node(2, 5));
        priorityQueue.add(new Node(3, 10));

        assertEquals(5, priorityQueue.poll().getOccurrence());
        assertEquals(10, priorityQueue.poll().getOccurrence());
        assertEquals(20, priorityQueue.poll().getOccurrence());
    }

    @Test
    public void testEmptyQueue()
    {
        PriorityQueue priorityQueue = new PriorityQueue();
        assertEquals(0, priorityQueue.size());
        assertNull(priorityQueue.poll());
    }

    @Test
    public void testSize()
    {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.add(new Node(1, 1));
        priorityQueue.add(new Node(2, 1));
        assertEquals(2, priorityQueue.size());
        
        priorityQueue.poll();
        assertEquals(1, priorityQueue.size());
    }
}