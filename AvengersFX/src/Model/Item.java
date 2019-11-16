package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
	
	public void toFile() throws IOException
	{
		BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveItems.txt", true));
		buffwriter.append("@i" + itemName + "\n");
		buffwriter.append("@t" + itemType + "\n");
		buffwriter.append("@dv" + itemDelta + "\n");
		String desc = description;
		desc = desc.replaceAll("\n", "\n~");
		buffwriter.append("~" + desc + "\n");
		buffwriter.append("+\n");
		buffwriter.close();
	}
}
