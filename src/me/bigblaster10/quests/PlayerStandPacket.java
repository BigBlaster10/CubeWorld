package me.bigblaster10.quests;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.Vector3f;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

public class PlayerStandPacket {

	static ArrayList<PlayerStandPacket> packets = new ArrayList<PlayerStandPacket>();
	
	public Player player;
	public EntityArmorStand stand;
	public ArmorStand normalStand;
	
	@Deprecated
	public PlayerStandPacket(Player player, ArmorStand stand){
		this.player = player;
		this.normalStand = stand;
		this.stand = setArmorStand(stand);
		//this.stand = ((CraftArmorStand) stand).getHandle();
		//ItemStack head = new ItemStack(Material.LEATHER_HELMET, 1);

		net.minecraft.server.v1_8_R3.ItemStack head = new net.minecraft.server.v1_8_R3.ItemStack(Items.LEATHER_CHESTPLATE, 1);
		this.stand.setEquipment(0, head);
		//this.stand.setEquipment(1, head);
		//this.stand.setEquipment(2, head);
		//this.stand.setEquipment(3, head);
		//this.stand.setEquipment(4, head);
		for(net.minecraft.server.v1_8_R3.ItemStack i : this.stand.getEquipment()){
			//Bukkit.broadcastMessage(i.toString());
			//Bukkit.broadcastMessage(i.getItem().toString());
			//Bukkit.broadcastMessage(i.getItem().getName().toString());
		}
		
		((CraftPlayer) player).getHandle().inventory.setItem(1, head);
		
		
		//this.stand.setPositionRotation(stand.getLocation().getX(), stand.getLocation().getY(), stand.getLocation().getZ(), 0, 0);
		
		show();
		
		packets.add(this);
	}
	
	private EntityArmorStand setArmorStand(ArmorStand stand){
		Location loc = stand.getLocation();
		EntityArmorStand s = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
		s.setCustomName(stand.getCustomName());
		s.setCustomNameVisible(true);
		s.setGravity(stand.hasGravity());

		s.setArms(true);
		s.setBasePlate(false);
		
		//Bukkit.broadcastMessage(s.get)
		
		//s.setPositionRotation(BlockPosition.ZERO, loc.getYaw(), loc.getPitch());
		
		s.setHeadPose(toVector(stand.getBodyPose()));	
		s.setBodyPose(toVector(stand.getBodyPose()));		
		s.setRightArmPose(toVector(stand.getRightArmPose()));
		s.setLeftArmPose(toVector(stand.getLeftArmPose()));
		s.setLeftLegPose(toVector(stand.getLeftLegPose()));
		s.setRightLegPose(toVector(stand.getRightLegPose()));
		
		return s;

	}
	
	private Vector3f toVector(EulerAngle angle){
		return new Vector3f((float) angle.getX(), (float) angle.getY(), (float) angle.getZ());
	}
	
	
	public void show(){
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);	
		
		if(normalStand.getItemInHand() != null && !normalStand.getItemInHand().getType().equals(Material.AIR)){
			PacketPlayOutEntityEquipment hand = new PacketPlayOutEntityEquipment(stand.getId(), 0, CraftItemStack.asNMSCopy(normalStand.getItemInHand()));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(hand);	
		}		
		
		if(normalStand.getBoots() != null && !normalStand.getBoots().getType().equals(Material.AIR)){
			PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(stand.getId(), 1, CraftItemStack.asNMSCopy(normalStand.getBoots()));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(boots);	
		}
		
		if(normalStand.getLeggings() != null && !normalStand.getLeggings().getType().equals(Material.AIR)){
			PacketPlayOutEntityEquipment leggings = new PacketPlayOutEntityEquipment(stand.getId(), 2, CraftItemStack.asNMSCopy(normalStand.getLeggings()));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(leggings);	
		}
		
		if(normalStand.getChestplate() != null && !normalStand.getChestplate().getType().equals(Material.AIR)){
			PacketPlayOutEntityEquipment chestplate = new PacketPlayOutEntityEquipment(stand.getId(), 3, CraftItemStack.asNMSCopy(normalStand.getChestplate()));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(chestplate);	
		}
		
		if(normalStand.getHelmet() != null && !normalStand.getHelmet().getType().equals(Material.AIR)){
			PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(stand.getId(), 4, CraftItemStack.asNMSCopy(normalStand.getHelmet()));
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(helmet);	
		}
	}
	
	public void hide(){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(stand.getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	public void remove(){
		packets.remove(this);
		hide();		
		Bukkit.broadcastMessage("hide");
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(((CraftArmorStand) normalStand).getHandle());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);	
	
	}
	
	
	public Player getPlayer(){
		return player;		
	}
	
	public EntityArmorStand getEntityArmorStand(){
		return stand;
	}
	
	public ArmorStand getArmorStand(){
		return normalStand;
	}
	
	
	public static ArrayList<PlayerStandPacket> getPackets(){
		return packets;
	}
	
	public static PlayerStandPacket getPlayer(Player player, ArmorStand stand){
		for(PlayerStandPacket packet : packets){
			if(packet.getPlayer().equals(player) && packet.getArmorStand().equals(stand)) return packet;
		}
		return null;
	}
	
}
