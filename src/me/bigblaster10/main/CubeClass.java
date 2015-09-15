package me.bigblaster10.main;

public enum CubeClass {

	MAGE("Mage", 1.0),
	RANGER("Ranger", 1.10),
	ROGUE("Rogue", 1.20),
	WARRIOR("Warrior", 1.30);
	
	private String name;
	private double healthCoefficient;
	
	CubeClass(String name, double healthCoefficient){
		this.name = name;
		this.healthCoefficient = healthCoefficient;
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return name;
	}
	
	public double getHealthCoefficient(){
		return healthCoefficient;
	}
	
	public static CubeClass getCubeClass(String s){
		for(CubeClass cubeClass : CubeClass.values()){
			if(cubeClass.getName().equals(s)) return cubeClass;
		}
		return null;
	}
	
	
}
