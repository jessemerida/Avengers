package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileManager {

    public static Player readPlayer(String fileName) throws FileNotFoundException {
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
                    if (items.get(0).equals("0")) items.clear();
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

    public static void savePlayer(Player player) throws FileNotFoundException {
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter("savePlayer.txt", false));
            buffwriter.write("");
            buffwriter.close();
            player.toFile();
        } catch (IOException e) {
            throw new FileNotFoundException("Player save file not found.");
        }
    }

    public static ArrayList<Room> readRooms(String fileName) throws FileNotFoundException {
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
                    if (currLine.contains("=-")) {
                        if (descriptionVisited.isEmpty()) descriptionVisited += currLine.substring(2);
                        else descriptionVisited += "\n" + currLine.substring(2);
                    }
                    if (currLine.contains("@i"))
                        items = new ArrayList<String>(Arrays.asList(currLine.substring(2).split(",")));
                    for (int i = 0; i < items.size(); i++) {
                        String tempItem = items.get(i);
                        items.set(i, tempItem.trim());
                    }
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
                    if (currLine.contains("@rn")) pattern = Integer.parseInt(currLine.substring(3));
                }

                if (currLine.contains("+")) {
                    allRooms.add(new Room(roomId, isVisited, description, descriptionVisited, items, puzzleID, monsterID, connection1, connection2, connection3, connection4, connection1Locked, connection2Locked, connection3Locked, connection4Locked, connection1Key, connection2Key, connection3Key, connection4Key, pattern));
                    if (items.get(0).equals("0")) items.clear();
                    roomId = "";
                    isVisited = false;
                    description = "";
                    descriptionVisited = "";
                    items = new ArrayList<String>();
                    puzzleID = "";
                    monsterID = "";
                    connection1 = "";
                    connection2 = "";
                    connection3 = "";
                    connection4 = "";
                    connection1Locked = false;
                    connection2Locked = false;
                    connection3Locked = false;
                    connection4Locked = false;
                    connection1Key = 0;
                    connection2Key = 0;
                    connection3Key = 0;
                    connection4Key = 0;
                    pattern = 0;
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

    public static void saveRooms(ArrayList<Room> allRooms) throws FileNotFoundException {
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveRooms.txt", false));
            buffwriter.write("");
            buffwriter.close();
            for (Room r : allRooms) {
                r.toFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Room save file not found.");
        }
    }

    public static ArrayList<Item> readItems(String fileName) throws FileNotFoundException {
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

    public static void saveItems(ArrayList<Item> allItems) throws FileNotFoundException {
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveItems.txt", false));
            buffwriter.write("");
            buffwriter.close();
            for (Item i : allItems) {
                i.toFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Item save file not found.");
        }
    }

    public static ArrayList<Puzzle> readPuzzles(String fileName) throws FileNotFoundException {
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
                    if (currLine.contains("@msg")) passMessage = currLine.substring(4);
                    if (currLine.contains("@fmsg")) failMessage = currLine.substring(5);
                    if (currLine.contains("@try")) attemptsAllowed = Integer.parseInt(currLine.substring(4));
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

    public static void savePuzzles(ArrayList<Puzzle> allPuzzles) throws FileNotFoundException {
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter("savePuzzles.txt", false));
            buffwriter.write("");
            buffwriter.close();
            for (Puzzle p : allPuzzles) {
                p.toFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Puzzle save file not found.");
        }
    }

    public static ArrayList<Monster> readMonsters(String fileName) throws FileNotFoundException {
        FileReader fileReader = null;
        Scanner inFile = null;

        ArrayList<Monster> allMonsters = new ArrayList<Monster>();
        String currLine = "";

        String id = "";
        String name = "";
        String description = "";
        boolean isDead = false;
        int healthPoints = 0;
        int maxHealthPoints = 0;
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
                    if (currLine.contains("@hp")) healthPoints = Integer.parseInt(currLine.substring(3));
                    if (currLine.contains("@HmaxP")) {
                        maxHealthPoints = Integer.parseInt(currLine.substring(6));
                    }
                    if (currLine.contains("@apmin")) attackPointsMin = Integer.parseInt(currLine.substring(6));
                    if (currLine.contains("@apmax")) attackPointsMax = Integer.parseInt(currLine.substring(6));
                }
                if (currLine.contains("+")) {
                    allMonsters.add(new Monster(id, name, description, isDead, healthPoints, maxHealthPoints, attackPointsMin, attackPointsMax));
                    id = "";
                    name = "";
                    description = "";
                    isDead = false;
                    healthPoints = 0;
                    maxHealthPoints = 0;
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

    public static void saveMonsters(ArrayList<Monster> allMonsters) throws FileNotFoundException {
        try {
            BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveMonsters.txt", false));
            buffwriter.write("");
            buffwriter.close();
            for (Monster m : allMonsters) {
                m.toFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException("Monster save file not found.");
        }
    }
}
