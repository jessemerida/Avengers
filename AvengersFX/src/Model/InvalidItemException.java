package Model;

public class InvalidItemException extends GameException
{
	private static final long serialVersionUID = -7670914799288150337L;

	public InvalidItemException()
	{
		super();
	}
	
	public InvalidItemException(String msg)
	{
		super(msg);
	}
}
