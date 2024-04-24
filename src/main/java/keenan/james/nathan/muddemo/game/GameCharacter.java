package keenan.james.nathan.muddemo.game;

public class GameCharacter
{
	public enum CharacterType { PC, NPC }
	public enum RelativeDirection { FORWARD, RIGHT, BACK, LEFT }
	public enum Direction { NORTH, EAST, SOUTH, WEST }
	
	private CharacterType characterType;
	private String playerSessionId;
	
	private String name;
	private Race race;
	
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	private int speed;
	private int maxHealth;
	private int health;
	
	private Equipment[] equipment; // MAIN HAND, OFF HAND, ARMOR, ACCESSORY so size 4?
	
	private String mapLocation;
	private int x;
	private int y;
	private Direction facingDirection;
	
	public enum Race { HUMAN, ELF, HALF_ORC, DWARF }
	
	public GameCharacter(CharacterType characterType, String playerSessionId)
	{
		this.characterType = characterType;
		this.facingDirection = Direction.NORTH;
		
		switch (characterType)
		{
		case PC:
			this.playerSessionId = playerSessionId;
			break;
		
		case NPC:
			this.playerSessionId = "";
			break;
		}
	}
	
	public boolean move(GameMap gameMap, RelativeDirection moveDirection)
	{
		Direction newDirection = Direction.values()[(facingDirection.ordinal() + moveDirection.ordinal()) % 4];
		
		switch (newDirection)
		{
		case NORTH:
			if (!gameMap.isTileWalkable(x, y + 1))
				return false;
			y++;
			break;
			
		case SOUTH:
			if (!gameMap.isTileWalkable(x, y - 1))
				return false;
			y--;
			break;
		
		case EAST:
			if (!gameMap.isTileWalkable(x + 1, y))
				return false;
			x++;
			break;
			
		case WEST:
			if (!gameMap.isTileWalkable(x - 1, y))
				return false;
			x--;
			break;
		}
		
		facingDirection = newDirection;
		return true;
	}
	
	public boolean moveForward(GameMap gameMap)
	{
		return move(gameMap, RelativeDirection.FORWARD);
	}
	
	public boolean moveBack(GameMap gameMap)
	{
		return move(gameMap, RelativeDirection.BACK);
	}
	
	public boolean moveLeft(GameMap gameMap)
	{
		return move(gameMap, RelativeDirection.LEFT);
	}
	
	public boolean moveRight(GameMap gameMap)
	{
		return move(gameMap, RelativeDirection.RIGHT);
	}
	
	public String getDescription()
	{
		return "a creature";
	}
	
	public CharacterType getCharacterType()
	{
		return characterType;
	}

	public String getPlayerSessionId()
	{
		return playerSessionId;
	}

	public String getName()
	{
		return name;
	}

	public Race getRace()
	{
		return race;
	}

	public int getStrength()
	{
		return strength;
	}

	public int getDexterity()
	{
		return dexterity;
	}

	public int getConstitution()
	{
		return constitution;
	}

	public int getIntelligence()
	{
		return intelligence;
	}

	public int getWisdom()
	{
		return wisdom;
	}

	public int getCharisma()
	{
		return charisma;
	}

	public int getSpeed()
	{
		return speed;
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public int getHealth()
	{
		return health;
	}

	public Equipment[] getEquipment()
	{
		return equipment;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Direction getFacingDirection()
	{
		return facingDirection;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setStats(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma)
	{
		this.strength = strength;
		this.dexterity = dexterity;
		this.constitution = constitution;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
	}
	
	public void modifyStats(int strengthMod, int dexterityMod, int constitutionMod, int intelligenceMod, int wisdomMod, int charismaMod)
	{
		this.strength += strengthMod;
		this.dexterity += dexterityMod;
		this.constitution += constitutionMod;
		this.intelligence += intelligenceMod;
		this.wisdom += wisdomMod;
		this.charisma += charismaMod;
	}
}
