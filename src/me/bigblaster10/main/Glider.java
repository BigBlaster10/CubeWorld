package me.bigblaster10.main;

import java.util.ArrayList;










import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Glider implements Listener{

	
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Player> cooldown = new ArrayList<Player>();
	static ArrayList<Glide> glide = new ArrayList<Glide>();
	
	static Material[] ignoredMaterials = {Material.AIR, Material.LONG_GRASS, Material.YELLOW_FLOWER, Material.RED_ROSE, Material.SUGAR_CANE_BLOCK, Material.DEAD_BUSH, Material.SAPLING};
	
	final static int SPEED = 10;
	

	
		public static void update(){
			for(Player player : Bukkit.getOnlinePlayers()){
				if(player.getItemInHand().getType().equals(Material.SPONGE) && player.getVelocity().getY() >= -0.15 && goodGlideLocation(player.getLocation())){
					if(PlayerData.getCurrentPlayerData(player) == null) continue;
					if(players.contains(player)) continue;
					if(cooldown.contains(player)) continue;
					
					ArmorStand stand = (ArmorStand) Main.world.spawnEntity(player.getLocation().add(0,0.2,0), EntityType.ARMOR_STAND);
					stand.setVisible(false);
					stand.setPassenger(player);
					stand.setCustomName(player.getName());
					stand.setSmall(true);
					players.add(player);
				}				
				
			}
			
			
			for(int i = players.size()-1; i >= 0; i--){
				Player player = players.get(i);				
				if(getStandWithName(player.getName()) == null) return;
				ArmorStand stand = (ArmorStand) getStandWithName(player.getName());
				
				if((!player.getItemInHand().getType().equals(Material.SPONGE)) || !goodGlideLocation(stand.getLocation()) || stand.getPassenger() != player){
					removePlayer(player);
					continue;
				}
				Vector pVec = player.getLocation().getDirection();
				double y = -0.1;
				for(int a = glide.size()-1; a >= 0; a--){
					Glide g = glide.get(i);
					if(g.getPlayer().equals(player)){
						y = 0.1;
						g.setTicks(g.getTicks()-1);
						if(g.getTicks() == 1) glide.remove(i);
					}
					PlayerData data = PlayerData.getCurrentPlayerData(player);
					data.setStamina(data.getStamina()-1);
				}
				Vector vec = new Vector(pVec.getX() * 0.05 * SPEED, y, pVec.getZ() * 0.05 * SPEED);
				stand.setVelocity(vec);

			}
			
		}
		
		public static void removePlayer(Player player){
			players.remove(player);
			removeArmorStand(player.getName());
			addCooldown(player);
		}
		
		public static void addCooldown(final Player player){
			cooldown.add(player);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				  public void run() {
					  cooldown.remove(player);
				  }
				}, 20L);
		}
		
		public static boolean goodGlideLocation(Location l){
			Location loc = l.clone().add(0,-1,0);
			Material type = loc.getBlock().getType();
			for(Material mat : ignoredMaterials){
				if(type.equals(mat)) return true;
			}
			return false;		
		}
		
		
		
		public static Entity getStandWithName(String s){
			for(Entity e : Main.world.getEntities()){
				if(!(e instanceof ArmorStand)) continue;
				if(((ArmorStand) e).getCustomName() == null) continue;
				if(((ArmorStand) e).getCustomName().equals(s)) return e;
				
			}
			return null;
		}
		
		
		public static void removeArmorStand(String s){
			for(Entity e : Main.world.getEntities()){
				if(!(e instanceof ArmorStand)) continue;
				if(e.getCustomName() == null) continue;
				if(((ArmorStand) e).getCustomName().equals(s)) e.remove();
			}
		}
		
		
		
		@EventHandler
		public void onRightClick(PlayerInteractEvent event){
			Player player = event.getPlayer();
			if(event.getAction().equals(Action.RIGHT_CLICK_AIR) && player.getItemInHand().getType().equals(Material.SPONGE)){
				if(PlayerData.getCurrentPlayerData(player) == null) return;
				PlayerData data = PlayerData.getCurrentPlayerData(player);
				if(data.getStamina() == 0) return;
				Glider.glide.add(new Glide(player, 5));
			}
		}
		
		
		
		
		
		public class Glide{
			
			Player player;
			int ticks;
			
			public Glide(Player player, int ticks){
				this.player = player;
				this.ticks = ticks;
				
			}
			
			@Override
			public boolean equals(Object obj){
				if(!(obj instanceof Glide)) return false;
				Player player = ((Glide) obj).getPlayer();
				return this.player == player;
			}
			
			public Player getPlayer(){
				return player;
			}
			
			public int getTicks(){
				return ticks;
			}
			
			public void setTicks(int ticks){
				this.ticks = ticks;
			}
			
			
		}
		
		
		
		
	
	
}
