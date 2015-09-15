package me.bigblaster10.main;

import java.util.ArrayList;
import java.util.List;

import me.bigblaster10.inventory.PlayerInventory;
import me.bigblaster10.utils.Item;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClassSelectionListener implements Listener{

	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event){
		
		if(event.getItem() == null) return;
		ItemStack item = event.getItem();
		Player player = event.getPlayer();
		if(item.getType().equals(Material.COMPASS)){
			openCreateCharacterInv(player);
		}else if(item.getType().equals(Material.DIAMOND_HELMET)){
			openSelectCharacterInv(player);
		}
	}
	
	public void openCreateCharacterInv(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, "Create Character");
		inv.setItem(10, new Item(new ItemStack(Material.STONE_SWORD),  ChatColor.GOLD + "Warrior"));
		inv.setItem(12, new Item(new ItemStack(Material.STONE_SPADE),  ChatColor.GOLD + "Rogue"));
		inv.setItem(14, new Item(new ItemStack(Material.BOW),  ChatColor.GOLD + "Ranger"));
		inv.setItem(16, new Item(new ItemStack(Material.DIAMOND_HOE),  ChatColor.GOLD + "Mage"));
		player.openInventory(inv);
	}
	
	public void openSelectCharacterInv(Player player){
		Inventory inv = Bukkit.createInventory(null, 27, "Characters");
		setClassInv(player, 11, inv, 0);
		setClassInv(player, 13, inv, 1);
		setClassInv(player, 15, inv, 2);
		player.openInventory(inv);
	}
	
	

	public void setClassInv(Player player, int slot, Inventory inv, int id){
		inv.setItem(slot, getClassItem(player, id));
	}
	
	public ItemStack getClassItem(Player player, int charID){
		if(PlayerData.getPlayerData(player, charID) == null){			
			return new Item(new ItemStack(Material.DIAMOND), ChatColor.GREEN + "Create Character");
		}
		PlayerData data = PlayerData.getPlayerData(player, charID);
		Item item = new Item(new ItemStack(getClassMaterial(data.getCubeClass())));
		item.setDisplayName(ChatColor.GOLD + data.getCubeClass().getName());
		List<String> list = new ArrayList<String>();
		list.add("Level: " + data.getLevel());
		list.add("Experience: " + data.getExp());
		list.add("Race: " + data.getRace().getName());
		item.setLore(list);
		return item;
	}
	
	public Material getClassMaterial(CubeClass cubeClass){
		if(cubeClass.equals(CubeClass.WARRIOR)){
			return Material.STONE_SWORD;
		}else if(cubeClass.equals(CubeClass.ROGUE)){
			return Material.STONE_SPADE;
		}else if(cubeClass.equals(CubeClass.RANGER)){
			return Material.BOW;
		}else if(cubeClass.equals(CubeClass.MAGE)){
			return Material.DIAMOND_HOE;
		}
		return null;
	}
	
	
	@EventHandler
	public void playerClickInv(InventoryClickEvent event){
		if(event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) return;
		ItemStack item = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		if(!item.hasItemMeta()) return;
		if(!item.getItemMeta().hasDisplayName()) return;
		String name = item.getItemMeta().getDisplayName();
		if(event.getInventory().getTitle().equals("Characters")){
			if(name.equals(ChatColor.GREEN + "Create Character")){
				event.setCancelled(true);
				openCreateCharacterInv(player);
				return;
			}
			PlayerInventory.saveInventory(player);

			setCurrentCharacter(player, event.getSlot());
			event.setCancelled(true);
			player.closeInventory();
			player.sendMessage("Class set to: " + PlayerData.getCurrentPlayerData(player).getCubeClass().getName());
			PlayerInventory.loadInventory(player);
			return;
		}
		if(!event.getInventory().getTitle().equals("Create Character")) return;
		event.setCancelled(true);
		int id = getLowestUnusedID(player);
		if(id == -1){
			player.sendMessage(ChatColor.RED + "Error: You can only create up to 3 characters");
			return;
		}
		if(name.equals(ChatColor.GOLD + "Warrior")){
			PlayerData.createCharacter(player, id, Race.HUMAN, CubeClass.WARRIOR);
		}else if(name.equals(ChatColor.GOLD + "Rogue")){
			PlayerData.createCharacter(player, id, Race.HUMAN, CubeClass.ROGUE);
		}else if(name.equals(ChatColor.GOLD + "Ranger")){
			PlayerData.createCharacter(player, id, Race.HUMAN, CubeClass.RANGER);
		}else if(name.equals(ChatColor.GOLD + "Mage")){
			PlayerData.createCharacter(player, id, Race.HUMAN, CubeClass.MAGE);
		}else{
			return;
		}
		PlayerData.getPlayerData(player, id).setCurrent(true);
		openSelectCharacterInv(player);

	}

	
	public void setCurrentCharacter(Player player, int slot){
		int id = slotToID(slot);
		for(PlayerData data : PlayerData.getPlayerData()){
			if(data.getCharID() == id && data.getPlayer().equals(player)) {
				data.setCurrent(true);
				return;
			}
		}
	}
	
	public int slotToID(int slot){
		if(slot == 11){
			return 0;
		}if(slot == 13){
			return 1;
		}if(slot == 15){
			return 2;
		}
		return 0;
	}
	
	
	public int getLowestUnusedID(Player player){
		if(!PlayerData.hasCharacter(player, 0)){
			return 0;
		}if(!PlayerData.hasCharacter(player, 1)){
			return 1;
		}if(!PlayerData.hasCharacter(player, 2)){
			return 2;
		}
		return -1;
	}
	
	
	
	
	
	
}
