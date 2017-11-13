package logic;

import java.awt.Image;
import java.awt.Point;

public class Enemy {
	public enum EnemyClass {
		SPEED, STRENGTH, HEALTH  //Denotes dominant attribute; will rename
	}
	
	private EnemyClass eClass;
	private int travelSpeed;
	private long cooldownTime;
	private long lastAttackTime;
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
		lastAttackTime = 0;
		step = 0;
		alive = true;
		atEnd = false;
	}
	
	Enemy(EnemyClass eClass) {
		this();
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
	public EnemyClass geteClass() {
		return eClass;
	}
	public void seteClass(EnemyClass eClass) {
		this.eClass = eClass;
	}
	public long getCooldownTime() {
		return cooldownTime;
	}
	public void setCooldownTime(long cooldownTime) {
		this.cooldownTime = cooldownTime;
	}
	public long getLastAttackTime() {
		return lastAttackTime;
	}
	public void setLastAttackTime(long lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
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


	public void setAttackReady(boolean attackReady) {
		this.attackReady = attackReady;
	}
	
	public boolean isAttackReady() {
		if(System.currentTimeMillis() - lastAttackTime > cooldownTime ) {
			attackReady = true;
		}
		else attackReady = false;
		
		return attackReady;
	}

	public void attack(Player player) {
		player.takeDamage(attackDamage);
		lastAttackTime = System.currentTimeMillis();
	}
	
	public void takeDamage(int dmg) {
		health -= dmg;
		if(health < 0) {
			//Enemy is dead - handle removing and awarding player in game loop
			health = 0;
			alive = false;
		}
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