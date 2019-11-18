package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String name;
    private int currentHealthPoints;
    private int maxHealthPoints;
    private ArrayList<String> items;
    private int defaultAttack;
    private String equippedItem;

    public Player(String name, int currentHealthPoints, int maxHealthPoints, ArrayList<String> items, int defaultAttack) {
        this.name = name;
        this.currentHealthPoints = currentHealthPoints;
        this.maxHealthPoints = maxHealthPoints;
        this.items = items;
        this.defaultAttack = defaultAttack;
        equippedItem = "";
    }

    public String setEquippedItem(String item) throws InvalidItemException {
        if (items.contains(item) == false) {
            throw new InvalidItemException("This item is not here");
        }
        equippedItem = item;

        return equippedItem + " is now equipped";
    }

    public String clearEquippedItem() {
        equippedItem = "";
        return "Hand is now clear";
    }

    public String getEquippedItem() {
        return equippedItem;
    }

    public String getStats() {
        return "Name: " + name + "\nHealth: " + currentHealthPoints;
    }

    public int getHealth() {
        return currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public ArrayList<String> getInventory() {
        Collections.sort(items);
        return items;
    }

    public String pickupItem(String item) {
        items.add(item);
        return item + " is picked up";
    }

    public String dropItem(String item) throws InvalidItemException {
        if (items.contains(item) == false) {
            throw new InvalidItemException(item + " is not here");
        }

        items.remove(items.indexOf(item));
        return item + " was dropped";
    }

    public int getDefaultAttack() {
        return defaultAttack;
    }

    public void acceptDamage(int damage) {
        currentHealthPoints -= damage;

        if(currentHealthPoints <=0)
        {
            currentHealthPoints = 0;
        }
    }

    public void acceptHeal(int hp) {
        currentHealthPoints += hp;

        if (currentHealthPoints > maxHealthPoints) {
            currentHealthPoints = maxHealthPoints;
        }
    }

    public void toFile() throws IOException {
        BufferedWriter buffwriter = new BufferedWriter(new FileWriter("savePlayer.txt", true));
        buffwriter.append("@n" + name + "\n");
        buffwriter.append("@hp" + currentHealthPoints + "\n");
        buffwriter.append("@mhp" + maxHealthPoints + "\n");
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                if (i == 0) buffwriter.append("@i" + items.get(i));
                else buffwriter.append(", " + items.get(i));
            }
        } else buffwriter.append("@i0");
        buffwriter.append("\n@ap" + defaultAttack + "\n");
        buffwriter.append("+\n");
        buffwriter.close();
    }
}
