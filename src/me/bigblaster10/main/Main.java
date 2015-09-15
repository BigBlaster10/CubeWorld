package me.bigblaster10.main;

import java.util.ArrayList;
import java.util.Random;

import me.bigblaster10.inventory.ArmorType;
import me.bigblaster10.inventory.CubeItem;
import me.bigblaster10.inventory.ItemRarity;
import me.bigblaster10.inventory.PlayerInventory;
import me.bigblaster10.inventory.WeaponType;
import me.bigblaster10.mage.Fireball;
import me.bigblaster10.mage.MageListener;
import me.bigblaster10.quests.Quest;
import me.bigblaster10.quests.QuestListener;
import me.bigblaster10.quests.TestQuest;
import me.bigblaster10.resources.ResourceListener;
import me.bigblaster10.resources.Scrub;
import me.bigblaster10.utils.MySQL;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public static World world;
	public static Plugin plugin;

	
	Location loc1;
	Location loc2;
	public void onEnable() {
		plugin = this;
		world = Bukkit.getWorld("world");

		loc1 = new Location(world, -511, 57, 511);
		loc2 = new Location(world, 511, 111, -511);
		
		Bukkit.getServer().getPluginManager().registerEvents(new Glider(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ResourceListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new QuestListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ClassSelectionListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new MageListener(), this);

		
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(this, new Runnable() {
					@Override
					public void run() {
						if (!MySQL.isConnected()) {
							MySQL.createConnection();
							if(MySQL.isConnected()){
								Bukkit.broadcastMessage(ChatColor.GREEN
									+ "Successfully Connected To MySQL Server");
								PlayerData.loadData();
							}
						}

						Fireball.update();
						PlayerHUD.update();
						Glider.update();
						Quest.updateQuests();
					}
				}, 0L, 1L);
		
		
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("cube")) {
			if (args.length > 1 && args[0].equals("stats")) {
				if (Bukkit.getPlayer(args[1]) == null) {
					player.sendMessage(ChatColor.DARK_RED + "ERROR: "
							+ ChatColor.RED + "\"" + args[1]
							+ "\" is not a valid player name");
					return true;
				}
				Player target = Bukkit.getPlayer(args[1]);
				if (PlayerData.getPlayerData(player) == null) {
					player.sendMessage(ChatColor.DARK_RED + "ERROR: "
							+ ChatColor.RED + args[1]
							+ " has no character with an id of 1");
					return false;
				}
				player.sendMessage(PlayerData.getPlayerData(player)
						.toString());

			} else if (args.length > 0 && args[0].equals("insert")) {
				PlayerData.createCharacter(player, 1, Race.HUMAN,
						CubeClass.MAGE);
				Bukkit.broadcastMessage(ChatColor.GREEN + "Created Character");
			} else if (args.length > 0 && args[0].equals("remove")) {
				PlayerData.removeCharacter(player, 0);
				PlayerData.removeCharacter(player, 1);
				PlayerData.removeCharacter(player, 2);
				Bukkit.broadcastMessage(ChatColor.RED + "Removed Character");
			} else if (args.length > 0 && args[0].equals("test")) {
				
				
				player.sendMessage("Activating test");
				
				//new TestQuest(player.getLocation());
				//CubeItem item = new CubeItem(Material.DIAMOND_AXE, ChatColor.GOLD + "DA KILLER", ItemRarity.LEGENDARY, CubeClass.WARRIOR, 10,20,30,40,50, 0);
				CubeItem item = new CubeItem(Material.DIAMOND_AXE, ChatColor.GOLD + "DA KILLER", 1, CubeClass.WARRIOR, WeaponType.GREATAXE, ItemRarity.COMMON);
				player.getInventory().addItem(item.getItemStack());
				
				CubeItem mage = new CubeItem(Material.STONE_HOE, ChatColor.GOLD + "Bracelet", 1, CubeClass.MAGE, WeaponType.BRACELET, ItemRarity.UNCOMMON);
				player.getInventory().addItem(mage.getItemStack());
				
				
				//CubeItem item1 = new CubeItem(Material.IRON_CHESTPLATE, ChatColor.GOLD + "DA PROTTERCTOR", ItemRarity.LEGENDARY, CubeClass.WARRIOR, 10,20,30,40,50, 2);
				CubeItem item1 = new CubeItem(Material.IRON_CHESTPLATE, ChatColor.GOLD + "DA PROTTERCTOR", 1, CubeClass.WARRIOR, ArmorType.DEFAULTTEST, ItemRarity.COMMON);
				player.getInventory().addItem(item1.getItemStack());
				
				CubeItem item2 = new CubeItem(Material.IRON_HELMET, ChatColor.GOLD + "DA PROTTERCTOR", 1, CubeClass.WARRIOR, ArmorType.DEFAULTTEST, ItemRarity.COMMON);
				player.getInventory().addItem(item2.getItemStack());

				CubeItem item3 = new CubeItem(Material.IRON_LEGGINGS, ChatColor.GOLD + "DA PROTTERCTOR", 1, CubeClass.WARRIOR, ArmorType.DEFAULTTEST, ItemRarity.COMMON);
				player.getInventory().addItem(item3.getItemStack());

				CubeItem item4 = new CubeItem(Material.IRON_BOOTS, ChatColor.GOLD + "DA PROTTERCTOR", 1, CubeClass.WARRIOR, ArmorType.DEFAULTTEST, ItemRarity.COMMON);
				player.getInventory().addItem(item4.getItemStack());

				
			} else if (args.length > 0 && args[0].equals("save")) {
				player.sendMessage("Saving");
				PlayerInventory.saveInventory(player);
			} else if (args.length > 0 && args[0].equals("load")) {
				player.sendMessage("Loading...");
				PlayerInventory.loadInventory(player);
			}
			
			else if (args.length > 0 && args[0].equals("spawn")) {
				spawnItems();
			} else if (args.length > 0 && args[0].equals("clear")) {
				player.sendMessage("Clearing ArmorStands");
				int t = 0;
				for(Entity e : world.getEntities()){
					if(e instanceof ArmorStand){
						e.remove();			
						t++;
					}
				}
				player.sendMessage("Removed " + t + " ArmorStands");
			}
			
			else{
				player.sendMessage(ChatColor.DARK_RED + "ERROR: "
						+ ChatColor.RED + "Invalid Syntax");
				player.sendMessage(ChatColor.RED + "/cube stats <Player>");
				player.sendMessage(ChatColor.RED + "/cube insert");
				player.sendMessage(ChatColor.RED + "/cube remove");
			}

		}
		return false;
	}
	
	
	
	
	
	
	public void spawnItems(){
		
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		Location minLoc = new Location(world, minX, minY, minZ);
		Location maxLoc = new Location(world, maxX, maxY, maxZ);
		
		int tot = 0;
		
		for(Entity e : world.getEntities()){
			if(e instanceof ArmorStand) e.remove();			
		}
		
		Random rand = new Random();
		ArrayList<Location> locs = new ArrayList<Location>();
		while (locs.size() < 1000){
			
			int x = rand.nextInt((maxX - minX) + 1) + minX;
			int y = rand.nextInt((maxY - minY) + 1) + minY;
			int z = rand.nextInt((maxZ - minZ) + 1) + minZ;
			Location loc = new Location(world, x, y ,z);
			tot++;
			if(!loc.getBlock().getType().equals(Material.AIR)) continue;
			if(loc.clone().add(0,-1,0).getBlock().getType().equals(Material.GRASS)){
				locs.add(loc);				
			}			
		}
		Bukkit.broadcastMessage("Done1");
		
		for(Location loc : locs){
			new Scrub(loc);			
		}
		Bukkit.broadcastMessage("Spawned");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
