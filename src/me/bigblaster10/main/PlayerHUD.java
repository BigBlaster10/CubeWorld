package me.bigblaster10.main;

import me.bigblaster10.utils.ActionBar;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHUD {

	
	public static void updateHUD(Player player){
		//PlayerData data = PlayerData.getPlayerData(player);
		
		
		
	}
	
	
	
	
	
	
	public static void updateHealth(Player player, PlayerData data){		
		if(data.getHealth() <= 0 && !player.isDead()){
			player.setHealth(0);
			return;
		}
		player.setHealth((data.getHealth()/data.getMaxHealth()) * 20);		
	}
	
	public static void updateExperience(Player player, PlayerData data){
		double percent = data.getExp()/data.getNeededExperience();
		player.setLevel(data.getLevel());
		player.setExp((float) percent);		
	}
	
	private static int stamina = 0;
	public static void updateStamina(Player player, PlayerData data){
		stamina++;
		if(!data.isGliding() && stamina >= 10 && data.getStamina() < 100){
			data.setStamina(data.getStamina() + 1);			
		}
		int bars = (int) (1.0 * (1.0 * data.getStamina()/100) * 20);
		String s = ChatColor.GRAY + "[";
		s += ChatColor.GREEN + "";
		for(int i = 0; i < bars; i++){
			s += "|";
		}
		s += ChatColor.RED + "";
		for(int i = 0; i < 20-bars; i++){
			s += "|";
		}
		s += ChatColor.GRAY + "]";
		
		
		ActionBar.sendActionBar(player, ChatColor.BLUE + "Stamina: " + s);		
	}
	
	public static void updateScoreboard(Player player, PlayerData data){
		
	}
	
	
	
	public static void update(){
		
		for(PlayerData data : PlayerData.getPlayerData()){
			Player player = data.getPlayer();			
			updateHealth(player, data);
			updateExperience(player, data);
			updateStamina(player, data);
		}
		
		
	}
	
	
}
