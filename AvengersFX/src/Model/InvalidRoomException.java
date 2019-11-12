package Model;

public class InvalidRoomException extends GameException
{
	private static final long serialVersionUID = -3245611047254143510L;

	public InvalidRoomException()
	{
		super();
	}
	
	public InvalidRoomException(String msg)
	{
		super(msg);
	}
}
