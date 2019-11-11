package Model;

import java.util.Random;

public class Puzzle
{
	private String id;
	private String question;
	private String answer;
	private String passMessage;
	private String failMessage;
	private int attemptsAllowed;
	private String hint;
	private boolean solved;
	private int damageMin;
	private int damageMax;

	public Puzzle(String id, String question, String answer, String passMessage, String failMessage, int attempts,
			String hint, boolean solved, int damageMin, int damageMax)
	{
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.passMessage = passMessage;
		this.failMessage = failMessage;
		this.attemptsAllowed = attempts;
		this.hint = hint;
		this.solved = solved;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
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

	public int getDamage()
	{
		Random r = new Random();
		return r.nextInt((damageMax - damageMin) + 1) + damageMin;
	}
	
	public String getHint()
	{
		return hint;
	}

	public String solvePuzzle(String text) throws InvalidPuzzleException
	{
		if (text.equalsIgnoreCase(answer))
		{
			setSolved(true);
			return passMessage;
		} else
		{
			return failMessage;
		}
	}

}
