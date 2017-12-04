package logic;

import java.io.Serializable;

import game.Game;

public class Player implements Serializable{
	private int score;
	private int health;
	private int maxHealth;
	private int money;
	private boolean alive;
	
	public Player() {
		score = 0;
		maxHealth = 200;
		health = maxHealth;
		money = 500;
		alive = true;
	}
	
	public Player(Game.Difficulty difficulty) {
		switch(difficulty) {
		case BEGINNER:
			score = 0;
			maxHealth = 200;
			health = maxHealth;
			money = 500;
			alive = true;
			break;
		case INTERMEDIATE:
			score = 0;
			maxHealth = 200;
			health = maxHealth;
			money = 500;
			alive = true;
			break;
		case ADVANCED:
			score = 0;
			maxHealth = 200;
			health = maxHealth;
			money = 500;
			alive = true;
			break;
		}
	}
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void takeDamage(int dmg) {
		health -= dmg;
		if(health <= 0) {
			health = 0;
			alive = false;
		}
	}
	
	public void gainMoney(int money) {
		this.money += money;
	}
	
	public void depleteMoney(int money) {
		this.money -= money;
	}
	
}
