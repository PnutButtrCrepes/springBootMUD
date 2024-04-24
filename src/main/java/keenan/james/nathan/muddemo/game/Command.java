package keenan.james.nathan.muddemo.game;

public class Command
{
	public enum CommandType
	{
		NONE,
		HELP,
		LOOK,
		FORWARD, BACK, LEFT, RIGHT, UP, DOWN,
		SAY, TELL
	}
	
	public CommandType commandType;
	public String argumentText;
	
	public Command(CommandType commandType, String argumentText)
	{
		this.commandType = commandType;
	}
}
