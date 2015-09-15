package me.bigblaster10.main;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class CubeNPC extends CubeEntity{

	private ArmorStand stand;
	private Location loc;
	private ArmorStandPosition standPosition;
	private String name;
	
	public CubeNPC(String name, Race race, CubeClass cubeClass, Location loc, ArmorStandPosition standPosition) {
		super(race, cubeClass);		
		this.name = name;
		this.loc = loc;
		this.standPosition = standPosition;		
	}
	
	public void spawn(){
		ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		standPosition.setArmorStandPosition(stand);
		stand.setCustomNameVisible(true);
		stand.setCustomName(name);
		this.stand = stand;
	}
	
	public ArmorStand getStandNPC(){
		return stand;
	}
	
}
