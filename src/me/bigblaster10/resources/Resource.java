package me.bigblaster10.resources;

import java.util.ArrayList;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Resource {

	private ArmorStand healthAndHeadStand;
	private ArmorStand nameStand;
	private int currHealth;	
	private boolean isDead = false;
	
	public abstract String getDisplayName();
	public abstract int getMaxHealth();	
	public abstract ItemStack getHeadItem();
	public abstract ArrayList<ResourceItem> getDrops();
	
	public static ArrayList<Resource> resources = new ArrayList<Resource>();
	
	public Resource(Location loc){
		currHealth = getMaxHealth();
		healthAndHeadStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		nameStand = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0,0.3,0), EntityType.ARMOR_STAND);
		
		healthAndHeadStand.setHelmet(getHeadItem());
		updateCustomName();		
		healthAndHeadStand.setCustomNameVisible(true);		

		healthAndHeadStand.setVisible(false);		
		healthAndHeadStand.setSmall(true);
		healthAndHeadStand.setGravity(false);
		
		nameStand.setCustomName(getDisplayName());
		nameStand.setCustomNameVisible(true);		
		nameStand.setVisible(false);		
		nameStand.setSmall(true);
		nameStand.setGravity(false);
		
		resources.add(this);
		
	}
	
	
	
	public void updateCustomName(){
		String s = ChatColor.GRAY + "[" + ChatColor.GREEN;
		//int bars = (int) (1.0 * (1.0 * data.getStamina()/100) * 20);
		int healthBars = (int) (1.0 * (1.0 * currHealth/getMaxHealth()) * 20);
		for(int i = 0; i < healthBars; i++){
			s += "|";
		}
		s += "" + ChatColor.RED;
		for(int i = 0; i < 20-healthBars; i++){
			s += "|";
		}
		s += ChatColor.GRAY + "]";
		healthAndHeadStand.setCustomName(s);
	}
	
	public static ArrayList<Resource> getResources(){
		return resources;
	}
	
	public ArmorStand getMainStand() {
		return healthAndHeadStand;
	}
	public ArmorStand getNameStand() {
		return nameStand;
	}
	public int getHealth() {
		return currHealth;
	}
	
	public boolean isDead(){
		return isDead;
	}
	
	public void setHealth(int health, Player player){
		if(health < 0){
			ItemStack drops = getRandomDrops();
			getNameStand().getWorld().dropItem(getNameStand().getLocation(), drops);
			getMainStand().remove();
			getNameStand().remove();	
			resources.remove(this);
			isDead = true;
			if(player != null)
				player.playSound(player.getLocation(), "destroy2", 1, 1);
			return;
		}else if(health > getMaxHealth()) currHealth = getMaxHealth();
		else currHealth = health;
		updateCustomName();		
	}
	
	
	public ItemStack getRandomDrops(){
		double totalWeight = 0.0;
		for(ResourceItem item : getDrops()){
			totalWeight += item.getWeight();
		}
		int rIndex = -1;
		double random = Math.random() * totalWeight;
		for(int i = 0; i < getDrops().size(); i++){
			random -= getDrops().get(i).getWeight();
			if(random <= 0.0){
				rIndex = i;
				break;
			}
		}
		
		ResourceItem randItem = getDrops().get(rIndex);
		
		ItemStack item = randItem.getItem();
		Random rand = new Random();
		item.setAmount(rand.nextInt((randItem.getMaxAmount() - randItem.getMinAmount()) + 1) + randItem.getMinAmount());
		
		return item;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
