package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Puzzle {
    private String id;
    private String question;
    private String answer;
    private String passMessage;
    private String failMessage;
    private int attemptsAllowed;
    private int attemptsRemaining;
    private String hint;
    private boolean solved;
    private int damageMin;
    private int damageMax;

    public Puzzle(String id, String question, String answer, String passMessage, String failMessage, int attempts, String hint, boolean solved, int damageMin, int damageMax) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.passMessage = passMessage;
        this.failMessage = failMessage;
        this.attemptsAllowed = attempts;
        attemptsRemaining = attempts;
        this.hint = hint;
        this.solved = solved;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getAttempts() {
        return attemptsAllowed;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public void reduceAttemptsRemaining() {
        --attemptsRemaining;
    }

    public int getDamage() {
        Random r = new Random();
        return r.nextInt((damageMax - damageMin) + 1) + damageMin;
    }

    public String getHint() {
        return hint;
    }

    public boolean solvePuzzle(String text) throws InvalidPuzzleException {
        if (text.equalsIgnoreCase(answer)) {
            setSolved(true);
            return true;
        } else {
            return false;
        }
    }

    public void toFile() throws IOException
	{
		BufferedWriter buffwriter = new BufferedWriter(new FileWriter("savePuzzles.txt", true));
		buffwriter.append("@p" + id + "\n");
		String desc = question;
		desc = desc.replaceAll("\n", "\n~");
		buffwriter.append("~" + desc + "\n");
		buffwriter.append("@a" + answer + "\n");
		buffwriter.append("@msg" + passMessage + "\n");
		buffwriter.append("@fmsg" + failMessage + "\n");
		buffwriter.append("@try" + attemptsAllowed + "\n");
		buffwriter.append("@h" + hint + "\n");
		buffwriter.append("@ss" + solved + "\n");
		buffwriter.append("@dmin" + damageMin + "\n");
		buffwriter.append("@dmax" + damageMax + "\n");
		buffwriter.append("+\n");
		buffwriter.close();
	}
}
