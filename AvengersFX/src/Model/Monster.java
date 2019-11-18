package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Monster {
    private String id;
    private String name;
    private String description;
    private boolean isDead;
    private int healthPoints;
    private int maxHealthPoints;
    private int attackPointsMin;
    private int attackPointsMax;

    public Monster(String id, String name, String description, boolean isDead, int healthPoints, int maxHealthPoints, int attackPointsMin, int attackPointsMax) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDead = isDead;
        this.healthPoints = healthPoints;
        this.maxHealthPoints = maxHealthPoints;
        this.attackPointsMin = attackPointsMin;
        this.attackPointsMax = attackPointsMax;
    }

    public String getMonsterID() {
        return id;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void acceptDamage(int healthPoints) {
        this.healthPoints -= healthPoints;

        if (this.healthPoints <= 0) {
            isDead = true;
            this.healthPoints = 0;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMonsterDead() {
        return isDead;
    }

    public int getAttackPoints() {
        Random r = new Random();
        return r.nextInt((attackPointsMax - attackPointsMin) + 1) + attackPointsMin;
    }

    public String getStats() {
        return "Name: " + name + "\nHealth: " + healthPoints;
    }

    public void toFile() throws IOException {
        BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveMonsters.txt", true));
        buffwriter.append("@m" + id + "\n");
        buffwriter.append("@n" + name + "\n");
        String desc = description;
        desc = desc.replaceAll("\n", "\n~");
        buffwriter.append("~" + desc + "\n");
        buffwriter.append("@ds" + isDead + "\n");
        buffwriter.append("@hp" + healthPoints + "\n");
        buffwriter.append("@HmaxP" + maxHealthPoints + "\n");
        buffwriter.append("@apmin" + attackPointsMin + "\n");
        buffwriter.append("@apmax" + attackPointsMax + "\n");
        buffwriter.append("+\n");
        buffwriter.close();
    }

    @Override
    public String toString() {
        return "Monster{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", description='" + description + '\'' + ", isDead=" + isDead + ", healthPoints=" + healthPoints + ", maxHealthPoints=" + maxHealthPoints + ", attackPointsMin=" + attackPointsMin + ", attackPointsMax=" + attackPointsMax + '}';
    }
}
