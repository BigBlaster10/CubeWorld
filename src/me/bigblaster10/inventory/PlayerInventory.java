package me.bigblaster10.inventory;

import java.sql.ResultSet;

import me.bigblaster10.main.CubeClass;
import me.bigblaster10.main.PlayerData;
import me.bigblaster10.utils.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class PlayerInventory {

	
	public static void saveInventory(Player player){
		if(PlayerData.getCurrentPlayerData(player) == null) return;
		PlayerData data = PlayerData.getCurrentPlayerData(player);
		//remove items
		MySQL.prepareStatement("DELETE FROM playerinventory WHERE playername = '" + player.getUniqueId().toString() + "' AND id = " + data.getCharID() + ";");
		
		Inventory inv = player.getInventory();
		for(int i = 0; i < inv.getSize(); i++){
			if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) continue;
			if(CubeItem.getCubeItem(inv.getItem(i)) == null) continue;
			CubeItem item = CubeItem.getCubeItem(inv.getItem(i));
			MySQL.prepareStatement("INSERT INTO playerinventory VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', " + data.getCharID() + "," + i + ", '" + data.getCubeClass().getName() + "', '" + item.getRarity().getDisplayName() + "', '" + inv.getItem(i).getType() + "', '" + inv.getItem(i).getItemMeta().getDisplayName() + "', '" + item.getMySQLStats() + "');");              																								
		}		
		
		ItemStack[] armor = player.getInventory().getArmorContents();
		for(int i = 0; i < armor.length; i++){
			if(armor[i] == null || armor[i].getType().equals(Material.AIR)) continue;
			if(CubeItem.getCubeItem(armor[i]) == null) continue;
			CubeItem item = CubeItem.getCubeItem(armor[i]);
			MySQL.prepareStatement("INSERT INTO playerinventory VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', " + data.getCharID() + "," + (i+36) + ", '" + data.getCubeClass().getName() + "', '" + item.getRarity().getDisplayName() + "', '" + armor[i].getType() + "', '" + armor[i].getItemMeta().getDisplayName() + "', '" + item.getMySQLStats() + "');");              																								
		
		}
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));

	}
	
	
	public static void loadInventory(Player player){
		if(PlayerData.getCurrentPlayerData(player) == null) return;
		PlayerData data = PlayerData.getCurrentPlayerData(player);
		
		Inventory inv = player.getInventory();
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerinventory WHERE playername = '" + player.getUniqueId().toString() + "' AND id = " + data.getCharID() + ";");
		
		for(int i = 0; i < inv.getSize(); i++){
			if(inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) continue;
			if(CubeItem.getCubeItem(inv.getItem(i)) == null) continue;
			CubeItem item = CubeItem.getCubeItem(inv.getItem(i));
			CubeItem.getCubeItems().remove(item);
		}
		
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		while(MySQL.next(rs)){
			Material mat = Material.getMaterial(MySQL.getString(rs, "itemMaterial"));
			String displayName = MySQL.getString(rs, "itemName");
			String rarity = MySQL.getString(rs, "rarity");
			String cubeClass = MySQL.getString(rs, "cubeClass");
			String s = MySQL.getString(rs, "stats");
			int slot = MySQL.getInt(rs, "slot");
			CubeItem item = CubeItem.getItemFromMySQLStats(mat, displayName, rarity, cubeClass, s);
			if(slot < 36){
				player.getInventory().setItem(slot, item.getItemStack());
			}else if(slot == 36){
				player.getInventory().setBoots(item.getItemStack());
			}else if(slot == 37){
				player.getInventory().setLeggings(item.getItemStack());
			}else if(slot == 38){
				player.getInventory().setChestplate(item.getItemStack());
			}else if(slot == 39){
				player.getInventory().setHelmet(item.getItemStack());
			}
		}
		player.getInventory().setItem(8, new ItemStack(Material.DIAMOND_HELMET));
		
	}
	
	
	
	
	
	
}
