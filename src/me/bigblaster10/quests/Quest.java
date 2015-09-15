package me.bigblaster10.quests;

import java.sql.ResultSet;
import java.util.ArrayList;

import me.bigblaster10.main.CubeNPC;
import me.bigblaster10.utils.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class Quest {

	private static ArrayList<Quest> quests = new ArrayList<Quest>();

	public Quest() {
		quests.add(this);
	}

	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public abstract void onClick(Player player);

	public abstract CubeNPC getNPC();

	public static boolean onClick(Player player, Entity entity) {
		for (Quest quest : quests) {
			if (quest.getNPC().getStandNPC().equals(entity)) {
				quest.onClick(player);
				return true;
			}
		}
		return false;
	}

	public abstract void update();

	public abstract String getQuestName();

	public abstract String getID();

	public static void updateQuests() {
		for (Quest quest : quests) {
			quest.update();
		}
	}

	public void addQuest(Player player){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerquest WHERE playername = '" + player.getUniqueId().toString() + "' AND quest = '" + getID() + "';" );
		if(MySQL.next(rs)) return;
		MySQL.prepareStatement("INSERT INTO playerquest VALUES ('" + player.getUniqueId().toString() + "', '" + player.getName() + "', '" + getID() + "', 0);" );
	}
	
	public void setCompleted(Player player, boolean bool){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerquest WHERE playername = '" + player.getUniqueId().toString() + "' AND quest = '" + getID() + "';" );
		if(!MySQL.next(rs)) return;
		int i = 0;
		if(bool) i = 1;
		MySQL.prepareStatement("UPDATE playerquest SET completed = " + i + " WHERE playername = '" + player.getUniqueId().toString() + "';");
	}
	
	public boolean hasQuest(Player player){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerquest WHERE playername = '" + player.getUniqueId().toString() + "' AND quest = '" + getID() + "';" );
		return MySQL.next(rs);
	}
	
	public boolean isCompleted(Player player){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerquest WHERE playername = '" + player.getUniqueId().toString() + "' AND quest = '" + getID() + "' AND completed = 1;" );
		return MySQL.next(rs);
	}
	
	public void removeQuest(Player player){
		MySQL.prepareStatement("DELETE FROM playerquest WHERE playername = '" + player.getUniqueId().toString() + "' AND quest = '" + getID() + "';");
	}
	
	
}
