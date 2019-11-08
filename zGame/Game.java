package zGame;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game
{
	private Scanner input = new Scanner(System.in);
	private Map map;

	public static void main(String[] args)
	{
		Game game = new Game();

		game.startGame();

		game.input.close();
	}

	private void startGame()
	{
		try
		{
			map = new Map();
			System.out.println(map.getCurrentRoomDescription());
			System.out.println(map.getCurrentRoomItems());
			System.out.println("Connections:" + map.getCurrentRoomConnections());
		} catch (GameException e)
		{
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e)
		{
			System.err.println(e.getMessage());
			System.exit(0);
		}

		while (true)
		{
			try
			{
				activateCommands();
			} catch (GameException e)
			{
				System.out.println(e.getMessage());
			}
		}

	}

	private void activateCommands() throws GameException
	{
		try
		{
			Thread.sleep(10);
		} catch (InterruptedException e)
		{
			System.out.println("THIS SHOULD NEVER PRINT");
		}

		System.err.println("Enter Command:");
		String command = input.nextLine();

		if (command.equalsIgnoreCase("Commands"))
		{
			System.out.println("-Commands-" + "move to <roomNumber>" + "\nstats: Player Stats" + "\nI: Inventory"
					+ "\npickup <item>: add <item> to inventory" + "\ndrop <item>: Discard <item> to current room"
					+ "\ninspect <item>: Look at <item>" + "\nunequip: Empty players hand"
					+ "\nequip <item>: Place <item> from inventory to players hand" + "\nheal: Use consumable"
					+ "\nexplore: List items in the room");

		} else if (command.contains("move to"))
		{
			System.out.println(map.movePlayerTo(command.split(" ")[2]));
			System.out.println(map.getCurrentRoomItems());
			System.out.println("Connections: " + map.getCurrentRoomConnections());
		} else if (command.equalsIgnoreCase("stats"))
		{
			System.out.println(map.getPlayerStats());
		} else if (command.equalsIgnoreCase("I"))
		{
			System.out.println(map.getPlayerInventory().toString());
		} else if (command.contains("pickup"))
		{
			System.out.println(map.pickupPlayerItem(command.split(" ")[1]));
		} else if (command.contains("drop"))
		{
			System.out.println(map.dropPlayerItem(command.split(" ")[1]));
		} else if (command.contains("inspect"))
		{
			System.out.println(map.inspectItem(command.split(" ")[1]));
		} else if (command.equalsIgnoreCase("unequip"))
		{
			System.out.println(map.unequipPlayerItem());
		} else if (command.contains("equip"))
		{
			System.out.println(map.equipPlayerItem(command.split(" ")[1]));
		} else if (command.equalsIgnoreCase("heal"))
		{
			System.out.println(map.playerHeal());
		} else if (command.equalsIgnoreCase("explore"))
		{
			System.out.println(map.getCurrentRoomDescription());
			System.out.println(map.getCurrentRoomItems());
			System.out.println("Connections:" + map.getCurrentRoomConnections());
		} else if (command.contains("unlock"))
		{
			System.out.println(map.playerUnlocksDoor(command.split(" ")[1]));
		} else
		{
			System.out.println("Please ented a valid command. \nEnter 'Commands' to see a list of valid commands");
		}

		if (map.isCurrentRoomMonsterDead() == false)
		{
			findAndActivateMonster();
		}

		if (map.isCurrentRoomPuzzleSolved() == false)
		{
			if (findAndActivatePuzzle() == false)
			{
				System.out.println(map.movePlayerToPreviousRoom());
			}
		}

	}

	private boolean findAndActivatePuzzle() throws InvalidPuzzleException
	{
		for (int i = 0; i < map.getPuzzleAttempts() && map.isCurrentRoomPuzzleSolved() == false; i++)
		{
			System.out.println(map.getPuzzleQuestion());

			String answer = input.nextLine();
			if (answer.equalsIgnoreCase("exit"))
			{
				return false;
			}

			System.out.println(map.solveCurrentRoomPuzzle(answer));
		}

		if (map.isCurrentRoomPuzzleSolved() == false)
		{
			return false;
		}
		return true;
	}

	// TODO return bool if exit command
	private void findAndActivateMonster()
	{
		while (map.isCurrentRoomMonsterDead() == false)
		{
			try
			{
				System.out.println("-Player-\n" + map.getPlayerStats() + "\n-Monster-\n" + map.getMonsterStats());
				Thread.sleep(10);
				System.err.println("Enter Command:");

				String command = input.nextLine();

				if (command.equalsIgnoreCase("commands"))
				{
					System.out.println("\n-Commands-" + "\nAttack: Attack the monster"
							+ "\nHeal: Give yourself some health" + "\nStudy: Study the monster" + "\nI: Inventory"
							+ "\nInspect <item>: Look at <item>" + "\nUnequip: Empty players hand"
							+ "\nEquip <item>: Place <item> from inventory to players hand");

				} else if (command.equalsIgnoreCase("attack"))
				{
					System.out.println(map.playerAttacksMonster());

					if (map.isCurrentRoomMonsterDead() == false)
					{
						System.out.println(map.monsterAttacksPlayer());
					}

				} else if (command.equalsIgnoreCase("heal"))
				{
					System.out.println(map.playerHeal());

					if (map.isCurrentRoomMonsterDead() == false)
					{
						System.out.println(map.monsterAttacksPlayer());
					}

				} else if (command.equalsIgnoreCase("study"))
				{
					System.out.println(map.getMonsterDescription());
				} else if (command.equalsIgnoreCase("i"))
				{
					System.out.println(map.getPlayerInventory().toString());
				} else if (command.contains("inspect"))
				{
					System.out.println(map.inspectItem(command.split(" ")[1]));
				} else if (command.equalsIgnoreCase(("unequip")))
				{
					System.out.println(map.unequipPlayerItem());
				} else if (command.contains("equip"))
				{
					System.out.println(map.equipPlayerItem(command.split(" ")[1]));
				} else
				{
					System.out.println(
							"Please ented a valid command. \nEnter 'Commands' to see a list of valid commands");
				}
			} catch (GameException e)
			{
				System.out.println(e.getMessage());
			} catch (InterruptedException e)
			{
				System.out.println("THIS SHOULD NEVER PRINT");
			}

		}

	}
}
