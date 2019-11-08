package zGame;

import java.util.ArrayList;

public class Room
{
	private String roomId;
	private boolean isVisited;
	private String description;
	private String descriptionVisited;
	private ArrayList<String> items;
	private String puzzleID;
	private String monsterID;
	private String northRoomID;
	private String eastRoomID;
	private String southRoomID;
	private String westRoomID;
	private boolean northLocked;
	private boolean eastLocked;
	private boolean southLocked;
	private boolean westLocked;
	private int northKey;
	private int eastKey;
	private int southKey;
	private int westKey;
	private int pattern;

	public Room(String roomId, boolean isVisited, String description, String descriptionVisited,
			ArrayList<String> items, String puzzleID, String monsterID, String northRoomID, String eastRoomID,
			String southRoomID, String westRoomID, boolean northLocked, boolean eastLocked, boolean southLocked,
			boolean westLocked, int northKey, int eastKey, int southKey, int westKey, int pattern)
	{
		this.roomId = roomId;
		this.isVisited = isVisited;
		this.description = description;
		this.descriptionVisited = descriptionVisited;
		this.items = items;
		this.puzzleID = puzzleID;
		this.monsterID = monsterID;
		this.northRoomID = northRoomID;
		this.eastRoomID = eastRoomID;
		this.southRoomID = southRoomID;
		this.westRoomID = westRoomID;
		this.northLocked = northLocked;
		this.eastLocked = eastLocked;
		this.southLocked = southLocked;
		this.westLocked = westLocked;
		this.northKey = northKey;
		this.eastKey = eastKey;
		this.southKey = southKey;
		this.westKey = westKey;
		this.pattern = pattern;
	}

	public String getRoomId()
	{
		return roomId;
	}

	public boolean isVisited()
	{
		return isVisited;
	}

	public void setVisited(boolean isVisited)
	{
		this.isVisited = isVisited;
	}

	public String getDescription()
	{
		if (isVisited == true)
		{
			return descriptionVisited;
		} else
		{
			return description;
		}
	}

	public void pickupItem(String item)
	{
		items.add(item);
	}

	public void dropItem(String item) throws InvalidItemException
	{
		if (items.contains(item) == false)
		{
			throw new InvalidItemException("This item is not here");
		}

		items.remove(items.indexOf(item));
	}

	public ArrayList<String> getItems()
	{
		return items;
	}

	public String getPuzzleId()
	{
		return puzzleID;
	}

	public String getMonsterID()
	{
		return monsterID;
	}

	public String getConnections()
	{
		String des = "";

		if (!northRoomID.equals("0"))
		{
			des = northRoomID;
		}
		if (!eastRoomID.equals("0"))
		{
			des += " " + eastRoomID;
		}
		if (!southRoomID.equals("0"))
		{
			des += " " + southRoomID;
		}
		if (!westRoomID.equals("0"))
		{
			des += " " + westRoomID;
		}

		return des;
	}

	public String getNorthRoomID()
	{
		return northRoomID;
	}

	public String getEastRoomID()
	{
		return eastRoomID;
	}

	public String getSouthRoomID()
	{
		return southRoomID;
	}

	public String getWestRoomID()
	{
		return westRoomID;
	}

	public boolean isNorthLocked()
	{
		return northLocked;
	}

	public void setNorthLocked(boolean northLocked)
	{
		this.northLocked = northLocked;
	}

	public boolean isEastLocked()
	{
		return eastLocked;
	}

	public void setEastLocked(boolean eastLocked)
	{
		this.eastLocked = eastLocked;
	}

	public boolean isSouthLocked()
	{
		return southLocked;
	}

	public void setSouthLocked(boolean southLocked)
	{
		this.southLocked = southLocked;
	}

	public boolean isWestLocked()
	{
		return westLocked;
	}

	public void setWestLocked(boolean westLocked)
	{
		this.westLocked = westLocked;
	}

	public int getNorthKey()
	{
		return northKey;
	}

	public void setNorthKey(int northKey)
	{
		this.northKey = northKey;
	}

	public int getEastKey()
	{
		return eastKey;
	}

	public void setEastKey(int eastKey)
	{
		this.eastKey = eastKey;
	}

	public int getSouthKey()
	{
		return southKey;
	}

	public void setSouthKey(int southKey)
	{
		this.southKey = southKey;
	}

	public int getWestKey()
	{
		return westKey;
	}

	public void setWestKey(int westKey)
	{
		this.westKey = westKey;
	}
	
	public int getPattern()
	{
		return pattern;
	}

}
