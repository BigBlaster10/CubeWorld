package me.bigblaster10.quests;

import java.util.ArrayList;

import me.bigblaster10.main.ArmorStandPosition;
import me.bigblaster10.main.CubeClass;
import me.bigblaster10.main.CubeNPC;
import me.bigblaster10.main.Main;
import me.bigblaster10.main.Race;
import me.bigblaster10.utils.Hologram;
import me.bigblaster10.utils.Vector3;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class TestQuest extends Quest{

	CubeNPC npc;
	Location loc;
	ArrayList<Player> currentlyClicked = new ArrayList<Player>();
	String[] lore = {"Hello there Lad!", "OMG, your name is &p", "Here, have this...", "Use it to find me a diamond"};
	
	public TestQuest(Location loc){
		this.loc = loc;
		ArmorStandPosition pos = new ArmorStandPosition();
		pos.setLeftArmPose(new EulerAngle(301, 0, 0));
		npc = new CubeNPC(ChatColor.GREEN + "Moron", Race.HUMAN, CubeClass.MAGE, loc, pos);
		npc.spawn();
		npc.getStandNPC().setHelmet(new ItemStack(Material.PUMPKIN));

	}
	
	
	@Override
	public void onClick(final Player player) {
		if(currentlyClicked.contains(player)) return;
		if(currentlyClicked.size() >= 1){
			playInteruption(player);			
			return;
		}
		//PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(npc.getStandNPC().getEntityId());
		//((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		/////////////////new PlayerStandPacket(player, npc.getStandNPC());
		if(player.isSneaking()){
			removeQuest(player);
			player.sendMessage("Reset quest");
			return;
		}
		
		if(isCompleted(player)){
			
			player.sendMessage("You have allready completed this quest");
			return;
		}
		if(hasQuest(player)){
			if(player.getInventory().getItemInHand().getType().equals(Material.DIAMOND)){
				String[] lore = {ChatColor.GRAY + "Thank you soo much!"};
				playLore(player, lore, 30, 0);
				currentlyClicked.add(player);

				setCompleted(player, true);
				player.getInventory().setItemInHand(new ItemStack(Material.AIR));
				for(int i = 0; i < 200; i++){
					player.getWorld().dropItemNaturally(npc.getStandNPC().getLocation(), new ItemStack(Material.DIAMOND, 1));
				}
				
			}else{
				String[] lore = {ChatColor.RED + "WHAT ARE YOU DOING??!! GET ME MY DIAMONDSS"};
				playLore(player, lore, 30, 0);
				currentlyClicked.add(player);
			}
			return;
		}else{
			player.sendMessage("Starting quest...");
		}
		
		addQuest(player);
		playLore(player, lore, 30, 0);
		currentlyClicked.add(player);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			  public void run() {
			     player.getInventory().addItem(new ItemStack(Material.DIAMOND_BLOCK));
			  }
			}, 30 * lore.length);	
	}
	
	
	
	
	public void playLore(final Player player, final String[] lore, final long delay, final int i){
		String sLore = lore[i].replace("&p", player.getName());
		final Hologram holo = new Hologram(sLore, loc.clone().add(0, 0.3 ,0));
		holo.show(player);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			  public void run() {
			      holo.hide(player);
				  if(i >= lore.length-1){
					  currentlyClicked.remove(player);
					 //// PlayerStandPacket.getPlayer(player, npc.getStandNPC()).remove();
					  return;
				  }
				  playLore(player, lore, delay, i+1);	
			  }
			}, delay);		
	}
	
	public void playInteruption(final Player player){
		final Hologram holo = new Hologram("You rude MOFO, I am currently talking to " + currentlyClicked.get(0).getName(), loc.clone().add(0, 0.3 ,0));
		holo.show(player);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			  public void run() {
				holo.hide(player);  
			  }				 
			}, 30L);
		
	}
	
	@Override
	public void update(){	
		for(Player player : currentlyClicked){
			ArmorStand stand = npc.getStandNPC();
			
			Vector3 vector = Vector3.fromLocation(stand.getLocation()).minus(Vector3.fromLocation(player.getLocation()));
			float distance = (float) Math.sqrt(vector.getZ() * vector.getZ() + vector.getX() * vector.getX());
			double pitch = Math.asin(vector.getY()/distance);
			float f = (float)(Math.atan2(vector.getX(), vector.getZ()));
			//Bukkit.broadcastMessage("yaw" + (180 -  Math.toDegrees(f)));
			Location loc = stand.getLocation();
			loc.setYaw((float) ((float)180 -  Math.toDegrees(f)));
			//stand.teleport(loc);
			stand.setHeadPose(new EulerAngle(0, Math.toRadians(180 - Math.toDegrees(f)), 0));
		}
		
		
		
	}

	public Location minus(Location loc0, Location loc01){
		Location loc1 = loc0.clone();
		Location loc2 = loc01.clone();
		return new Location(loc1.getWorld(), loc1.getX() - loc2.getX(), loc1.getY() - loc2.getY(), loc1.getZ() - loc2.getZ());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public CubeNPC getNPC(){
		return npc;
	}


	@Override
	public String getQuestName() {
		return "Gather shit for Moron";
	}


	@Override
	public String getID() {
		return "1";
	}
	

}
