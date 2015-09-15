package me.bigblaster10.mage;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.EntityArmorStand;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Fireball {

	
	Player player;
	Location loc;
	ArmorStand stand;
	Vector direction;
	int ticks = 0;
	
	static ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
	
	public Fireball(Player player, Location loc){
		this.player = player;
		this.loc = loc;
		this.direction = player.getLocation().getDirection();
		fireballs.add(this);
		
		stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
		//stand.setVisible(false);
		stand.setHelmet(new ItemStack(Material.ICE));
		//stand.setGravity(false);
		CraftArmorStand s = ((CraftArmorStand) this.stand);
		//AxisAlignedBB bb = s.getHandle().getBoundingBox();
		//s.getHandle().a(new CustomBoundingBox());
	}
	
	public static void update(){
		for(int i = fireballs.size()-1; i >= 0; i--){
			Fireball fireball = fireballs.get(i);
			
			fireball.getStand().setVelocity(fireball.getDirection());
			if(!fireball.getStand().getLocation().clone().add(0,-1,0).getBlock().equals(Material.AIR)){
				fireball.getStand().teleport(fireball.getStand().getLocation().clone().add(0,-0.2,0));
			}
			fireball.setTicks(fireball.getTicks()+1);
			if(fireball.getTicks() > 100){
				fireballs.remove(fireball);
				fireball.getStand().remove();
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public ArmorStand getStand() {
		return stand;
	}

	public void setStand(ArmorStand stand) {
		this.stand = stand;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public ArrayList<Fireball> getFireballs() {
		return fireballs;
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

	public int getTicks(){
		return ticks;
	}
	
	
	
}
