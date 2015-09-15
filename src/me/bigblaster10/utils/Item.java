package me.bigblaster10.utils;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item extends ItemStack{

	public Item(ItemStack item){
		super(item);		
	}
	
	public Item(ItemStack item, String s){
		super(item);	
		setDisplayName(s);
	}
	
	public Item(ItemStack item, String s, List<String> list){
		super(item);		
		setDisplayName(s);
		setLore(list);
	}
	
	public void setDisplayName(String s){
		ItemMeta meta = this.getItemMeta();
		meta.setDisplayName(s);
		this.setItemMeta(meta);		
	}
	
	public void setLore(List<String> s){
		ItemMeta meta = this.getItemMeta();
		meta.setLore(s);
		this.setItemMeta(meta);
	}
	
	
	
}
