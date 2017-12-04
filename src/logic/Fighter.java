package logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Fighter implements Serializable{
	public enum FighterClass {
		RANGE, STRENGTH, SPEED	//Denotes dominant attribute
	}
	
	private FighterClass fClass;
	private long cooldownTime; //time between attacks i.e. speed
	private int attackDamage;
	private int range;
	private Point position;
	private double cost;
	private double value; 	//money earned for selling it
	private int level;
	private int maxLevel;
	private long lastAttackTime;
	private boolean attackReady;
	private Slot slot;
	private FighterSprite sprite;
	
	public Fighter() {
		lastAttackTime = 0;
		sprite = new FighterSprite(this);
		
	}
	
	//builds fighter according to class
	public Fighter(FighterClass fClass) {
		this.fClass = fClass;
		switch(fClass) {
		case RANGE:
			cost = 200;
			attackDamage = 15;
			cooldownTime = 1000;
			range = 500;
			break;
		case STRENGTH:
			cost = 200;
			attackDamage = 50;
			cooldownTime = 2000;
			range = 300;
			break;
		case SPEED:
			cost = 200;
			attackDamage = 10;
			cooldownTime = 500;
			range = 200;
			break;
		}
		
		value = 0;
		level = 0;
		maxLevel = 3;
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
	
	//set position AND update its sprite
	public void setPosition(Point position) {
		this.position = position;
		sprite.updatePositionDimension();
		
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(int level) {
		this.maxLevel = level;
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
	
	//mutually set slot
	public void setSlot(Slot slot) {
		this.position = (Point) slot.getPos().clone();
		this.slot = slot;
		if(!this.equals(slot.getFighter())) {
			slot.setFighter(this);
		}
		this.slot = slot;
		sprite.updatePositionDimension();
	}
	
	//re
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
	
	//increases stats an arbritrary amount
	//Note: level 0 is before the fighter is placed
	public void levelUp() {
		if(level == 0) {
			value = cost * 0.75;
			cost = cost * 0.50;
		}
		else {
			range = (int) (range * 1.5);
			attackDamage = (int)(attackDamage * 1.5);
			cooldownTime = (int)(cooldownTime * 0.8);
			cost += cost*0.50;
			value += cost * 0.75;
		}
		
		++level;
		
	}


	
	
	
	
	
	
}
