package zGame;

public class Puzzle
{
	private String id;
	private String question;
	private String answer;
	private String passMessage;
	private String failMessage;
	private int attemptsAllowed;
	private boolean solved;

	public Puzzle(String id, String question, String answer, String passMessage, String failMessage, int attempts, boolean solved)
	{
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.passMessage = passMessage;
		this.failMessage = failMessage;
		this.attemptsAllowed = attempts;
		this.solved = solved;
	}

	public boolean isSolved()
	{
		return solved;
	}

	public void setSolved(boolean solved)
	{
		this.solved = solved;
	}

	public String getId()
	{
		return id;
	}

	public String getQuestion()
	{
		return question;
	}

	public String getAnswer()
	{
		return answer;
	}

	public int getAttempts()
	{
		return attemptsAllowed;
	}

	public String solvePuzzle(String text) throws InvalidPuzzleException
	{
		if(text.equalsIgnoreCase(answer))
		{
			setSolved(true);
			return passMessage;
		}
		else
		{
			return failMessage;
		}
	}

}