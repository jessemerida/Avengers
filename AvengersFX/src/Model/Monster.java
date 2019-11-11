package Model;

import java.util.Random;

public class Monster
{
	private String id;
	private String name;
	private String description;
	private boolean isDead;
	private int healthPoints;
	private int attackPointsMin;
	private int attackPointsMax;

	public Monster(String id, String name, String description, boolean isDead, int healthPoints, int attackPointsMin, int attackPointsMax)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.isDead = isDead;
		this.healthPoints = healthPoints;
		this.attackPointsMin = attackPointsMin;
		this.attackPointsMax = attackPointsMax;
	}

	public String getMonsterID()
	{
		return id;
	}

	public int getHealthPoints()
	{
		return healthPoints;
	}

	public void acceptDamage(int healthPoints)
	{
		this.healthPoints -= healthPoints;
		
		if (this.healthPoints <= 0)
		{
			isDead = true;
		}
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isMonsterDead()
	{
		return isDead;
	}

	public int getAttackPoints()
	{
		Random r = new Random();
		return r.nextInt((attackPointsMax - attackPointsMin) + 1) + attackPointsMin;
	}
	
	public String getStats()
	{
		return "Name: " + name +"\nHealth: " + healthPoints;
	}

}
