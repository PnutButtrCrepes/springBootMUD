package keenan.james.nathan.muddemo.game;

public class Item
{
	public record ActiveEffects(int strMod, int dexMod, int conMod, int intMod, int widMod, int chaMod, int speedMod, int maxHealthMod, int healthMod) {};
	public int durationSeconds;
}
