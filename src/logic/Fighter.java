package logic;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Fighter implements Serializable{
	public enum FighterClass {
		RANGE, STRENGTH, SPEED	//Denotes dominant attribute; will rename
	}
	
	private FighterClass fClass;
	private long cooldownTime; //time between attacks i.e. speed
	private int attackDamage;
	private int range;
	private Point position;
	private int cost;
	private int value; 	//money earned for selling it
	private int level;
	private long lastAttackTime;
	private boolean attackReady;
	private Slot slot;
	private FighterSprite sprite;
	
	public Fighter() {
		lastAttackTime = 0;
		sprite = new FighterSprite(this);
		
	}
	
	public Fighter(FighterClass fClass) {
		this.fClass = fClass;
		switch(fClass) {
		case RANGE:
	
			cost = 200;
			attackDamage = 10;
			cooldownTime = 500;
			range = 1000;
				
				
				
			
			break;
		case STRENGTH:
			break;
		case SPEED:
			break;
		}
		
		sprite = new FighterSprite(this);
	}
	
	public void setfClass(Fighter.FighterClass fClass) {
		this.fClass = fClass;
	}
	
	public Fighter.FighterClass getfClass() {
		return fClass;
	}
	
	public long getCooldownTime() {
		return cooldownTime;
	}
	public void setCooldownTime(int cooldownTime) {
		this.cooldownTime = cooldownTime;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
		sprite.updatePositionDimension();
		
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Slot getSlot() {
		return slot;
	}
	public FighterSprite getSprite() {
		return sprite;
	}
	public void setSprite(FighterSprite sprite) {
		this.sprite = sprite;
	}	
	
	public void setSlot(Slot slot) {
		this.position = (Point) slot.getPos().clone();
		this.slot = slot;
		if(!this.equals(slot.getFighter())) {
			slot.setFighter(this);
		}
		this.slot = slot;
		sprite.updatePositionDimension();
	}
	
	public boolean isAttackReady() {
		if(System.currentTimeMillis() - lastAttackTime > cooldownTime ) {
			attackReady = true;
		}
		else attackReady = false;
		
		return attackReady;
	}

	public void setAttackReady(boolean attackReady) {
		this.attackReady = attackReady;
	}
	
	
	public void attack(Enemy enemy) {
		sprite.drawAim(enemy);
		enemy.takeDamage(this.attackDamage);
		lastAttackTime = System.currentTimeMillis();
	}
	
	public Enemy detectEnemy(ArrayList<Enemy> enemies){
		Enemy temp = null;
		double maxStep = 0;
		double dist;
		for(Enemy enemy: enemies) {
			dist = Math.sqrt(Math.pow(position.x - enemy.getPosition().getX(), 2) + Math.pow(position.y - enemy.getPosition().getY(), 2));
			if( dist < range && enemy.getStep() > maxStep) {
				temp = enemy;
				maxStep = enemy.getStep();
			}
		}
			
		return temp;
		
	}
	
	public void levelUp() {
		range = (int) (range * 1.5);
		attackDamage = (int)(attackDamage * 1.5);
		cooldownTime = (int)(cooldownTime * 0.8);
	}
	
	
	
	
	
	
}
