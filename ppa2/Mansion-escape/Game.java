import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ArrayList;
/**
 *
 *  This class is the main class of the "Mansion escape" game. 
 *  "Mansion escape" is a classic escape game where the player is put in
 *  a situation where they need to complete a certain set of tasks to be able to escape.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes and Mihail Bratanov
 * @version 1.0
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Room> visitedRooms;//all the rooms the player has visited go here
    private ArrayList<String>inventory;//any picked up item would be addded here
    private int backStepCounter=0;
    private int storageSpace=30; //total storage space of the inventory
    private int currentStorageSpace=0;
    private int spaceLeft=0;
    private boolean hasKey1=false;
    private boolean hasKey2=false;
    private boolean hasKey3=false;
    private boolean hasSpare=false;
    private ArrayList<Room> roomsToBeRandomlyChosen;//arrayList of Room typed objects to be choosen randomly
    private  Room starterRoom, library, mainHall, diningRoom, stairwell,northWestStorageRoom,northEastStorageRoom, southWestBedroom, southEastBedroom, transporter, tower, dungeonGrounds;
    private NPC winnie;
    private NPC badWinnie;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {   

        roomsToBeRandomlyChosen=new ArrayList<Room>();
        createRooms();
        visitedRooms=new ArrayList<Room>();
        inventory=new ArrayList<String>();
        parser = new Parser();

    }

    /**
     * Create all the rooms and link their exits together.
     * Fill the rooms with items. Each item has a certain weight.
     * Adds non-playable characters as well. (npc's)
     */
    private void createRooms()
    {   
        //create npc
        winnie=new NPC("___________________________________________________________________________________\n| I AM A WINNIE.I AM NOT GOING TO HELP YOU AT ALL BUT I AM HERE FOR MORAL SUPPORT!|\n___________________________________________________________________________________\n");
        badWinnie=new NPC("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n| I AM A  BAD WINNIE.KILL ONE GOOD WINNIE AND I WILL GIVE YOU A SPARE PART OF THE KEY!|\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
        // create the rooms
        transporter=new Room("being transported. Type in go to confirm!");
        starterRoom = new Room("where you wake up. Take a look around...");
        library = new Room("in the library. You are not alone here. There is a Librarian. They tell that if you want to leave you need to answer the riddle correctly.\n It states:\n When is a door not a door? Your choices are: \n 1.When it's brown!\n 2.When it is made of air!.\n 3.When it is open!\n Insert reply and number from 1 to 3 to answer.");
        mainHall = new Room("in the main hall");
        diningRoom = new Room("in a dining room. There is a stranger sitting on the chair next to you. He is wearing a chef's hat. He is a chef! He tells you:\n Traveler if you want to escape this place I will give you a part of the key. But find and return me my recipe book! It is somewhere SOUTH of here! ");
        stairwell = new Room("in a stairwell");
        northWestStorageRoom=new Room("in the north-west storage room");
        northEastStorageRoom=new Room("in the north-east storage room");
        southWestBedroom=new Room("in the south-west bedroom");
        southEastBedroom=new Room("in the south-east bedroom");
        tower=new Room("in a mysterious tower.You suddenly hear a voice and turn around. There stands a wizzard just like the ones in Harry Potter! Only this one is missing his hat!\nHelp me find my hat traveler!\nIf you do I will provide you with all answers you need to escape this prison!" );
        dungeonGrounds=new Room("in a dark dungeon. You're not alone here. Your eyes slowly adjust to the darkness and you find out you're standing face to face with a scary monster!!!\n^^^^^\n<0.0>\n<vvv>\n<^^^>\nvvvvv");

        // initialise room exits

        starterRoom.setExit("north", mainHall);
        starterRoom.setExit("east", southEastBedroom);
        starterRoom.setExit("west", southWestBedroom);

        southEastBedroom.setExit("north-west",mainHall);
        southEastBedroom.setExit("west",starterRoom);

        southWestBedroom.setExit("north-east",mainHall);
        southWestBedroom.setExit("east",starterRoom);

        mainHall.setExit("north", diningRoom);
        mainHall.setExit("north-east", diningRoom);
        mainHall.setExit("east", library);
        mainHall.setExit("south-east", southEastBedroom);
        mainHall.setExit("south", starterRoom);
        mainHall.setExit("south-west", southWestBedroom);
        mainHall.setExit("west", stairwell);
        mainHall.setExit("north-west", northWestStorageRoom);

        diningRoom.setExit("south", mainHall);
        diningRoom.setExit("east", northEastStorageRoom);
        diningRoom.setExit("west", northWestStorageRoom);

        northEastStorageRoom.setExit("south-west",mainHall);
        northEastStorageRoom.setExit("west",diningRoom);

        northWestStorageRoom.setExit("south-east",mainHall);
        northWestStorageRoom.setExit("east",diningRoom);

        library.setExit("west" ,mainHall);
        library.setExit("east",transporter);

        stairwell.setExit("up",tower);
        stairwell.setExit("east",mainHall);
        stairwell.setExit("down",dungeonGrounds);

        tower.setExit("down",stairwell);

        dungeonGrounds.setExit("up",stairwell);  

        currentRoom = starterRoom;  // start game outside
        //fill rooms with items
        diningRoom.setItem("apple" , 5);
        diningRoom.setItem("candle" , 3);
        diningRoom.setItem("painting" , 8);
        diningRoom.setItem("chair" , 10);
        diningRoom.setItem("table",30);
        northWestStorageRoom.setItem("wizzardHat",10);
        northWestStorageRoom.setItem("meat",10);
        northWestStorageRoom.setItem("beans",2);
        northWestStorageRoom.setItem("eggplant",2);
        northWestStorageRoom.setItem("peppers",2);
        mainHall.setItem("piano",50);
        mainHall.setItem("painting",50);
        mainHall.setItem("lantern",20);
        southWestBedroom.setItem("recipebook",10);
        library.setItem("book",5);
        library.setItem("bookshelf",500);
        //prepare the rooms for the transporter
        roomsToBeRandomlyChosen.add(starterRoom);
        roomsToBeRandomlyChosen.add(library);
        roomsToBeRandomlyChosen.add(mainHall);
        roomsToBeRandomlyChosen.add(diningRoom);
        roomsToBeRandomlyChosen.add(stairwell);
        roomsToBeRandomlyChosen.add(northWestStorageRoom);
        roomsToBeRandomlyChosen.add(northEastStorageRoom);
        roomsToBeRandomlyChosen.add(southWestBedroom);
        roomsToBeRandomlyChosen.add(southEastBedroom);
        roomsToBeRandomlyChosen.add(tower);
        roomsToBeRandomlyChosen.add(dungeonGrounds);
        roomsToBeRandomlyChosen.add(transporter);

    }

    /**
     *  Main play routine.  Loops until end of game.
     *  If player has all the 3 parts of the key he will win the game.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
            if((hasKey1==true && hasKey2==true & hasKey3==true ) || (hasSpare==true && hasKey2==true & hasKey3==true ) || (hasKey1==true && hasSpare==true & hasKey3==true ) || (hasKey1==true && hasKey2==true & hasSpare==true ))
            {
                System.out.println("\n\nCONGRATULATIONS! YOU ESCAPED! YOU WON!\n\n");
                break;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You wake up in a dark room. It is cold. The smell of cold stone and old furniture fills the room.\n A faint light!.. There is a candle on the cabinet next to you. A note is underneath it.\n Type 'read note' to proceed...");
        System.out.println();
        //System.out.println(currentRoom.getLongDescription());

    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) 
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) 
        {
            backStepCounter=0;
            printHelp();
        }
        else if (commandWord.equals("go"))
        {
            backStepCounter=0;
            goRoom(command,roomsToBeRandomlyChosen);
        }
        else if (commandWord.equals("quit")) 
        {
            backStepCounter=0;
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("read"))
        {
            backStepCounter=0;
            read(command);
        }
        else if(commandWord.equals("get"))
        {
            backStepCounter=0;
            get(command);
        }
        else if(commandWord.equals("inspect"))
        {
            backStepCounter=0;
            getItems(command,inventory);
        }
        else if(commandWord.equals("back"))
        {
            backStepCounter++;
            goBackRoom(backStepCounter);
        }
        else if(commandWord.equals("take"))
        {
            backStepCounter=0;
            take(command,inventory);
        }
        else if(commandWord.equals("drop"))
        {
            backStepCounter=0;
            drop(command,inventory);
        }
        else if(commandWord.equals("give"))
        {
            backStepCounter=0;
            give(command,inventory);
        }
        else if(commandWord.equals("reply"))
        {
            backStepCounter=0;
            reply(command);
        }
        else if(commandWord.equals("print"))
        {
            backStepCounter=0;
            print(command);
        }
        else if(commandWord.equals("kill"))
        {
            backStepCounter=0;
            kill(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     *Kill command to kill winnnie and get the spareKey.
     */
    private void kill(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Select target to kill!");
            return;
        }
        String secondString=command.getSecondWord();
        if(secondString.toLowerCase().equals("winnie"))
        {
            System.out.println("Congratulations! YOU  murdered a pure and innocent Winnie! Welcome to the dark side! Here is your reward!\n You got the Spare key!!\n");
            winnie.setAlive(false);
            hasSpare=true;
            return;

        } 
    }

    /**
     *Print + location. Gives the player information about the current room he is in.
     */
    private void print (Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Please specify what you wish to print out! Perhaps 'location'?");
        }
        String secondWord=command.getSecondWord();
        if(secondWord.toLowerCase().equals("location"))
        {
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     *Reply 1/2/3 - a command to answer the Librarian's question.
     */
    private void reply(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Please select one of the answers!\n");
            return;
        }
        String secondString=command.getSecondWord();
        if(secondString.startsWith("1"))
        {
            System.out.println("Wrong answer.Please try again!\n");
            return;
        }
        else if(secondString.startsWith("2"))
        {
            System.out.println("Wrong answer.Please try again!\n");
            return;
        }
        else if(secondString.startsWith("3"))
        {
            System.out.println("Correct! Here is your award stranger.\n You got 1/3 of the key.");
            hasKey1=true;
            return;
        }

    }

    /**
     * Give item character command- a 3-word command which gives the character an item from the inventory.
     *GIVE ITEM from inventory to CHEF
     *GIVE ITEM from invertory to MONSTER etc...
     */
    private void give(Command command, ArrayList inventory)
    {
        //implement cmd
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what? Perhaps you mean 'exits'...\n");
            return;
        }
        String secondWord=command.getSecondWord();
        if( inventory.contains(secondWord))
        {
            if(!command.hasThirdWord())
            {
                System.out.println("Give item to whom? Perhaps you mean chef/monster/someone else?\n"); 
                return;
            }

            String thirdWord=command.getThirdWord();
            if(thirdWord.equals("chef"))
            {
                System.out.println("Thank you kindly traveler!Here is your award.\n You got 1/3 of the key.\n");
                inventory.remove(command.getSecondWord());
                hasKey2=true;
                return;

            }
            if(thirdWord.toLowerCase().equals("winnie"))
            {
                System.out.println("Remember - Winnie is useless! DO not give items to Winnie. It is only here for moral support!");
                return;
            }

            else if(thirdWord.equals("monster"))
            {
                System.out.println(" ____");
                System.out.println("|@ @|");
                System.out.println("|VVV|");
                System.out.println("|^^^|");
                System.out.println(" ----");
                System.out.println("You got 1/3 of the key.\n");
                inventory.remove(command.getSecondWord());
                hasKey3=true;
                return;

            }
            else if(thirdWord.equals("wizzard"))
            {
                System.out.println("Thank you kindly traveler!Here is your award.\n The key once whole is now separated in three parts guarded by a chef in the dining room,\n a cruel librarian and a monster, residing in the dungeon bellow us.\nShould you wish to acquire the pieces take the recipebook of the chef from the south-west bedroom.\n The librarian will ask you a riddle - 3 is all you need to remember!\n In the north-west storage room you will find meat. Give it to the monster to tame it! NOW BEGONE TRAVELER!\n ");
                inventory.remove(command.getSecondWord());
                return;

            }
        }

    }

    /**
     * Pick up item command.
     * Here the player can pick up items.
     * Items are stored in the inventory.
     */
    private void take(Command command, ArrayList inventory)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what? You need an item to take.Type inspect to see the items...\n");
            return;
        }
        String secondWord=command.getSecondWord();
        if( currentRoom.printItems().contains(secondWord))
        {
            int itemWeight = currentRoom.getItemWeight(secondWord);
            if(itemWeight < storageSpace && (itemWeight + currentStorageSpace) < storageSpace)
            {
                inventory.add(secondWord);
                currentStorageSpace+= itemWeight;
                spaceLeft=storageSpace-currentStorageSpace;
                System.out.println("Item added to the inventory. You have "+ spaceLeft+" kg of storage left!\n");
            }

            else if((itemWeight + spaceLeft) > storageSpace && itemWeight < storageSpace )
            {
                System.out.println("Inventory full! You have "+ spaceLeft+" kg of storage left!\n");

            }

            else
            {
                System.out.println("Item too heavy to take. Your storage is 30kg. Type get + item + weight to check it!\n");

            }
            if
            (secondWord.equals("bookshelf"))
            {
                System.out.println("You can not pick up this item!");

            }

        }
    }

    /**
     * Drop item command.
     * Here the player can drop items.
     * Items are removed from the inventory.
     */    
    private void drop(Command command, ArrayList inventory)
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Remove what? You need an item to remove it.Type 'inspect inventory' to see the items...\n");
            return;
        }
        String secondWord=command.getSecondWord();
        if( inventory.contains(secondWord))
        {
            currentStorageSpace-=currentRoom.getItemWeight(secondWord);
            inventory.remove(secondWord);
            spaceLeft+=currentRoom.getItemWeight(secondWord);
            System.out.println("Item removed from the inventory. You have "+ spaceLeft+" kg of storage left!\n");
        }
        else if (!inventory.contains(secondWord))
        {
            System.out.println("You do not have such item!");
        }
    }

    /**
     * Get the items in the current room.
     * Here we print out the items to the player.
     * 
     */
    private void getItems(Command command,ArrayList inventory)
    {
        if(!command.hasSecondWord()) 
        {
            if(currentRoom.printItems().charAt(currentRoom.printItems().length()-1)==' ')
            {
                System.out.println("There are no items in this room!");
            }
            else
                System.out.println(currentRoom.printItems());

            return;
        }
        String secondWord=command.getSecondWord();
        if(secondWord.toLowerCase().equals("inventory"))
        {
            if(inventory.size()==0)
            {
                System.out.println("Your inventory is empty!\n ");
            }
            for(int i=0;i<inventory.size();i++)
            {
                System.out.println("You are holding a/an " + inventory.get(i)+ " !\n");
            }
        }
        else if (!secondWord.equals("inventory"))
        {
            System.out.println("Please enter a valid command!");
        }
    }

    /**
     * Get the exits of the current room.
     * Here we print out the exits to the player.
     * If second and third command words are 'item'  and weight then get the weight of an item.
     */
    private void get(Command command)
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what? Perhaps you mean 'exits'.../n");
            return;
        }
        String secondWord = command.getSecondWord();
        if(secondWord.toLowerCase().equals("exits"))
        {
            System.out.println(currentRoom.getLongDescription());
        }
        if(secondWord.toLowerCase().equals("items"))
        {
            System.out.println("Please type in a certain item and not 'item'!");
        }
        else if( currentRoom.printItems().contains(secondWord))
        {
            if(!command.hasThirdWord())
            {
                System.out.println("Get item's what? Perhaps you mean weight?\n"); 
                return;
            }

            String thirdWord=command.getThirdWord();
            if(thirdWord.equals("weight"))
            {
                System.out.println(currentRoom.getItemWeight(secondWord)+ "kg\n");
            }
        }

    }

    /**
     * Read the selected item aka the second command  word.
     * Here we print out the text which is read by the player.
     */
    private void read(Command command)
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Read what? Specify an object...\n");
            return;
        }

        String object = command.getSecondWord();
        if(object.toLowerCase().equals("note"))
        {
            System.out.println("...to whoever reads this, I have failed to escape this place.\n I am writing this to warn YOU, traveler.\n If you wish to avoid my fate then you have to follow my instructions to the best of your ability.\n I do not know much about this place, no one does really. I have been given this knowledge by the person casted away here before my time and so\n I am passing my wisdom to you now.There is an exit from this place. To escape you need to find the key.\n Do not be fooled - for this task is nearly impossible. The key, once whole, \n is now shattered into 3 pieces, each and every one of them guarded by a vile and  mischievous creature somewhere in the halls of this mansion.\n Outsmarting them is the 'key' to getting your freedom back. Whenever unsure as to what to do remember to ask for 'help'. Good luck traveler...\n" );
        }
        else if (!object.equals("note"))
        {
            System.out.println("Please enter a valid command!");
        }
    }

    /**
     * Print out some help information.
     * Here we print a table with all the command decriptions to aid the player in their quest.
     */
    private void printHelp() 
    {
        System.out.println("=================================================================================");
        System.out.println("Your command words are: \n");
        parser.showCommands();
        System.out.println("=================================================================================");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("give item character - Gives a character some item.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("take item - Adds item to the inventory.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("drop item - Removes item from the inventory.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("get exits - Prints out all the exits from the current room.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("get item weight - Gets the weight of a certain item.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("help - Prints out all the commands.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("go exit - Moves the player to another room.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("reply number - Used to answer to the Librarians's question.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("back- Return to previous room.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("quit - Quit game.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("inspect- Inspects the current room for items.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("inspect inventory- Returns the items the player is holding.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("print location- Prints the current location of the player.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("kill winnie- Takes winnie's life.");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("read note- Reads starting note.");
        System.out.println("---------------------------------------------------------------------------------");

    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message. If you enter the transporter you get transported to a random room.
     */
    private void goRoom(Command command ,ArrayList roomsToBeRandomlyChosen) 
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?\n");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.

        visitedRooms.add(currentRoom);    
        Room nextRoom = currentRoom.getExit(direction);
        //create a random index for Good winnie
        Random rand=new Random();
        int randomNumber = rand.nextInt(roomsToBeRandomlyChosen.size()-1);
        //create a random index for Bad winnie
        Random rand2=new Random();
        int randomNumber2 = rand2.nextInt(roomsToBeRandomlyChosen.size()-1);

        Room nextNpcRoom=(Room)roomsToBeRandomlyChosen.get(randomNumber);
        nextNpcRoom.getExit(getRandomNeighboringRoom()); //rooms which the winnie useless npc visits

        Room nextNpcRoomForBadNpc=(Room)roomsToBeRandomlyChosen.get(randomNumber2);
        nextNpcRoomForBadNpc.getExit(getRandomNeighboringRoom()); //rooms which the bad winnie  npc visits
        if (nextRoom == null) 
        {
            System.out.println("There is no door!\n");
            return;

        }
        else if(nextRoom==library && hasKey1==true)
        { 
            currentRoom = nextRoom;
            System.out.println("This fragment has already been collected!"); //fragment already taken
        }
        else if(nextRoom==dungeonGrounds && hasKey3==true)
        { 
            currentRoom = nextRoom;
            System.out.println("This fragment has already been collected!");//fragment already taken
        }
        else if(nextRoom==diningRoom && hasKey2==true)
        { 
            currentRoom = nextRoom;
            System.out.println("This fragment has already been collected!");//fragment already taken
        }

        else if(nextRoom == transporter) // ransporter room
        {

            randomNumber = rand.nextInt(roomsToBeRandomlyChosen.size()-1);
            currentRoom = (Room)roomsToBeRandomlyChosen.get(randomNumber);
            System.out.println(" You are suddenly being transported!\n"+ currentRoom.getLongDescription());
            return;

        }
        else {
            if(nextRoom==nextNpcRoom)
            {
                currentRoom = nextRoom;
                if(winnie.getAlive()==true)
                {
                    System.out.println("\nA good winnie appears!.\n");
                    System.out.println(winnie.getDescription());
                }
            }
            if(nextRoom==nextNpcRoomForBadNpc)
            {
                currentRoom = nextRoom;
                if(winnie.getAlive()==false)
                {
                    System.out.println("\n You met the bad winnie again. Winnie is dead! You are a part of the dark side now!\n");
                }
                else
                {

                    System.out.println("\nA bad winnie appears!.\n");
                    System.out.println(badWinnie.getDescription());

                }
            }
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }

    }

    /** 
     * Go to the last room you visited.
     * 
     */
    private void goBackRoom(int backStepCounter)
    {
        if(backStepCounter>visitedRooms.size()) //if you go back too many times stop!
        {
            System.out.println("You can't go back any further!");
            return;
        }
        if(visitedRooms.contains(visitedRooms.get(visitedRooms.size()-backStepCounter)))
        {
            currentRoom=visitedRooms.get(visitedRooms.size()-backStepCounter);
            System.out.println(currentRoom.getLongDescription());

        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) 
        {
            System.out.println("Quit what?");
            return false;
        }
        else 
        {
            return true;  // signal that we want to quit
        }
    }

    /** 
     * Gets the exits of the current room the player is in and returns one of them randomly.
     */
    private String getRandomNeighboringRoom()
    {
        String exitString=currentRoom.returnExitString();
        ArrayList<String> exits = new ArrayList<String>(Arrays.asList(exitString.split(" ")));
        exits.add(exitString);
        Random rand=new Random();
        int randomNumber = rand.nextInt(exits.size()-1);
        String randomExit = exits.get(randomNumber);
        return randomExit;

    }
}
