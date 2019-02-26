import java.util.Set;
import java.util.HashMap;
import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Mansion escape" game. 
 * "Mansion escape" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes and Mihail Bratanov
 * @version 1.0
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    // stores exits of this room.
    private HashMap<String,Integer>items;
    

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items=new HashMap<>();

        
       
    }
 
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    public void setItem(String item,int weight){
        items.put(item,weight);
    }
   
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    } 
    public String returnExitString(){
        return getExitString();
    }
    /**
     * Return a description of the items in the room in the form:
     *     
     *     Items: apple, book..
     * @return A long description of this room
     */
    public String printItems()
    {
        return "The items in this room are:  " + getItemString();
    } 
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        
        for(String exit : keys) {
            returnString += " " + exit;
        }
        if(returnString.equals(" ")){
             returnString="?????";
             return returnString;
        }
        return returnString;
    }
     /**
     * Return a string describing the room's items, for example
     * "Items: apple book".
     * @return Details of the room's exits.
     */
    private String getItemString()
    {
        String returnString = " ";
        Set<String> keys = items.keySet();
        for(String item : keys) {
            returnString += " " + item;
        }
        return returnString;
    }
    
   public int getItemWeight(String item){
       return items.get(item);
    }
    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
   
}

