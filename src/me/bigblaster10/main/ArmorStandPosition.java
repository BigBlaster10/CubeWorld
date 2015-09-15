package me.bigblaster10.main;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

public class ArmorStandPosition {

	EulerAngle headPosition = new EulerAngle(0,0,0);
	EulerAngle bodyPosition = new EulerAngle(0,0,0);
	
	EulerAngle leftLegPos = new EulerAngle(0,0,0);
	EulerAngle rightLegPos = new EulerAngle(0,0,0);
	
	EulerAngle rightArmPos = new EulerAngle(0,0,0);
	EulerAngle leftArmPos = new EulerAngle(0,0,0);
	
	int direction;
	
	public ArmorStandPosition(){
		
	}
	
	public void setArmorStandPosition(ArmorStand stand){
		stand.setHeadPose(headPosition);
		stand.setBodyPose(bodyPosition);
		
		stand.setLeftLegPose(leftLegPos);
		stand.setRightLegPose(rightLegPos);
		
		stand.setLeftArmPose(leftArmPos);
		stand.setRightArmPose(rightArmPos);
		Location loc = stand.getLocation();
		loc.setYaw(direction);
		stand.teleport(loc);		
	}
	
	
	
	
	
	public void setHeadPose(EulerAngle headPosition){
		this.headPosition = headPosition;
	}
	
	public void setBodyPosition(EulerAngle bodyPosition){
		this.bodyPosition = bodyPosition;
	}
	
	public void setLeftLegPose(EulerAngle leftLegPos){
		this.leftLegPos = leftLegPos;
	}
	
	public void setRightLegPose(EulerAngle rightLegPos){
		this.rightLegPos = rightLegPos;
	}
	
	public void setRightArmPose(EulerAngle rightArmPos){
		this.rightArmPos = rightArmPos;
	}
	
	public void setLeftArmPose(EulerAngle leftArmPos){
		this.leftArmPos = leftArmPos;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
	}
	
	
}
