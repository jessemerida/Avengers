package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileManager {
    public static Player readPlayer(String fileName) throws FileNotFoundException {
        Player player = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner inFile = new Scanner(fileReader);

            // skip first line
            inFile.nextLine();

            // add the rooms to a list

            String[] tokens = inFile.nextLine().split("~");
            ArrayList<String> items = new ArrayList<String>(Arrays.asList(tokens[3].trim().split(",")));
            player = new Player(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), items, Integer.parseInt(tokens[4]));

            inFile.close();
            fileReader.close();

        } catch (IOException e) {
            throw new FileNotFoundException("Player file not found.");
        }

        return player;

    }

    public static Player readPlayer2(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        Player player = null;
        String currLine = "";

        String playername = "";
        int currenthealthPoints = 0;
        int maxHealthPoints = 0;
        ArrayList<String> items = new ArrayList<String>();
        int defaultAttack = 0;

        try {
            fileReader = new FileReader(fileName);
            inFile = new Scanner(fileReader);

            while (inFile.hasNextLine()) {
                currLine = inFile.nextLine();
                if (!currLine.contains("+")) {
                    if (currLine.contains("@n")) playername = currLine.substring(2);
                    if (currLine.contains("@hp")) currenthealthPoints = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@mhp")) maxHealthPoints = Integer.parseInt(currLine.substring(4));
                    if (currLine.contains("@i"))
                        items = new ArrayList<String>(Arrays.asList(currLine.substring(2).split(",")));
                    if (currLine.contains("@ap")) defaultAttack = Integer.parseInt(currLine.substring(3));
                }
                if (currLine.contains("+")) {
                    player = new Player(playername, currenthealthPoints, maxHealthPoints, items, defaultAttack);
                    playername = "";
                    currenthealthPoints = 0;
                    maxHealthPoints = 0;
                    items = new ArrayList<String>();
                    defaultAttack = 0;
                }
            }
            inFile.close();
            fileReader.close();
        } catch (IOException e) {
            throw new FileNotFoundException("Player file not found.");
        }

        return player;
    }

    public static ArrayList<Room> readRooms(String fileName) throws FileNotFoundException {
        ArrayList<Room> allRooms = new ArrayList<Room>();

        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner inFile = new Scanner(fileReader);

            // skip first line
            inFile.nextLine();

            // add the rooms to a list
            while (inFile.hasNextLine()) {
                String[] tokens = inFile.nextLine().trim().split("~");
                ArrayList<String> itemsList = new ArrayList<String>(Arrays.asList(tokens[4].trim().split(",")));
                // not all rooms will have an item, so noItems indicates that the item list is
                // empty
                if (itemsList.get(0).equals("0")) {
                    itemsList.clear();
                }

                String[] roomTokens = tokens[7].trim().split(",");
                String[] roomLockedTokens = tokens[8].trim().split(",");
                String[] roomKeys = tokens[9].trim().split(",");

                allRooms.add(new Room(tokens[0], Boolean.parseBoolean(tokens[1]), tokens[2], tokens[3], itemsList, tokens[5], tokens[6], roomTokens[0], roomTokens[1], roomTokens[2], roomTokens[3], Boolean.parseBoolean(roomLockedTokens[0]), Boolean.parseBoolean(roomLockedTokens[1]), Boolean.parseBoolean(roomLockedTokens[2]), Boolean.parseBoolean(roomLockedTokens[3]), Integer.parseInt(roomKeys[0]), Integer.parseInt(roomKeys[1]), Integer.parseInt(roomKeys[2]), Integer.parseInt(roomKeys[3]), Integer.parseInt(tokens[10])));
            }

            inFile.close();
            fileReader.close();

        } catch (IOException e) {
            throw new FileNotFoundException("Room file not found.");
        }

        return allRooms;
    }

    public static ArrayList<Room> readRooms2(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        ArrayList<Room> allRooms = new ArrayList<Room>();
        String currLine = "";

        String roomId = "";
        boolean isVisited = false;
        String description = "";
        String descriptionVisited = "";
        ArrayList<String> items = new ArrayList<String>();
        String puzzleID = "";
        String monsterID = "";
        String connection1 = "";
        String connection2 = "";
        String connection3 = "";
        String connection4 = "";
        boolean connection1Locked = false;
        boolean connection2Locked = false;
        boolean connection3Locked = false;
        boolean connection4Locked = false;
        int connection1Key = 0;
        int connection2Key = 0;
        int connection3Key = 0;
        int connection4Key = 0;
        int pattern = 0;

        try {
            fileReader = new FileReader(fileName);
            inFile = new Scanner(fileReader);

            while (inFile.hasNextLine()) {
                currLine = inFile.nextLine();
                if (!currLine.contains("+")) {
                    if (currLine.contains("#")) roomId = currLine.substring(1);
                    if (currLine.contains("@v")) isVisited = Boolean.parseBoolean(currLine.substring(2));
                    if (currLine.contains("~")) {
                        if (description.isEmpty()) description += currLine.substring(1);
                        else description += "\n" + currLine.substring(1);
                    }
                    if (currLine.contains("~-")) {
                        if (description.isEmpty()) description += currLine.substring(2);
                        else description += "\n" + currLine.substring(2);
                    }
                    if (currLine.contains("@i"))
                        items = new ArrayList<String>(Arrays.asList(currLine.substring(2).split(",")));
                    if (currLine.contains("@p")) puzzleID = currLine.substring(2);
                    if (currLine.contains("@m")) monsterID = currLine.substring(2);
                    if (currLine.contains("@c1")) connection1 = currLine.substring(3);
                    if (currLine.contains("@c2")) connection2 = currLine.substring(3);
                    if (currLine.contains("@c3")) connection3 = currLine.substring(3);
                    if (currLine.contains("@c4")) connection4 = currLine.substring(3);
                    if (currLine.contains("@l1")) connection1Locked = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@l2")) connection2Locked = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@l3")) connection3Locked = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@l4")) connection4Locked = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@k1")) connection1Key = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@k2")) connection2Key = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@k3")) connection3Key = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@k4")) connection4Key = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@pa")) pattern = Integer.parseInt(currLine.substring(3));
                }

                if (currLine.contains("+")) {
                    allRooms.add(new Room(roomId, isVisited, description, descriptionVisited, items, puzzleID, monsterID, connection1, connection2, connection3, connection4, connection1Locked, connection2Locked, connection3Locked, connection4Locked, connection1Key, connection2Key, connection3Key, connection4Key, pattern));
                    //                    if (items.get(0).equals("0")) items.clear();
                    //                    // USED TO BE HERE
                    //                    roomId = "";
                    //                    isVisited = false;
                    //                    description = "";
                    //                    descriptionVisited = "";
                    //                    items = new ArrayList<String>();
                    //                    puzzleID = "";
                    //                    monsterID = "";
                    //                    connection1 = "";
                    //                    connection2 = "";
                    //                    connection3 = "";
                    //                    connection4 = "";
                    //                    connection1Locked = false;
                    //                    connection2Locked = false;
                    //                    connection3Locked = false;
                    //                    connection4Locked = false;
                    //                    connection1Key = 0;
                    //                    connection2Key = 0;
                    //                    connection3Key = 0;
                    //                    connection4Key = 0;
                    //                    pattern = 0;
                }
                /**
                 * LOOP END
                 */
            }

            inFile.close();
            fileReader.close();
            /**
             * TRY END
             */
        } catch (IOException e) {
            throw new FileNotFoundException("Room file not found.");
        }

        return allRooms;
    }

    public static ArrayList<Item> readItems(String fileName) throws FileNotFoundException {
        ArrayList<Item> allItems = new ArrayList<Item>();

        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner inFile = new Scanner(fileReader);

            // skip first line
            inFile.nextLine();

            // add the rooms to a list
            while (inFile.hasNextLine()) {
                String[] tokens = inFile.nextLine().trim().split("~");
                allItems.add(new Item(tokens[0], ItemType.valueOf(tokens[1].toUpperCase()), Integer.parseInt(tokens[2]), tokens[3]));
            }

            inFile.close();
            fileReader.close();

        } catch (IOException e) {
            throw new FileNotFoundException("Item file not found.");
        }

        return allItems;
    }

    public static ArrayList<Item> readItems2(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        ArrayList<Item> allItems = new ArrayList<Item>();
        String currLine = "";

        String itemName = "";
        ItemType itemType = null;
        int itemDelta = 0;
        String description = "";

        try {
            fileReader = new FileReader(fileName);
            inFile = new Scanner(fileReader);

            while (inFile.hasNextLine()) {
                currLine = inFile.nextLine();
                if (!currLine.contains("+")) {
                    if (currLine.contains("@i")) itemName = currLine.substring(2);
                    if (currLine.contains("@t")) itemType = ItemType.valueOf(currLine.substring(2).toUpperCase());
                    if (currLine.contains("@dv")) itemDelta = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("~")) {
                        if (description.isEmpty()) description += currLine.substring(1);
                        else description += "\n" + currLine.substring(1);
                    }
                }
                if (currLine.contains("+")) {
                    allItems.add(new Item(itemName, itemType, itemDelta, description));
                    itemName = "";
                    itemType = null;
                    itemDelta = 0;
                    description = "";
                }
            }
            inFile.close();
            fileReader.close();
        } catch (IOException e) {
            throw new FileNotFoundException("Item file not found.");
        }

        return allItems;
    }

    public static ArrayList<Puzzle> readPuzzles(String fileName) throws FileNotFoundException {
        ArrayList<Puzzle> allPuzzles = new ArrayList<Puzzle>();

        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner inFile = new Scanner(fileReader);

            // skip first line
            inFile.nextLine();

            // add the rooms to a list
            while (inFile.hasNextLine()) {
                String[] tokens = inFile.nextLine().trim().split("~");
                allPuzzles.add(new Puzzle(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), tokens[6], Boolean.parseBoolean(tokens[7]), Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9])));
            }

            inFile.close();
            fileReader.close();

        } catch (IOException e) {
            throw new FileNotFoundException("Puzzle file not found.");
        }
        return allPuzzles;

    }

    public static ArrayList<Puzzle> readPuzzles2(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        ArrayList<Puzzle> allPuzzles = new ArrayList<Puzzle>();
        String currLine = "";

        String id = "";
        String question = "";
        String answer = "";
        String passMessage = "";
        String failMessage = "";
        int attemptsAllowed = 0;
        String hint = "";
        boolean solved = false;
        int damageMin = 0;
        int damageMax = 0;

        try {
            fileReader = new FileReader(fileName);
            inFile = new Scanner(fileReader);

            while (inFile.hasNextLine()) {
                currLine = inFile.nextLine();
                if (!currLine.contains("+")) {
                    if (currLine.contains("@p")) id = currLine.substring(2);
                    if (currLine.contains("~")) {
                        if (question.isEmpty()) question += currLine.substring(1);
                        else question += "\n" + currLine.substring(1);
                    }
                    if (currLine.contains("@a")) answer = currLine.substring(2);
                    if (currLine.contains("@pmsg")) passMessage = currLine.substring(5);
                    if (currLine.contains("@fmsg")) failMessage = currLine.substring(5);
                    if (currLine.contains("@at")) attemptsAllowed = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@h")) hint = currLine.substring(2);
                    if (currLine.contains("@ss")) solved = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@dmin")) damageMin = Integer.parseInt(currLine.substring(5));
                    if (currLine.contains("@dmax")) damageMax = Integer.parseInt(currLine.substring(5));
                }
                if (currLine.contains("+")) {
                    allPuzzles.add(new Puzzle(id, question, answer, passMessage, failMessage, attemptsAllowed, hint, solved, damageMin, damageMax));
                    id = "";
                    question = "";
                    answer = "";
                    passMessage = "";
                    failMessage = "";
                    attemptsAllowed = 0;
                    hint = "";
                    solved = false;
                    damageMin = 0;
                    damageMax = 0;
                }
            }
            inFile.close();
            fileReader.close();
        } catch (IOException e) {
            throw new FileNotFoundException("Puzzle file not found.");
        }

        return allPuzzles;
    }

    public static ArrayList<Monster> readMonsters(String fileName) throws FileNotFoundException {
        ArrayList<Monster> allMonsters = new ArrayList<Monster>();

        try {
            FileReader fileReader = new FileReader(fileName);
            Scanner inFile = new Scanner(fileReader);

            // skip first line
            inFile.nextLine();

            // add the rooms to a list
            while (inFile.hasNextLine()) {
                String[] tokens = inFile.nextLine().trim().split("~");
                allMonsters.add(new Monster(tokens[0], tokens[1], tokens[2], Boolean.parseBoolean(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));
            }

            inFile.close();
            fileReader.close();

        } catch (IOException e) {
            throw new FileNotFoundException("Monster file not found.");
        }
        return allMonsters;
    }

    public static ArrayList<Monster> readMonsters2(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        ArrayList<Monster> allMonsters = new ArrayList<Monster>();
        String currLine = "";

        String id = "";
        String name = "";
        String description = "";
        boolean isDead = false;
        int healthPoints = 0;
        int attackPointsMin = 0;
        int attackPointsMax = 0;

        try {
            fileReader = new FileReader(fileName);
            inFile = new Scanner(fileReader);

            while (inFile.hasNextLine()) {
                currLine = inFile.nextLine();
                if (!currLine.contains("+")) {
                    if (currLine.contains("@m")) id = currLine.substring(2);
                    if (currLine.contains("@n")) name = currLine.substring(2);
                    if (currLine.contains("~")) {
                        if (description.isEmpty()) description += currLine.substring(1);
                        else description += "\n" + currLine.substring(1);
                    }
                    if (currLine.contains("@ds")) isDead = Boolean.parseBoolean(currLine.substring(3));
                    if (currLine.contains("@hp")) attackPointsMin = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@apmin")) attackPointsMin = Integer.parseInt(currLine.substring(6));
                    if (currLine.contains("@apmax")) attackPointsMax = Integer.parseInt(currLine.substring(6));
                }
                if (currLine.contains("+")) {
                    allMonsters.add(new Monster(id, name, description, isDead, healthPoints, attackPointsMin, attackPointsMax));
                    id = "";
                    name = "";
                    description = "";
                    isDead = false;
                    healthPoints = 0;
                    attackPointsMin = 0;
                    attackPointsMax = 0;
                }
            }

            inFile.close();
            fileReader.close();
        } catch (IOException e) {
            throw new FileNotFoundException("Monster file not found.");
        }

        return allMonsters;
    }
}
