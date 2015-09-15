package me.bigblaster10.resources;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.util.Vector;

public class ResourceListener implements Listener{

	
	@EventHandler
	public void onEntityDamageEntityEvent(EntityDamageByEntityEvent event){
		if(event.getDamager() instanceof Arrow) event.setCancelled(true);
		if(!(event.getEntity() instanceof ArmorStand)) return;
		Player player = null;
		if(event.getDamager() instanceof Player) player = (Player) event.getDamager();
		ArmorStand target = (ArmorStand) event.getEntity();
		Location targetLoc = target.getLocation();
		for(int i = Resource.getResources().size()-1; i >= 0; i--){			
			Resource r = Resource.getResources().get(i);
			if(targetLoc.equals(r.getMainStand().getLocation()) || targetLoc.equals(r.getNameStand().getLocation())){
				r.setHealth(r.getHealth()-1, player);
				
				
			}			
		}
		
		
	}
	
	
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		
		Player player = event.getPlayer();
		event.setCancelled(true);
		//Bukkit.broadcastMessage("dropped item");
		Vector v = player.getVelocity();
		Bukkit.broadcastMessage("yaw: " + player.getLocation().getYaw());
		//player.setVelocity(player.getLocation().getDirection().multiply(new Vector(1,0,1)));
	}

	
	
	
	
	
}
