
/**
 * NPC class
 * This class is part of the "Mansion escape" game. 
 * "Mansion escape" is a very simple, text based adventure game.  
 * 
 * This class creates npc-objects. They have descriptions.
 * @author  Michael Kölling and David J. Barnes and Mihail Bratanov
 * @version 1.0
 */
public class NPC
{
    //description of the npc
    private String description;
    private boolean isAlive;

    /**
     * Constructor for objects of class NPC
     */
    public NPC(String description)
    {
        // initialise description
        this.description=description;
        isAlive = true;
    }

    /**
     * Gets npc's description and returns it to other classes to be used further.
     */
    public String getDescription()
    {
        return description;
    }
     /**
     * Sets npc to either dead or alive.
     */
    public void setAlive(boolean aliveChange)
    {
        isAlive = aliveChange;
        
    }
     /**
     * Returns npc's state of life :D 
     */
     public boolean getAlive()
    {
        return isAlive;
    }
}
