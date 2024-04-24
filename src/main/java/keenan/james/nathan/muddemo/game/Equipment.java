package keenan.james.nathan.muddemo.game;

public class Equipment
{
	public enum EquipmentType { MAIN_HAND, OFF_HAND, ARMOR, ACCESSORY }
	public record PassiveEffects(int strMod, int dexMod, int conMod, int intMod, int widMod, int chaMod, int speedMod, int maxHealthMod, int healthMod) {};
	
	public EquipmentType equipmentType;
	public PassiveEffects passiveEffects;
	public int attack;
	public int armor;
}
