package me.bigblaster10.inventory;


public enum ItemRarity {
	
	COMMON("Common", 1),
	UNCOMMON("Uncommon", 1.5),
	RARE("Rare", 2),
	EPIC("Epic", 2.5),
	LEGENDARY("Legendary", 3);
	
	String displayName;
	double multiplier;
	ItemRarity(String displayName, double multiplier){
		this.displayName = displayName;
		this.multiplier = multiplier;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public double getMultiplier(){
		return multiplier;
	}
	
	public static ItemRarity getRarity(String s){
		for(ItemRarity r : ItemRarity.values()){
			if(r.getDisplayName().equals(s)) return r;
		}
		return null;
	}
	
	
}
