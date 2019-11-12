package Model;

public class Item
{
	private String itemName;
	private ItemType itemType;
	private int itemDelta;
	private String description;

	public Item(String itemName, ItemType itemType, int itemDelta, String description)
	{
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemDelta = itemDelta;
		this.description = description;
	}

	public String getItemName()
	{
		return itemName;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public int getItemDelta()
	{
		return itemDelta;
	}

	public String getDescription()
	{
		return itemType.toString().toLowerCase() + ": " + description;
		// TODO list full description including based on type and damage/heal
//		if (itemType == ItemType.CONSUMABLE)
//		{
//			return "health stuff";
//		} else if (itemType == ItemType.WEAPON)
//		{
//			return "weapon stuff";
//		}

	}
}
