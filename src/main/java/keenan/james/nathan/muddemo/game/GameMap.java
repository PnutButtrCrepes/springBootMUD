package keenan.james.nathan.muddemo.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class GameMap
{
	ArrayList<ArrayList<String>> map;
	
	public int spawnX;
	public int spawnY;
	
	public GameMap(String mapName)
	{
		map = new ArrayList<ArrayList<String>>();
		try
		{
			loadMapFromFile(mapName);
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public int getSpawnX() { return spawnX; }
	
	public int getSpawnY() { return spawnY; }
	
	public boolean isTileWalkable(int x, int y)
	{
		int correctedY = map.size() - 1 - y;
		if (correctedY < 0 || correctedY >= map.size() || x < 0 || x >= map.get(correctedY).size())
			return false;
		
		return !doesTileContain(x, y, "X");
	}
	
	public String getTileDescription(int x, int y)
	{
		int correctedY = map.size() - 1 - y;
		if (correctedY < 0 || correctedY >= map.size() || x < 0 || x >= map.get(correctedY).size())
			return "";
		
		String description = "";
		
		if (doesTileContain(x, y, "F"))
			description += "there are forests";
		
		if (doesTileContain(x, y, "P"))
			description += "there are plains";
		
		if (doesTileContain(x, y, "FP"))
			description += "there are forests and plains";
		
		return description;
	}
	
	public String getSurroundingTileDescriptions(GameCharacter gameCharacter)
	{
		int x = gameCharacter.getX();
		int y = gameCharacter.getY();
		
		String description = "";
		
		switch (gameCharacter.getFacingDirection())
		{
		case NORTH:
			description += capitalize(getTileDescription(x, y)) + " around you.";
			if (isTileWalkable(x, y + 1))
				description += " Ahead of you, " + getTileDescription(x, y + 1) + ".";
			if (isTileWalkable(x - 1, y))
				description += " To your left, " + getTileDescription(x - 1, y) + ".";
			if (isTileWalkable(x + 1, y))
				description += " To your right, " + getTileDescription(x + 1, y) + ".";
			break;
			
		case EAST:
			description += capitalize(getTileDescription(x, y)) + " around you.";
			if (isTileWalkable(x + 1, y))
				description += " Ahead of you, " + getTileDescription(x + 1, y) + ".";
			if (isTileWalkable(x, y + 1))
				description += " To your left, " + getTileDescription(x, y + 1) + ".";
			if (isTileWalkable(x, y - 1))
				description += " To your right, " + getTileDescription(x, y - 1) + ".";
			break;
			
		case SOUTH:
			description += capitalize(getTileDescription(x, y)) + " around you.";
			if (isTileWalkable(x, y - 1))
				description += " Ahead of you, " + getTileDescription(x, y - 1) + ".";
			if (isTileWalkable(x + 1, y))
				description += " To your left, " + getTileDescription(x + 1, y) + ".";
			if (isTileWalkable(x - 1, y))
				description += " To your right, " + getTileDescription(x - 1, y) + ".";
			break;
			
		case WEST:
			description += capitalize(getTileDescription(x, y)) + " around you.";
			if (isTileWalkable(x - 1, y))
				description += " Ahead of you, " + getTileDescription(x - 1, y) + ".";
			if (isTileWalkable(x, y - 1))
				description += " To your left, " + getTileDescription(x, y - 1) + ".";
			if (isTileWalkable(x, y + 1))
				description += " To your right, " + getTileDescription(x, y + 1) + ".";
			break;
		}
		
		return description;
	}
	
	private String capitalize(String string)
	{
		String firstCharacter = string.substring(0, 1);
		return string.replaceFirst(firstCharacter, firstCharacter.toUpperCase());
	}
	
	private boolean doesTileContain(int x, int y, String characteristic)
	{
		int correctedY = map.size() - 1 - y;
		return map.get(correctedY).get(x).contains(characteristic);
	}
	
	private void loadMapFromFile(String mapName) throws Exception
	{
		File mapFile = new File("./src/main/resources/maps/" + mapName + ".txt");
		if (!mapFile.exists())
			throw new Exception();
		
		// create a representation of the map from top to bottom
		
		BufferedReader mapReader = new BufferedReader(new FileReader(mapFile));
		String mapLine;
		while ((mapLine = mapReader.readLine()) != null)
		{
			String[] mapLineTiles = mapLine.toUpperCase().split("[\t]+");
			ArrayList<String> mapLineTilesList = new ArrayList<String>(Arrays.asList(mapLineTiles));
			map.add(mapLineTilesList);
		}
		mapReader.close();
		
		for (int y = 0; y < map.size(); y++)
			for (int x = 0; x < map.get(y).size(); x++)
				if (map.get(y).get(x).contains("S"))
				{
					spawnX = x;
					spawnY = map.size() - 1 - y;
				}
	}
}
