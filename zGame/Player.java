package zGame;

import java.util.ArrayList;

public class Player
{
	private String name;
	private int healthPoints;
	private ArrayList<String> items;
	private int defaultAttack;
	private String equippedItem;

	public Player(String name, int healthPoints, ArrayList<String> items, int defaultAttack)
	{
		this.name = name;
		this.healthPoints = healthPoints;
		this.items = items;
		this.defaultAttack = defaultAttack;
	}

	public String setEquippedItem(String item) throws InvalidItemException
	{
		if (items.contains(item) == false)
		{
			throw new InvalidItemException("This item is not here");
		}
		equippedItem = item;

		return equippedItem + " is now equipped";
	}

	public String clearEquippedItem()
	{
		equippedItem = "";
		return "Hand is now clear";
	}

	public String getEquippedItem()
	{
		return equippedItem;
	}

	public String getStats()
	{
		return "Name: " + name + "\nHealth: " + healthPoints;
	}

	public ArrayList<String> getInventory()
	{
		return items;
	}

	public String pickupItem(String item)
	{
		items.add(item);
		return item + " is picked up";
	}

	public String dropItem(String item) throws InvalidItemException
	{
		if (items.contains(item) == false)
		{
			throw new InvalidItemException(item + " is not here");
		}

		items.remove(items.indexOf(item));
		return item + " was dropped";
	}

	public int getDefaultAttack()
	{
		return defaultAttack;
	}

	public void acceptDamage(int damage)
	{
		healthPoints -= damage;
	}

	public void acceptHeal(int hp)
	{
		healthPoints += hp;
	}

}
