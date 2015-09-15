package me.bigblaster10.utils;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hologram {

	private ArrayList<EntityArmorStand> stands = new ArrayList<EntityArmorStand>();
	private String[] text;
	private Location loc;
	private double DISTANCE = 0.25;
	
	public Hologram(String[] text, Location loc){
		this.text = text;
		this.loc = loc;
		create();
	}
	
	public Hologram(String text, Location loc){
		this.text = new String[1];
		this.text[0] = text;
		this.loc = loc;
		create();
	}

	public void show(Player player){
		for(EntityArmorStand stand : stands){
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void hide(Player player){
		for(EntityArmorStand stand : stands){
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(stand.getId());
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void showAll(){
		for(Player player : loc.getWorld().getPlayers()){
			show(player);
		}
	}
	
	public void hideAll(){
		for(Player player : loc.getWorld().getPlayers()){
			hide(player);
		}
	}
	
	
	
	
	public void create(){
		Location loc = this.loc.clone();
		for(String text : this.text){
			EntityArmorStand stand = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
			stand.setCustomName(text);
			stand.setCustomNameVisible(true);
			stand.setInvisible(true);
			stand.setGravity(false);
			stands.add(stand);
			loc.subtract(0, DISTANCE, 0);
		}
		
		
		
		
		
	}
}
