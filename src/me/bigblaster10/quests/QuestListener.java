package me.bigblaster10.quests;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class QuestListener  implements Listener{

	
	@EventHandler
	public void onEntityInteract(PlayerInteractAtEntityEvent event){
		if(Quest.onClick(event.getPlayer(), event.getRightClicked()))		
			event.setCancelled(true);
	}
	
	


}
