package pl.edu.pw.ee.Structures;

public class Pair 
{
    private final int character;
    private final int occurrence;
    
    public Pair(int character, int occurrence)
    {
        this.character = character;
        this.occurrence = occurrence;
    }

    public int getCharacter()
    {
        return character;
    }

    public int getOccurrence()
    {
        return occurrence;
    }
}
