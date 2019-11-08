package zGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Map
{
	private Player player;
	private Room currentRoom;

	private ArrayList<Room> allRooms;
	private ArrayList<Item> allItems;
	private ArrayList<Puzzle> allPuzzles;
	private ArrayList<Monster> allMonsters;

	public Map() throws FileNotFoundException, InvalidRoomException
	{
		player = FileManager.readPlayer("player3.txt");
		allRooms = FileManager.readRooms("rooms3.txt");
		allItems = FileManager.readItems("items3.txt");
		allPuzzles = FileManager.readPuzzles("puzzles3.txt");
		allMonsters = FileManager.readMonsters("monsters3.txt");

		currentRoom = allRooms.get(0);
	}

	private Room getRoom(String roomId) throws InvalidRoomException
	{
		if (roomId.equals("0"))
		{
			throw new InvalidRoomException("You cant go that direction");
		}

		Room room = null;
		for (int i = 0; i < allRooms.size(); i++)
		{
			if (allRooms.get(i).getRoomId().equals(roomId))
			{
				room = allRooms.get(i);
			}
		}

		currentRoom.setVisited(true);
		return room;
	}

	private Item getItem(String itemName) throws InvalidItemException
	{

		Item item = null;
		for (int i = 0; i < allItems.size(); i++)
		{
			if (allItems.get(i).getItemName().equalsIgnoreCase(itemName))
			{
				item = allItems.get(i);
			}
		}

		if (item == null)
		{
			throw new InvalidItemException("This item is not here");
		}

		return item;
	}

	private Puzzle getPuzzle(String puzzleId) throws InvalidPuzzleException
	{
		Puzzle puzzle = null;

		for (int i = 0; i < allPuzzles.size(); i++)
		{
			if (allPuzzles.get(i).getId().equals(puzzleId))
			{
				puzzle = allPuzzles.get(i);
			}
		}

		if (puzzle == null)
		{
			throw new InvalidPuzzleException();
		}

		return puzzle;
	}

	private Monster getMonster(String monsterID) throws InvalidMonsterException
	{
		Monster monster = null;

		for (int i = 0; i < allMonsters.size(); i++)
		{
			if (allMonsters.get(i).getMonsterID().equals(monsterID))
			{
				monster = allMonsters.get(i);
			}
		}

		if (monster == null)
		{
			throw new InvalidMonsterException();
		}

		return monster;
	}

	public String movePlayerTo(String roomID) throws InvalidRoomException
	{
		if (roomID.equals(currentRoom.getNorthRoomID()))
		{
			return movePlayerNorth();
		} else if (roomID.equals(currentRoom.getEastRoomID()))
		{
			return movePlayerEast();
		} else if (roomID.equals(currentRoom.getSouthRoomID()))
		{
			return movePlayerSouth();
		} else if (roomID.equals(currentRoom.getWestRoomID()))
		{
			return movePlayerWest();
		} else
		{
			throw new InvalidRoomException("That room is not valid");
		}
	}

	public String movePlayerNorth() throws InvalidRoomException
	{
		if (currentRoom.isNorthLocked())
		{
			throw new InvalidRoomException("That room is locked");
		}

		currentRoom = getRoom(currentRoom.getNorthRoomID());
		return currentRoom.getDescription();
	}

	public String movePlayerEast() throws InvalidRoomException
	{
		if (currentRoom.isEastLocked())
		{
			throw new InvalidRoomException("That room is locked");
		}

		currentRoom = getRoom(currentRoom.getEastRoomID());
		return currentRoom.getDescription();
	}

	public String movePlayerSouth() throws InvalidRoomException
	{
		if (currentRoom.isSouthLocked())
		{
			throw new InvalidRoomException("That room is locked");
		}

		currentRoom = getRoom(currentRoom.getSouthRoomID());
		return currentRoom.getDescription();
	}

	public String movePlayerWest() throws InvalidRoomException
	{
		if (currentRoom.isWestLocked())
		{
			throw new InvalidRoomException("That room is locked");
		}

		currentRoom = getRoom(currentRoom.getWestRoomID());
		return currentRoom.getDescription();
	}

	public String getCurrentRoomDescription()
	{
		return currentRoom.getDescription();
	}
	
	public ArrayList<String> getCurrentRoomItems()
	{
		return currentRoom.getItems();
	}
	
	public String getCurrentRoomConnections()
	{
		return currentRoom.getConnections();
	}

	public ArrayList<String> getPlayerInventory() throws InvalidItemException
	{
		// TODO loop through items and list out descriptions
		return player.getInventory();
	}

	public String inspectItem(String item) throws InvalidItemException
	{
		if (!currentRoom.getItems().contains(item) && !player.getInventory().contains(item))
		{
			throw new InvalidItemException("This item is not here");
		}
		return getItem(item).getDescription();
	}

	public String pickupPlayerItem(String item) throws InvalidItemException
	{
		currentRoom.dropItem(item);
		return player.pickupItem(item);
	}

	public String dropPlayerItem(String item) throws InvalidItemException
	{
		currentRoom.pickupItem(item);
		return player.dropItem(item);
	}

	public String equipPlayerItem(String item) throws InvalidItemException
	{
		return player.setEquippedItem(item);
	}

	public String unequipPlayerItem()
	{
		return player.clearEquippedItem();
	}

	public boolean isCurrentRoomPuzzleSolved()
	{
		try
		{
			return getPuzzle(currentRoom.getPuzzleId()).isSolved();
		} catch (InvalidPuzzleException e)
		{
			return true;
		}
	}

	public String getPuzzleQuestion() throws InvalidPuzzleException
	{
		return getPuzzle(currentRoom.getPuzzleId()).getQuestion();
	}

	public String solveCurrentRoomPuzzle(String text) throws InvalidPuzzleException
	{
		return getPuzzle(currentRoom.getPuzzleId()).solvePuzzle(text);
	}

	public int getPuzzleAttempts() throws InvalidPuzzleException
	{
		return getPuzzle(currentRoom.getPuzzleId()).getAttempts();
	}

	public boolean isCurrentRoomMonsterDead()
	{
		try
		{
			return getMonster(currentRoom.getMonsterID()).isMonsterDead();
		} catch (InvalidMonsterException e)
		{
			return true;
		}
	}

	public String getMonsterDescription() throws InvalidMonsterException
	{
		String description = "";
		Monster monster = getMonster(currentRoom.getMonsterID());

		description += "Name: " + monster.getName();
		description += "\nDescription:" + monster.getDescription();
		description += "\nHealth: " + monster.getHealthPoints();
		description += "\nDamage: " + monster.getAttackPoints();

		return description;
	}

	public String getPlayerStats()
	{
		return player.getStats();
	}

	public String getMonsterStats() throws InvalidMonsterException
	{
		return getMonster(currentRoom.getMonsterID()).getStats();
	}

	public String playerAttacksMonster() throws InvalidMonsterException
	{
		Monster monster = getMonster(currentRoom.getMonsterID());
		Item equippedItem = null;
		try
		{
			equippedItem = getItem(player.getEquippedItem());
		} catch (InvalidItemException e)
		{

		}

		if (equippedItem == null || equippedItem.getItemType() != ItemType.WEAPON)
		{
			monster.acceptDamage(player.getDefaultAttack());
			return "player did " + player.getDefaultAttack() + " damage";
		} else
		{
			monster.acceptDamage(equippedItem.getItemDelta());
			return "player did " + equippedItem.getItemDelta() + " damage";
		}

	}

	public String monsterAttacksPlayer() throws InvalidMonsterException
	{
		Monster monster = getMonster(currentRoom.getMonsterID());
		player.acceptDamage(monster.getAttackPoints());

		return "monster did " + monster.getAttackPoints() + " damage";
	}

	public String playerHeal() throws InvalidItemException
	{
		Item equippedItem = null;

		try
		{
			equippedItem = getItem(player.getEquippedItem());
		} catch (InvalidItemException e)
		{

		}

		if (equippedItem == null || equippedItem.getItemType() != ItemType.CONSUMABLE)
		{
			throw new InvalidItemException("Consumable must be equipped to use");
		} else
		{
			player.acceptHeal(equippedItem.getItemDelta());
			player.dropItem(player.getEquippedItem());
			player.clearEquippedItem();
		}

		return equippedItem.getItemName() + " is used";
	}

	public String playerUnlocksDoor(String direction) throws InvalidItemException
	{
		Item equippedItem = null;
		try
		{
			equippedItem = getItem(player.getEquippedItem());
		} catch (InvalidItemException e)
		{

		}

		if (equippedItem == null || equippedItem.getItemType() != ItemType.KEY)
		{
			throw new InvalidItemException("Key must be equipped.");
		}

		if (direction.equalsIgnoreCase("north"))
		{
			if (currentRoom.isNorthLocked())
			{
				if (equippedItem.getItemDelta() == currentRoom.getNorthKey())
				{
					currentRoom.setNorthLocked(false);

					player.dropItem(player.getEquippedItem());
					player.clearEquippedItem();

					return "north is now unlocked";
				} else
				{
					throw new InvalidItemException(equippedItem.getItemName() + " is not the right key");
				}
			} else
			{
				throw new InvalidItemException("north is already unlocked");
			}

		} else if (direction.equalsIgnoreCase("east"))
		{
			if (currentRoom.isEastLocked())
			{
				if (equippedItem.getItemDelta() == currentRoom.getEastKey())
				{
					currentRoom.setEastLocked(false);

					player.dropItem(player.getEquippedItem());
					player.clearEquippedItem();

					return "east is now unlocked";
				} else
				{
					throw new InvalidItemException(equippedItem.getItemName() + " is not the right key");
				}
			} else
			{
				throw new InvalidItemException("east is already unlocked");
			}
		} else if (direction.equalsIgnoreCase("south"))
		{
			if (currentRoom.isSouthLocked())
			{
				if (equippedItem.getItemDelta() == currentRoom.getSouthKey())
				{
					currentRoom.setSouthLocked(false);

					player.dropItem(player.getEquippedItem());
					player.clearEquippedItem();

					return "south is now unlocked";
				} else
				{
					throw new InvalidItemException(equippedItem.getItemName() + " is not the right key");
				}
			} else
			{
				throw new InvalidItemException("south is already unlocked");
			}
		} else if (direction.equalsIgnoreCase("west"))
		{
			if (currentRoom.isWestLocked())
			{
				if (equippedItem.getItemDelta() == currentRoom.getWestKey())
				{
					currentRoom.setWestLocked(false);

					player.dropItem(player.getEquippedItem());
					player.clearEquippedItem();

					return "west is not unlocked";
				} else
				{
					throw new InvalidItemException(equippedItem.getItemName() + " is not the right key");
				}
			} else
			{
				throw new InvalidItemException("west is already unlocked");
			}
		} else
		{
			throw new InvalidItemException(direction + " is not a valid direction");
		}
	}
}