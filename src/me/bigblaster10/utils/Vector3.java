package me.bigblaster10.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Vector3 {

	
	private double x;
	private double y;
	private double z;
	
	public Vector3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;	
	}
	
	public Vector3 add(Vector3 v){
		double x = v.x + this.x;
		double y = v.y + this.y;
		double z = v.z + this.z;
		return new Vector3(x,y,z);
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public Vector3 multiply(double d){
		return new Vector3(x * d, y * d, z * d);		
	}
	
	public Vector3 divide(double d){
		return new Vector3(x / d, y / d, z / d);		
	}
	
	public Vector3 normalize(){
		double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		return divide(magnitude);
	}
	
	public Vector3 minus(Vector3 v){
		return new Vector3(x - v.x, y - v.y, z - v.z);
	}
	
	
	public static double Distance(Vector3 start, Vector3 end){
		return Math.sqrt(Math.pow((end.x - start.x), 2) + Math.pow((end.y - start.y), 2) + Math.pow((end.z - start.z), 2));
	}
	
	public Vector3 clone(){
		return new Vector3(x,y,z);
	}
	
	public static Vector3 Lerp(Vector3 start, Vector3 end, float t){		
		return start.multiply((1 - t)).add(end.multiply(t));		
	}
	
	
	public static Vector3 fromLocation(Location loc){
		return new Vector3(loc.getX(), loc.getY(), loc.getZ());		
	}
	
	public static Location toLocation(World world, Vector3 v){
		return new Location(world, v.x,v.y,v.z);
	}
	
	public static Vector tovector(Vector3 v){
		return new Vector(v.x, v.y, v.z);
	}
	
	public String toString(){
		return "X: " + x + " Y: " + y + " Z: " + z;
	}
	
}
