package pl.edu.pw.ee.Structures;

import java.util.ArrayList;
import java.util.List;

public class MapOfPairs //klasa napisana czysto dla przejrzystości (dzięki temu łatwiej mi się rozumowało co dalej muszę pisać w projekcie)
{
    public List<Pair> pairs = new ArrayList<>();
    
    public void add(Pair pair)
    {
        pairs.add(pair);
    }

    public int size()
    {
        return pairs.size();
    }
}
