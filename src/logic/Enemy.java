package logic;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Enemy implements Serializable {
	public enum EnemyClass {
		SPEED, STRENGTH, HEALTH  //Denotes dominant attribute; will rename
	}
	
	private EnemyClass eClass;
	private int travelSpeed;
	private int health;
	private int maxHealth;
	private int attackDamage;
	private Path path;
	private Point position;
	private double step;	//Determines how far along path it is
	private double stepCounter; //For sprite toggle
	private int cost;
	private boolean alive, atEnd;
	private EnemySprite sprite;
	
	
	public Enemy() throws IOException {
		position = new Point();
		step = 0;
		stepCounter = 0;
		alive = true;
		atEnd = false;
	}
	
	public Enemy(EnemyClass eClass) throws IOException {
		this();
		this.eClass = eClass;
		switch(eClass){
		case SPEED://All just arbritrary values right now
			maxHealth = 50;
			attackDamage = 10;
			travelSpeed = 5;
			cost = 100;
			sprite = new EnemySprite(this);	
			break;		
		case STRENGTH: 
			maxHealth = 50;
			attackDamage = 10;
			travelSpeed = 5;
			cost = 100;
			sprite = new EnemySprite(this);	
			break;
		case HEALTH:
			break;
		}
		
		health = maxHealth;
		
		
		
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
	public EnemySprite getSprite() {
		return sprite;
	}
	public void setSprite(EnemySprite sprite) {
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


	public void attack(Player player) {
		player.takeDamage(attackDamage);
	}
	
	public void takeDamage(int dmg) {
		health -= dmg;
		if(health <= 0) {
			//Enemy is dead - handle removing and awarding player in game loop
			health = 0;
			alive = false;
		}
	}
	
	public void moveForward() {
		Point newPos;
		step += travelSpeed;
		
		newPos = path.findPos((int)step);
		
		//if position == null, it has reached the end of the path
		if(newPos == null) {
			step = path.getPath().size()-1;
			newPos = path.findPos((int)step);
			atEnd = true;
		}
		
		if(newPos.x - position.x > 1) {
			sprite.setDirection(EnemySprite.Direction.RIGHT);
		}
		else if (newPos.x - position.x < -1) {
			sprite.setDirection(EnemySprite.Direction.LEFT);
		}
		else if (newPos.y - position.y > 1) {
			sprite.setDirection(EnemySprite.Direction.FRONT);
		}
		else if (newPos.y - position.y < -1) {
			sprite.setDirection(EnemySprite.Direction.BACK);
		}
		
		
		position = newPos;
		
		if(stepCounter >= 50) {
			stepCounter = 0;
			sprite.toggleStep();
		}
		else {
			stepCounter += travelSpeed;
		}
		
		sprite.updatePositionDimension();
		
	}
	
}