package Model;

public class InvalidMonsterException extends GameException
{
	private static final long serialVersionUID = -6167421114356110303L;

	public InvalidMonsterException()
	{
		super();
	}
	
	public InvalidMonsterException(String msg)
	{
		super(msg);
	}
	
}
