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
	//TDOD refactor to connection number
	private String connection1;
	private String connection2;
	private String connection3;
	private String connection4;
	private boolean connection1Locked;
	private boolean connection2Locked;
	private boolean connection3Locked;
	private boolean connection4Locked;
	private int connection1Key;
	private int connection2Key;
	private int connection3Key;
	private int connection4Key;
	private int pattern;

	public Room(String roomId, boolean isVisited, String description, String descriptionVisited,
			ArrayList<String> items, String puzzleID, String monsterID, String connection1, String connection2,
			String connection3, String connection4, boolean connection1Locked, boolean connection2Locked, boolean connection3Locked,
			boolean connection4Locked, int connection1Key, int connection2Key, int connection3Key, int connection4Key, int pattern)
	{
		this.roomId = roomId;
		this.isVisited = isVisited;
		this.description = description;
		this.descriptionVisited = descriptionVisited;
		this.items = items;
		this.puzzleID = puzzleID;
		this.monsterID = monsterID;
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.connection3 = connection3;
		this.connection4 = connection4;
		this.connection1Locked = connection1Locked;
		this.connection2Locked = connection2Locked;
		this.connection3Locked = connection3Locked;
		this.connection4Locked = connection4Locked;
		this.connection1Key = connection1Key;
		this.connection2Key = connection2Key;
		this.connection3Key = connection3Key;
		this.connection4Key = connection4Key;
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

		if (!connection1.equals("0"))
		{
			des = connection1;
		}
		if (!connection2.equals("0"))
		{
			des += " " + connection2;
		}
		if (!connection3.equals("0"))
		{
			des += " " + connection3;
		}
		if (!connection4.equals("0"))
		{
			des += " " + connection4;
		}

		return des;
	}

	public String getNorthRoomID()
	{
		return connection1;
	}

	public String getEastRoomID()
	{
		return connection2;
	}

	public String getSouthRoomID()
	{
		return connection3;
	}

	public String getWestRoomID()
	{
		return connection4;
	}

	public boolean isNorthLocked()
	{
		return connection1Locked;
	}

	public void setNorthLocked(boolean northLocked)
	{
		this.connection1Locked = northLocked;
	}

	public boolean isEastLocked()
	{
		return connection2Locked;
	}

	public void setEastLocked(boolean eastLocked)
	{
		this.connection2Locked = eastLocked;
	}

	public boolean isSouthLocked()
	{
		return connection3Locked;
	}

	public void setSouthLocked(boolean southLocked)
	{
		this.connection3Locked = southLocked;
	}

	public boolean isWestLocked()
	{
		return connection4Locked;
	}

	public void setWestLocked(boolean westLocked)
	{
		this.connection4Locked = westLocked;
	}

	public int getNorthKey()
	{
		return connection1Key;
	}

	public void setNorthKey(int northKey)
	{
		this.connection1Key = northKey;
	}

	public int getEastKey()
	{
		return connection2Key;
	}

	public void setEastKey(int eastKey)
	{
		this.connection2Key = eastKey;
	}

	public int getSouthKey()
	{
		return connection3Key;
	}

	public void setSouthKey(int southKey)
	{
		this.connection3Key = southKey;
	}

	public int getWestKey()
	{
		return connection4Key;
	}

	public void setWestKey(int westKey)
	{
		this.connection4Key = westKey;
	}
	
	public int getPattern()
	{
		return pattern;
	}

}
