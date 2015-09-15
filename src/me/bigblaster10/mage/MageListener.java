package me.bigblaster10.mage;

import me.bigblaster10.inventory.CubeItem;
import me.bigblaster10.main.CubeClass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MageListener implements Listener{

	@EventHandler
	public void onPlayerCLick(PlayerInteractEvent event){
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
		Bukkit.broadcastMessage("c1");
		Player player = event.getPlayer();
		if(player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return;
		if(CubeItem.getCubeItem(player.getItemInHand()) == null) return;
		Bukkit.broadcastMessage("c2");
		CubeItem item = CubeItem.getCubeItem(player.getItemInHand());
		if(!item.getCubeClass().equals(CubeClass.MAGE)) return;
		
		new Fireball(player, player.getLocation());
		
		//ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
		//stand.setVisible(false);
		//stand.setHelmet(new ItemStack(Material.ICE));
		//stand.setGravity(false);
		
	}
	
	
}
