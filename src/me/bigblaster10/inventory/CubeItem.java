package me.bigblaster10.inventory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.bigblaster10.main.CubeClass;
import me.bigblaster10.utils.Item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CubeItem {

	ItemRarity rarity;
	CubeClass cubeClass;
	
	Material mat;
	String displayName;
	int damage;
	int health;
	int level;
	double tempo;
	double critical;
	double reg;	
	double armor;
	
	String id;
	
	static ArrayList<CubeItem> items = new ArrayList<CubeItem>();
	
	public CubeItem(Material mat, String displayName, int level, ItemRarity rarity, CubeClass cubeClass, int damage, int health, double tempo, double critical, double reg, double armor){
		this.mat = mat;
		this.displayName = displayName;
		this.rarity = rarity;
		this.cubeClass = cubeClass;
		this.damage = damage;
		this.health = health;
		this.tempo = tempo;
		this.critical = critical;
		this.reg = reg;		
		this.armor = armor;
		this.level = level;
		this.id = ItemID.generateID();
		items.add(this);
	}
	
	
	public static final double DAMAGE_MULTIPLIER = 1;
	public static final double HEALTH_MULTIPLIER = 0.4;
	public static final double TEMPO_MULTIPLIER = 0.01;
	public static final double CRIT_MULTIPLIER = 0.1;
	public static final double REG_MULTIPLIER = 0.6;
	public static final double ARMOR_MULTIPLIER = 0.6;

	
	
	public CubeItem(Material mat, String displayName, int level, CubeClass cubeClass, WeaponType type, ItemRarity rarity){
		this.mat = mat;
		this.displayName = displayName;
		this.rarity = rarity;
		this.cubeClass = cubeClass;
		this.damage = (int) Math.round(type.getWeaponMultiplier() * DAMAGE_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.health = (int) Math.round(type.getWeaponMultiplier() * HEALTH_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.tempo = (type.getWeaponMultiplier() * TEMPO_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.critical = (type.getWeaponMultiplier() * CRIT_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.reg = (type.getWeaponMultiplier() * REG_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.id = ItemID.generateID();
		this.level = level;

		items.add(this);
	}
	
	public CubeItem(Material mat, String displayName, int level, CubeClass cubeClass, ArmorType type, ItemRarity rarity){
		this.mat = mat;
		this.displayName = displayName;
		this.rarity = rarity;
		this.cubeClass = cubeClass;
		this.health = (int) Math.round(type.getArmorMultiplier() * HEALTH_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.reg = (int) Math.round(type.getArmorMultiplier() * REG_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.armor = (int) Math.round(type.getArmorMultiplier() * ARMOR_MULTIPLIER * (Math.random()) * rarity.getMultiplier() * Math.sqrt(((level * 123) - 53)));
		this.id = ItemID.generateID();
		this.level = level;

		items.add(this);
	}
	
	public ItemStack getItemStack(){
		Item item = new Item(new ItemStack(mat), displayName);
		List<String> lore = new ArrayList<String>();
		lore.add("Level: " + level);
		if(damage != 0){
			lore.add("DMG: " + damage);
		}if(health != 0){
			lore.add("HP: " + health);
		}if(reg != 0){
			lore.add("REG: " + formatDecimal(reg));
		}if(armor != 0){
			lore.add("ARMOR: " + armor);
		}if(tempo != 0){
			lore.add("TEMPO: " + formatDecimal(tempo) + "%");
		}if(critical != 0){
		lore.add("CRIT: " + formatDecimal(critical) + "%");
		}
		lore.add("ID: " + id);
		item.setLore(lore);
		return item;
	}
	
	public static String formatDecimal(double number) {
		  DecimalFormat df = new DecimalFormat("0.00");
		  return df.format(number).replaceAll("\\.00$", "");
	}

	public static CubeItem getCubeItem(ItemStack item){
		
		if(!item.hasItemMeta()) return null;
		if(!item.getItemMeta().hasLore()) return null;
		List<String> lore = item.getItemMeta().getLore();
		String string = "";

		for(String s : lore){
			if(s.indexOf("ID:") != 0) continue;
			string = s.replace("ID: ", "");
			string = string.trim();
			break;
		}
		if(string == "") return null;
		for(CubeItem i : items){
			if(i.getID().equals(string)) return i;
		}
		return null;
	}
	
	
	public String getMySQLStats(){
		String s = "";
		s += damage + ":";
		s += health + ":";
		s += tempo + ":";
		s += critical + ":";
		s += reg + ":";
		s += armor + ":";
		s += level + "^";
		return s;
	}
	
	public static ArrayList<CubeItem> getCubeItems(){
		return items;
	}
	
	public static CubeItem getItemFromMySQLStats(Material mat, String displayName, String rarity, String cubeClass, String s){
		ItemRarity r = ItemRarity.getRarity(rarity);
		CubeClass cClass = CubeClass.getCubeClass(cubeClass);
		int damage = -1;
		int health = -1;
		double tempo = -1;
		double critical = -1;
		double reg = -1;
		double armor = -1;
		int level = -1;
		String temp = "";
		for(int i = 0; i < s.length(); i++){
			if(s.substring(i, i+1).equals(":")){
				if(damage == -1){
					damage = Integer.valueOf(temp);
				}else if(health == -1){
					health = Integer.valueOf(temp);
				}else if(tempo == -1){
					tempo = Double.valueOf(temp);
				}else if(critical == -1){
					critical = Double.valueOf(temp);
				}else if(reg == -1){
					reg = Double.valueOf(temp);
				}else if(armor == -1){
					armor = Double.valueOf(temp);
				}else if(armor == -1){
					level = Integer.valueOf(temp);
				}			
				temp = "";
				continue;
			}
			if(s.substring(i, i+1).equals("^")){
				level = Integer.valueOf(temp);
				break;
			}
			temp +=	s.substring(i, i+1);			
		}
		return new CubeItem(mat, displayName, level, r, cClass, damage, health, tempo, critical, reg, armor);	
	}
	
	
	
	
	
	
	public String toString(){
		String s = "";
		s += rarity.getDisplayName() + "\n";
		s += cubeClass.getName() + "\n";
		s += damage + "\n";
		s += health + "\n";
		s += tempo + "\n";
		s += critical + "\n";
		s += reg + "\n";
		return s;
	}
	
	
	
	
	
	public ItemRarity getRarity() {
		return rarity;
	}

	public CubeClass getCubeClass() {
		return cubeClass;
	}

	public int getDamage() {
		return damage;
	}

	public int getHealth() {
		return health;
	}

	public double getTempo() {
		return tempo;
	}

	public double getCritical() {
		return critical;
	}

	public double getReg() {
		return reg;
	}
	
	public String getID(){
		return id;
	}
	
	
	
	
	
}
