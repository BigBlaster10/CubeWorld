package me.bigblaster10.inventory;

import java.util.Random;

public class ItemID {

	
	public static String generateID(){
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rand = new Random();
		String s = "";
		for(int i = 0; i < 10; i++){
			int a = rand.nextInt(chars.length());
			s += chars.substring(a, a+1);
		}
		return s;		
	}
	
	
	
}
