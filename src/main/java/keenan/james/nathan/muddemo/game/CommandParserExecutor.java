package keenan.james.nathan.muddemo.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandParserExecutor
{
	public static record CommandMap(String[] commandTexts, int numArgs, boolean isLastArgEndless, Command.CommandType commandType) {};
	
	// words to be automatically excluded from commands
	public static String[] articlesAndPrepositions = new String[] { "aboard", "about", "above", "across", "after", "against", "along", "amid",
			"among", "anti", "around", "as", "at", "before", "behind", "below", "beneath", "beside", "besides", "between", "beyond",
			"but", "by", "concerning", "considering", "despite", "during", "except", "excepting", "excluding", "following",
			"for", "from", "in", "inside", "into", "like", "minus", "near", "of", "off", "on", "onto", "opposite", "outside", "over",
			"past", "per", "plus", "regarding", "round", "save", "since", "than", "through", "to", "toward", "towards", "under",
			"underneath", "unlike", "until", "upon", "versus", "via", "with", "within", "without", "a", "an", "the" };
	
	// maps different text to commands
	public static CommandMap[] commandMaps = new CommandMap[] { new CommandMap(new String[] { "help" }, 0, false, Command.CommandType.HELP),
																new CommandMap(new String[] { "look", "regard" }, 0, false, Command.CommandType.LOOK),
																new CommandMap(new String[] { "forward" }, 0, false, Command.CommandType.FORWARD),
																new CommandMap(new String[] { "back" }, 0, false, Command.CommandType.BACK),
																new CommandMap(new String[] { "left" }, 0, false, Command.CommandType.LEFT),
																new CommandMap(new String[] { "right" }, 0, false, Command.CommandType.RIGHT),
																new CommandMap(new String[] { "up" }, 0, false, Command.CommandType.UP),
																new CommandMap(new String[] { "down" }, 0, false, Command.CommandType.DOWN),
																new CommandMap(new String[] { "say" }, 1, true, Command.CommandType.SAY),
																new CommandMap(new String[] { "tell" }, 2, true, Command.CommandType.TELL) };
	
	// this is useless, delete after moving commands to maps
	public static String[] validCommandText = new String[] { "help",
														"look", /* "search", "examine", "exa", "investigate", */
														"forward", "back", "left", "right", "up", "down",
														"say", "tell",
														/*
														"take", "pick up",
														"equip", "wield", "wear",
														"drop",
														"eat", "drink", "apply", "use" */ };
	
	private CommandParserExecutor() {}
	
	public static String parseAndExecuteCommand(String command, GameMap gameMap, GameCharacter gameCharacter)
	{
		Command parsedCommand = parseCommand(command);
		return executeCommand(parsedCommand, gameMap, gameCharacter);
	}
	
	private static Command parseCommand(String command)
	{
		// trim and separate the words
		String[] wordsArray = command.trim().split(" ");
		ArrayList<String> words = new ArrayList<String>(Arrays.asList(wordsArray));
		
		// remove unnecessary words
		for (int i = 0; i < words.size();)
			if (Arrays.asList(articlesAndPrepositions).contains(words.get(i)))
				words.remove(i);
			else
				i++;
		
		// see which command matches most closely
		
		Command.CommandType commandType = Command.CommandType.NONE;
		String argumentString = "";
		
		ArrayList<String> wordsCopy = null;
		CommandLoop:
		for (CommandMap commandMap : commandMaps)
			PossibleTextLoop:
			for (String commandText : commandMap.commandTexts)
			{
				String[] commandTextArray = commandText.split(" ");
				
				wordsCopy = (ArrayList<String>) words.stream().collect(Collectors.toList());
				for (int i = 0; i < commandTextArray.length; i++)
					if (!words.get(i).equals(commandTextArray[i]))
						continue PossibleTextLoop;
					else
						wordsCopy.remove(words.get(i));
				
				// the text of one of the CommandMap possibilities has been matched, do rudimentary check on arguments
				
				if (words.size() < commandMap.numArgs + 1)
					continue;
				
				if (words.size() > 1 && commandMap.numArgs == 0)
					continue;
				
				// it would seem that the right command has been found!
				
				commandType = commandMap.commandType;
				for (String word : wordsCopy)
					argumentString += word + " ";
				argumentString.trim();
				break CommandLoop;
			}
		
		return new Command(commandType, argumentString);
	}
	
	private static String executeCommand(Command command, GameMap gameMap, GameCharacter gameCharacter)
	{
		String returnText = "";
		
		switch (command.commandType)
		{
		case NONE:
			break;
		
		case HELP:
			returnText = "Here is some placeholder help text!";
			break;
			
		case LOOK:
			returnText = gameMap.getSurroundingTileDescriptions(gameCharacter);
			break;
			
		case FORWARD:
			if (gameCharacter.moveForward(gameMap))
				returnText = "You move forward. " + gameMap.getSurroundingTileDescriptions(gameCharacter);
			else
				returnText = "You are unable to move forward.";
			break;
			
		case BACK:
			if (gameCharacter.moveBack(gameMap))
				returnText = "You turn around and go backward. " + gameMap.getSurroundingTileDescriptions(gameCharacter);
			else
				returnText = "You are unable to move back.";
			break;
			
		case LEFT:
			if (gameCharacter.moveLeft(gameMap))
				returnText = "You turn left. " + gameMap.getSurroundingTileDescriptions(gameCharacter);
			else
				returnText = "You are unable to move left.";
			break;
			
		case RIGHT:
			if (gameCharacter.moveRight(gameMap))
				returnText = "You turn right. " + gameMap.getSurroundingTileDescriptions(gameCharacter);
			else
				returnText = "You are unable to move right.";
			break;
			
		case UP:
			returnText = "You move up. " + gameMap.getTileDescription(gameCharacter.getX(), gameCharacter.getY()) + ".";
			break;
			
		case DOWN:
			returnText = "You move down. " + gameMap.getTileDescription(gameCharacter.getX(), gameCharacter.getY()) + ".";
			break;
			
		case SAY:
			break;
			
		case TELL:
			break;
			
		default:
			break;
		}
		
		return returnText;
	}
}
