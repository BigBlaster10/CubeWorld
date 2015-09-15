package me.bigblaster10.main;

import java.sql.ResultSet;
import java.util.ArrayList;

import me.bigblaster10.utils.MySQL;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerData extends CubeEntity{

	private Player player;
	private int charID;
	private int exp;	
	private int coins;
	private int mana;
	private boolean current = false;
	
	public static ArrayList<PlayerData> playerData = new ArrayList<PlayerData>();
	
	private PlayerData(Player player, int charID, Race race, CubeClass cubeClass){
		super(race, cubeClass);
		this.player = player;
		this.charID = charID;
		this.exp = 0;
		this.health = (int) calculateHealth(cubeClass, 1);
		this.level = 1;
		this.coins = 0;
		this.mana = 0;	
		this.stamina = 100;
		insertPlayer();
		playerData.add(this);
	}
	
	private PlayerData(Player player, int charID, int exp, int health, int level, String race, String cubeClass, int coins, int mana){
		super(Race.getRace(race), CubeClass.getCubeClass(cubeClass));
		this.player = player;
		this.charID = charID;
		this.exp = exp;
		this.health = health;
		this.level = level;
		this.coins = coins;
		this.mana = mana;	
		this.stamina = 100;
		playerData.add(this);
	}
	
	public static void loadData(){
		for(Player player : Bukkit.getOnlinePlayers()){
			PlayerData.getPlayerData(player);
			player.setSaturation(100000);
		}
	}
	
	private void insertPlayer(){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerdata WHERE playername = '" + player.getUniqueId().toString() + "' AND id = " + charID + ";");
		Bukkit.broadcastMessage("1");
		if(MySQL.next(rs)) return;
		Bukkit.broadcastMessage("2");
		MySQL.prepareStatement("INSERT INTO playerdata VALUES('" + player.getUniqueId().toString() + "', '" + player.getName() + "', " + charID + ", 0, " + health + ", 1, '" + race.toString() + "', '" + cubeClass.toString() + "', 0, 0);");
	}
	
	public double getMaxHealth(){
		return calculateHealth(cubeClass, level);
	}
	
	public double getNeededExperience(){
		return calculateExperienceNeeded(level);
	}
	
	public static ArrayList<PlayerData> getPlayerData(){
		return playerData;
	}
	
	
	public static PlayerData getCurrentPlayerData(Player player){
		for(PlayerData data : playerData){
			if(data.getPlayer().equals(player) && data.isCurrent()) return data;
		}
		return null;
	}
	
	public static ArrayList<PlayerData> getPlayerData(Player player){
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerdata WHERE player = '" + player.getName() + "';");
		ArrayList<PlayerData> datas = new ArrayList<PlayerData>();
		while(MySQL.next(rs)){
			datas.add(getPlayerData(player, MySQL.getInt(rs, "id")));
		}
		return datas;
	}
	
	public static boolean hasCharacter(Player player, int charID){
		if(getPlayerData(player, charID) == null) return false;
		return true;
	}
	
	public static PlayerData getPlayerData(Player player, int charID){
		for(PlayerData data : PlayerData.getPlayerData()){
			if(data.getPlayer().equals(player) && data.getCharID() == charID) return data;
		}
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM playerdata WHERE player = '" + player.getName() + "' AND id = " + charID + ";");
		if(MySQL.next(rs)){
			return new PlayerData(player, charID, Integer.parseInt(MySQL.getString(rs, "experience")), Integer.parseInt(MySQL.getString(rs, "health")), Integer.parseInt(MySQL.getString(rs, "level")), MySQL.getString(rs, "race"), MySQL.getString(rs, "class"), Integer.parseInt(MySQL.getString(rs, "coins")), Integer.parseInt(MySQL.getString(rs, "mana")));
		}
		return null;
	}
	
	public static PlayerData createCharacter(Player player, int charID, Race race, CubeClass cubeClass){
		return new PlayerData(player, charID, race, cubeClass);
	}
	
	public static void removeCharacter(Player player, int charID){
		MySQL.prepareStatement("DELETE FROM playerdata WHERE playername = '" + player.getUniqueId().toString() + "' AND id = " + charID + ";");
		for(int i = playerData.size()-1; i >= 0; i--){
			PlayerData data = playerData.get(i);
			if(data.getPlayer().equals(player) && data.getCharID() == charID){
				playerData.remove(data);
				return;
			}
		}
	}	
	
	public static double calculateHealth(CubeClass cubeClass, int level){
		double scalingFactor = 100;
		double classCoefficient = cubeClass.getHealthCoefficient();
		double baseHealth = scalingFactor * classCoefficient * Math.pow(2, (3 * (level - 1)/(level + 19) + 1));
		return baseHealth;
	}
	
	public static double calculateExperienceNeeded(int level){
		double exp = (1050 * level - 50)/(level + 19);
		return exp;
	}
	
	public String toString(){
		String s = "";
		s += "Player: " + player.getName()+ "\n";
		s += "CharID: " + charID+ "\n";
		s += "Experience: " + exp+ "\n";
		s += "Health: " + health+ "\n";
		s += "Level: " + level+ "\n";
		s += "Race: " + race.toString()+ "\n";
		s += "PlayerClass: " + cubeClass.toString()+ "\n";
		s += "Coins: " + coins+ "\n";
		s += "Mana: " + mana + "\n";	
		s += "Current: " + current + "\n";
		return s;
	}

	public Player getPlayer() {
		return player;
	}

	public int getCharID() {
		return charID;
	}

	public int getExp() {
		return exp;
	}

	public boolean isCurrent(){
		return current;
	}

	public int getCoins() {
		return coins;
	}

	

	public void setExp(int exp) {
		this.exp = exp;
	}



	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setCurrent(boolean bool){
		if(bool == false){
			this.current = false;
			return;
		}
		for(PlayerData data : PlayerData.getPlayerData(player)){
			data.setCurrent(false);
		}
		this.current = bool;
	}


	public boolean isGliding(){
		return Glider.players.contains(player);
	}
	
}
