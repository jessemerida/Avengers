package Model;

public class InvalidPuzzleException extends GameException
{
	private static final long serialVersionUID = -1493652339199331179L;

	public InvalidPuzzleException()
	{
		super();
	}
	
	public InvalidPuzzleException(String msg)
	{
		super(msg);
	}
}
