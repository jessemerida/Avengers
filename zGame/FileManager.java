package zGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FileManager
{
	public static Player readPlayer(String fileName) throws FileNotFoundException
	{
		Player player = null;

		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);

			// skip first line
			inFile.nextLine();

			// add the rooms to a list

			String[] tokens = inFile.nextLine().split("~");
			ArrayList<String> items = new ArrayList<String>(Arrays.asList(tokens[3].trim().split(",")));
			player = new Player(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), items,
					Integer.parseInt(tokens[4]));

			inFile.close();
			fileReader.close();

		} catch (IOException e)
		{
			throw new FileNotFoundException("Player file not found.");
		}

		return player;

	}
	
	public static Player readPlayer2(String fileName) throws FileNotFoundException
	{
		Player player = null;
		String currLine = null;
		
		String playername = null;
		int currenthealthPoints = null;
		int maxHealthPoints = null;
		ArrayList<String> items = new ArrayList<String>();
		int defaultAttack = null;
		String equippedItem = null;
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);
			
			while (inFile.hasNextLine())
			{
				currLine = inFile.nextLine();
				if (!currLine.contains("+"))
				{
					if (currLine.contains("@n"))
					{
						playername = currLine.substring(2);
					}
					if (currLine.contains("@hp"))
					{
						currenthealthPoints = Integer.parseInt(currLine.substring(3));
					}
					if (currLine.contains("@mhp"))
					{
						maxHealthPoints = Integer.parseInt(currLine.substring(4));
					}
					if (currLine.contains("@i"))
					{
						items = Arrays.asList(currLine.substring(2).split(","));
					}
					if (currLine.contains("@ap"))
					{
						defaultAttack = Integer.parseInt(currLine.substring(3));
					}					
				}
				if (currLine.contains("+"))
				{
					player = new Player(playername, currenthealthPoints, maxHealthPoints, items, defaultAttack, equippedItems);
					playername = null;
					currenthealthPoints = null;
					maxHealthPoints = null;
					items = new ArrayList<String>();
					defaultAttack = null;
					equippedItem = null;
				}
			}
		} catch (IOException e)
		{
			throw new FileNotFoundException("Player file not found.");
		} finally
		{
			inFile.close();
			fileReader.close();
		}

		return player;
	}

	public static ArrayList<Room> readRooms(String fileName) throws FileNotFoundException
	{
		ArrayList<Room> allRooms = new ArrayList<Room>();

		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);

			// skip first line
			inFile.nextLine();

			// add the rooms to a list
			while (inFile.hasNextLine())
			{
				String[] tokens = inFile.nextLine().trim().split("~");
				ArrayList<String> itemsList = new ArrayList<String>(Arrays.asList(tokens[4].trim().split(",")));
				// not all rooms will have an item, so noItems indicates that the item list is
				// empty
				if (itemsList.get(0).equals("0"))
				{
					itemsList.clear();
				}

				String[] roomTokens = tokens[7].trim().split(",");
				String[] roomLockedTokens = tokens[8].trim().split(",");
				String[] roomKeys = tokens[9].trim().split(",");

				allRooms.add(new Room(tokens[0], Boolean.parseBoolean(tokens[1]), tokens[2], tokens[3], itemsList,
						tokens[5], tokens[6], roomTokens[0], roomTokens[1], roomTokens[2], roomTokens[3],
						Boolean.parseBoolean(roomLockedTokens[0]), Boolean.parseBoolean(roomLockedTokens[1]),
						Boolean.parseBoolean(roomLockedTokens[2]), Boolean.parseBoolean(roomLockedTokens[3]),
						Integer.parseInt(roomKeys[0]), Integer.parseInt(roomKeys[1]), Integer.parseInt(roomKeys[2]),
						Integer.parseInt(roomKeys[3]), Integer.parseInt(tokens[10])));
			}

			inFile.close();
			fileReader.close();

		} catch (IOException e)
		{
			throw new FileNotFoundException("Room file not found.");
		}

		return allRooms;

	}

	public static ArrayList<Item> readItems(String fileName) throws FileNotFoundException
	{
		ArrayList<Item> allItems = new ArrayList<Item>();

		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);

			// skip first line
			inFile.nextLine();

			// add the rooms to a list
			while (inFile.hasNextLine())
			{
				String[] tokens = inFile.nextLine().trim().split("~");
				allItems.add(new Item(tokens[0], ItemType.valueOf(tokens[1].toUpperCase()), Integer.parseInt(tokens[2]),
						tokens[3]));
			}

			inFile.close();
			fileReader.close();

		} catch (IOException e)
		{
			throw new FileNotFoundException("Item file not found.");
		}

		return allItems;
	}

	public static ArrayList<Item> readItems2(String fileName) throws FileNotFoundException
	{
		ArrayList<Item> allItems = new ArrayList<Item>();
		String currLine = null;
		
		String itemName = null;
		ItemType itemType = null;
		int itemDelta = null;
		String description = null;
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);
			
			while (inFile.hasNextLine())
			{
				currLine = inFile.nextLine();
				if (!currLine.contains("+"))
				{
					if (currLine.contains("@i"))
					{
						itemName = currLine.substring(2);
					}
					if (currLine.contains("@t"))
					{
						itemType = ItemType.valueOf(currLine.substring(2).toUpperCase());
					}
					if (currLine.contains("@dv"))
					{
						itemDelta = Integer.parseInt(currLine.substring(3));
					}
					if (currLine.contains("~"))
					{
						if (description.isEmpty())
							description += currLine.substring(1);
						else
							description += "\n" + currLine.substring(1);
					}					
				}
				if (currLine.contains("+"))
				{
					allItems.add(new Item(itemName, itemType, itemDelta, description));
					itemName = null;
					itemType = null;
					itemDelta = null;
					description = null;
				}
			}
		} catch (IOException e)
		{
			throw new FileNotFoundException("Item file not found.");
		} finally
		{
			inFile.close();
			fileReader.close();
		}

		return allItems;
	}
	
	public static ArrayList<Puzzle> readPuzzles(String fileName) throws FileNotFoundException
	{
		ArrayList<Puzzle> allPuzzles = new ArrayList<Puzzle>();

		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);

			// skip first line
			inFile.nextLine();

			// add the rooms to a list
			while (inFile.hasNextLine())
			{
				String[] tokens = inFile.nextLine().trim().split("~");
				allPuzzles.add(new Puzzle(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4],
						Integer.parseInt(tokens[5]), tokens[6], Boolean.parseBoolean(tokens[7]),
						Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9])));
			}

			inFile.close();
			fileReader.close();

		} catch (IOException e)
		{
			throw new FileNotFoundException("Puzzle file not found.");
		}
		return allPuzzles;

	}

	public static ArrayList<Puzzle> readPuzzles2(String fileName) throws FileNotFoundException
	{
		ArrayList<Puzzle> allPuzzles = new ArrayList<Puzzle>();
		String currLine = null;
		
		String id = null;
		String question = null;
		String answer = null;
		String passMessage = null;
		String failMessage = null;
		int attemptsAllowed = null;
		String hint = null;
		boolean solved = null;
		int damageMin = null;
		int damageMax = null;
		
		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);
			
			while (inFile.hasNextLine())
			{
				currLine = inFile.nextLine();
				if (!currLine.contains("+"))
				{
					if (currLine.contains("@p"))
					{
						id = Integer.parseInt(currLine.substring(2));
					}
					if (currLine.contains("~"))
					{
						if (question.isEmpty())
							question += currLine.substring(1);
						else
							question += "\n" + currLine.substring(1);
					}
					if (currLine.contains("@a"))
					{
						answer = currLine.substring(2);
					}
					if (currLine.contains("@pmsg"))
					{
						passMessage = currLine.substring(5);
					}
					if (currLine.contains("@fmsg"))
					{
						failMessage = currLine.substring(5);
					}					
					if (currLine.contains("@at"))
					{
						attemptsAllowed = Integer.parseInt(currLine.substring(3));
					}
					if (currLine.contains("@h"))
					{
						hint = currLine.substring(2);
					}
					if (currLine.contains("@ss"))
					{
						solved = Boolean.parseBoolean(currLine.substring(3));
					}
					if (currLine.contains("@dmin"))
					{
						damageMin = Integer.parseInt(currLine.substring(5));
					}
					if (currLine.contains("@dmax"))
					{
						damageMax = Integer.parseInt(currLine.substring(5));
					}
				}
				if (currLine.contains("+"))
				{
					allPuzzles.add(new Puzzle(id, question, answer, passMessage, failMessage, attemptsAllowed, hint, solved, damageMin, damageMax));
					id = null;
					question = null;
					answer = null;
					passMessage = null;
					failMessage = null;
					attemptsAllowed = null;
					hint = null;
					solved = null;
					damageMin = null;
					damageMax = null;
				}
			}
		} catch (IOException e)
		{
			throw new FileNotFoundException("Puzzle file not found.");
		} finally
		{
			inFile.close();
			fileReader.close();
		}

		return allPuzzles;
	}
	
	public static ArrayList<Monster> readMonsters(String fileName) throws FileNotFoundException
	{
		ArrayList<Monster> allMonsters = new ArrayList<Monster>();

		try
		{
			FileReader fileReader = new FileReader(fileName);
			Scanner inFile = new Scanner(fileReader);

			// skip first line
			inFile.nextLine();

			// add the rooms to a list
			while (inFile.hasNextLine())
			{
				String[] tokens = inFile.nextLine().trim().split("~");
				allMonsters.add(new Monster(tokens[0], tokens[1], tokens[2], Boolean.parseBoolean(tokens[3]),
						Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));
			}

			inFile.close();
			fileReader.close();

		} catch (IOException e)
		{
			throw new FileNotFoundException("Monster file not found.");
		}
		return allMonsters;

	}

}
