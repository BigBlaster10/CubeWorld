package me.bigblaster10.main;

public class CubeEntity {

	protected int health;
	protected int level;
	protected Race race;
	protected CubeClass cubeClass;
	protected int mana;
	protected int stamina = 50;
	
	public CubeEntity(Race race, CubeClass cubeClass){
		this.cubeClass = cubeClass;
		this.race = race;
	}
	
	public int getHealth() {
		return health;
	}

	public int getLevel() {
		return level;
	}

	public Race getRace() {
		return race;
	}

	public CubeClass getCubeClass() {
		return cubeClass;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getStamina(){
		return stamina;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public void setStamina(int stamina){
		if(stamina > 100) this.stamina = 100;
		else if(stamina < 0) this.stamina = 0;
		else this.stamina = stamina;
				
	}
	
}
