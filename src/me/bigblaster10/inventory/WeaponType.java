package me.bigblaster10.inventory;

public enum WeaponType {

	SPEAR(1.1, RangeType.MELEE, 2),
	GREATAXE(1.2, RangeType.MELEE, 2),
	SWORD(0.6, RangeType.MELEE, 1),
	SHIELD(0, RangeType.MELEE, 1),
	
	LONGSWORD(1.3, RangeType.MELEE, 2),
	DAGGER(0.4, RangeType.MELEE, 1),
	
	STAFF(0.7, RangeType.RANGED, 2),
	BRACELET(0.5, RangeType.RANGED, 1),
	
	LONGBOW(2, RangeType.RANGED, 2),
	CROSSBOW(1.3, RangeType.RANGED, 1);
	
	double weaponMultiplier;
	RangeType rangeType;
	int hands;
	WeaponType(double weaponMultiplier, RangeType rangeType, int hands){
		this.weaponMultiplier = weaponMultiplier;
		this.rangeType = rangeType;
		this.hands = hands;		
	}
	public double getWeaponMultiplier() {
		return weaponMultiplier;
	}
	public RangeType getRangeType() {
		return rangeType;
	}
	public int getHands() {
		return hands;
	}
	
}
