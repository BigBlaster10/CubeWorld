package me.bigblaster10.resources;

import org.bukkit.inventory.ItemStack;

public class ResourceItem {

	private ItemStack item;
	private int maxAmount, minAmount;
	private int weight;
	
	public ResourceItem(ItemStack item, int minAmount, int maxAmount, int weight){
		this.item = item;
		this.maxAmount = maxAmount;
		this.minAmount = minAmount;
		this.weight = weight;
	}

	public ItemStack getItem() {
		return item;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getWeight() {
		return weight;
	}
	
	
}
