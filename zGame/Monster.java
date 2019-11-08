package zGame;

public class Monster
{
	private String id;
	private String name;
	private String description;
	private boolean isDead;
	private int healthPoints;
	private int attackPoints;

	public Monster(String id, String name, String description, boolean isDead, int healthPoints, int attackPoints)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.isDead = isDead;
		this.healthPoints = healthPoints;
		this.attackPoints = attackPoints;
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
		return attackPoints;
	}
	
	public String getStats()
	{
		return "Name: " + name +"\nHealth: " + healthPoints;
	}

}
