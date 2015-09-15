package me.bigblaster10.main;

public enum Race {
	
	HUMAN("Human"),
	ELF("Elf"),
	DWARF("Dwarf"),
	ORC("Orc"),
	GOBLIN("Goblin"),
	LIZZARDMAN("Lizzardman"),
	UNDEAD("Undead"),
	FROGMAN("Frogman");
	
	String name;
	
	Race(String name){
		this.name = name;
	}
	
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}
	
	public static Race getRace(String s){
		for(Race race : Race.values()){
			if(race.getName().equals(s)) return race;
		}
		return null;
	}
	
}
