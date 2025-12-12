package pl.edu.pw.ee;

import pl.edu.pw.ee.Structures.Node;
import pl.edu.pw.ee.Structures.PriorityQueue;

public class HuffmanTree 
{
        public Node buildTree(int[] occurrences)
        {
            PriorityQueue queue = new PriorityQueue();

            for(int i = 0; i < occurrences.length; i++)
            {
                if(occurrences[i] > 0)
                {
                    Node leaf = new Node(i, occurrences[i]);
                    queue.add(leaf);
                }
            }

            if(queue.size() == 0)
            {
                return null;
            }

            if(queue.size() == 1)
            {
                Node leaf = queue.poll();
                Node artificialParent = new Node(leaf, null);
                return artificialParent;
            }

            while(queue.size() > 1)
            {
                Node left = queue.poll();
                Node right = queue.poll();

                Node parent = new Node(left, right);

                queue.add(parent);
            }

            return queue.poll();
        }

        public String[] getCodes(Node root, int arraySize) 
        {
            String[] codes = new String[arraySize];

            if (root != null) 
            {
                generateRecursive(root, "", codes);
            }

            return codes;
        }

        private void generateRecursive(Node node, String currentCode, String[] codes) 
        {
            if (node == null) 
                {
                return;
            }

            if (node.isLeaf()) 
            {
                codes[node.getSymbol()] = currentCode;
                return;
            }
            
            generateRecursive(node.getLeft(), currentCode + "1", codes);
            generateRecursive(node.getRight(), currentCode + "0", codes);
        }

}
