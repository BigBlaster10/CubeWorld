package me.bigblaster10.inventory;

public enum ArmorType {

	DEFAULTTEST(1);
	
	double armorMultiplier;
	ArmorType(double armorMultiplier){
		this.armorMultiplier = armorMultiplier;
	}
	
	public double getArmorMultiplier(){
		return armorMultiplier;
	}
}
