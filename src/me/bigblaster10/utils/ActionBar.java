package me.bigblaster10.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

	
	  public static boolean sendActionBar(Player player, String msg){	       
	      IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + msg +  "\"}");
	      PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
	      ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
	      return true;
	  }
	
	
}