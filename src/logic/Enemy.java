package logic;

import java.awt.Image;
import java.awt.Point;

public class Enemy {
	public enum EnemyClass {
		SPEED, STRENGTH, HEALTH  //Denotes dominant attribute; will rename
	}
	
	private EnemyClass eClass;
	private int travelSpeed;
	private int attackSpeed;
	private int health;
	private int maxHealth;
	private int attackDamage;
	private Path path;
	private Point position;
	private double step;
	private int cost;
	private boolean alive, atEnd, attackReady;
	private Image sprite;
	
	
	Enemy() {
		position = new Point();
	}
	
	Enemy(EnemyClass eClass) {
		this.eClass = eClass;
		switch(eClass){
		case SPEED:
			break;
			
		case STRENGTH: 
			break;
		case HEALTH:
			break;
		}
	}

	public int getTravelSpeed() {
		return travelSpeed;
	}
	public void setTravelSpeed(int travelSpeed) {
		this.travelSpeed = travelSpeed;
	}
	public int getAttackSpeed() {
		return attackSpeed;
	}
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public double getStep() {
		return step;
	}
	public void setStep(double step) {
		this.step = step;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Image getSprite() {
		return sprite;
	}
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean isAtEnd() {
		return atEnd;
	}
	
	public void setAtEnd(boolean atEnd) {
		this.atEnd = atEnd;
	}
	
	public boolean isAttackReady() {
		return attackReady;
	}

	public void setAttackReady(boolean attackReady) {
		this.attackReady = attackReady;
	}

	public void attack(Player player) {
		
	}
	
	public void moveForward() {
		step += step*travelSpeed;
		
		position = path.findPos((int)step);
		
		if(position == null) {
			step = path.getPath().size()-1;
			position = path.findPos((int)step);
			atEnd = true;
		}
		
		
	}
	
}