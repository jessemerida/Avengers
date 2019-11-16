package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
    private String roomId;
    private boolean isVisited;
    private String description;
    private String descriptionVisited;
    private ArrayList<String> items;
    private String puzzleID;
    private String monsterID;
    // TODO refactor to connection number
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

    public Room(String roomId, boolean isVisited, String description, String descriptionVisited, ArrayList<String> items, String puzzleID, String monsterID, String connection1, String connection2, String connection3, String connection4, boolean connection1Locked, boolean connection2Locked, boolean connection3Locked, boolean connection4Locked, int connection1Key, int connection2Key, int connection3Key, int connection4Key, int pattern) {
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

    public String getRoomId() {
        return roomId;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public String getDescription() {
        if (isVisited == true) {
            return descriptionVisited;
        } else {
            return description;
        }
    }

    public void pickupItem(String item) {
        items.add(item);
    }

    public void dropItem(String item) throws InvalidItemException {
        if (items.contains(item) == false) {
            throw new InvalidItemException(item + " is not here");
        } else {
            items.remove(items.indexOf(item));
        }
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public String getPuzzleId() {
        return puzzleID;
    }

    public String getMonsterID() {
        return monsterID;
    }

    public ArrayList<String> getValidConnections() {
        ArrayList<String> connections = new ArrayList<String>();

        if (!connection1.equals("0")) {
            connections.add(connection1);
        }
        if (!connection2.equals("0")) {
            connections.add(connection2);
        }
        if (!connection3.equals("0")) {
            connections.add(connection3);
        }
        if (!connection4.equals("0")) {
            connections.add(connection4);
        }

        return connections;
    }

    public ArrayList<String> getAllConnections() {
        ArrayList<String> connections = new ArrayList<String>();

        connections.add(connection1);
        connections.add(connection2);
        connections.add(connection3);
        connections.add(connection4);

        return connections;
    }

    public String getConnection1ID() {
        return connection1;
    }

    public String getConnection2ID() {
        return connection2;
    }

    public String getConnection3ID() {
        return connection3;
    }

    public String getConnection4ID() {
        return connection4;
    }

    public boolean isConnection1Locked() {
        return connection1Locked;
    }

    public void setConnection1Locked(boolean northLocked) {
        this.connection1Locked = northLocked;
    }

    public boolean isConnection2Locked() {
        return connection2Locked;
    }

    public void setConnection2Locked(boolean eastLocked) {
        this.connection2Locked = eastLocked;
    }

    public boolean isConnection3Locked() {
        return connection3Locked;
    }

    public void setConnection3Locked(boolean southLocked) {
        this.connection3Locked = southLocked;
    }

    public boolean isConnection4Locked() {
        return connection4Locked;
    }

    public void setConnection4Locked(boolean westLocked) {
        this.connection4Locked = westLocked;
    }

    public int getConnection1Key() {
        return connection1Key;
    }

    public int getConnection2Key() {
        return connection2Key;
    }

    public int getConnection3Key() {
        return connection3Key;
    }

    public int getConnection4Key() {
        return connection4Key;
    }

    public int getPattern() {
        return pattern;
    }

    public void toFile() throws IOException
	{
		BufferedWriter buffwriter = new BufferedWriter(new FileWriter("saveRooms.txt", true));
		buffwriter.append("#" + roomId + "\n");
		buffwriter.append("@v" + isVisited + "\n");
		String desc = description;
		desc = desc.replaceAll("\n", "\n~");
		buffwriter.append("~" + desc + "\n");
		String desc2 = descriptionVisited;
		desc = desc2.replaceAll("\n", "\n=-");
		buffwriter.append("=-" + desc2 + "\n");
		if (items.size() > 0)
		{
			for (int i = 0; i < items.size(); i++)
			{
				if (i == 0)
					buffwriter.append("@i" + items.get(i));
				else
					buffwriter.append(", " + items.get(i));
			}
		}
		else
			buffwriter.append("@i0");
		buffwriter.append("\n@p" + puzzleID + "\n");
		buffwriter.append("@m" + monsterID + "\n");
		buffwriter.append("@c1" + connection1 + "\n");
		buffwriter.append("@c2" + connection2 + "\n");
		buffwriter.append("@c3" + connection3 + "\n");
		buffwriter.append("@c4" + connection4 + "\n");
		buffwriter.append("@l1" + connection1Locked + "\n");
		buffwriter.append("@l2" + connection2Locked + "\n");
		buffwriter.append("@l3" + connection3Locked + "\n");
		buffwriter.append("@l4" + connection4Locked + "\n");
		buffwriter.append("@k1" + connection1Key + "\n");
		buffwriter.append("@k2" + connection2Key + "\n");
		buffwriter.append("@k3" + connection3Key + "\n");
		buffwriter.append("@k4" + connection4Key + "\n");
		buffwriter.append("@rn" + pattern + "\n");
		buffwriter.append("+\n");
		buffwriter.close();
	}
}
