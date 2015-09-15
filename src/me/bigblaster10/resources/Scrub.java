package me.bigblaster10.resources;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Scrub extends Resource{

	public Scrub(Location loc) {
		super(loc);		
	}

	@Override
	public String getDisplayName() {
		return ChatColor.GRAY + "Scrub";
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public ItemStack getHeadItem() {		
		return new ItemStack(Material.WOOL);
	}

	@Override
	public ArrayList<ResourceItem> getDrops() {
		ArrayList<ResourceItem> items = new ArrayList<ResourceItem>();
		items.add(new ResourceItem(new ItemStack(Material.WEB), 1, 3, 1));		
		return items;
	}
	
	
	
	

}
