package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import logic.*;
import logic.Map;


/*Game: Controls the loop and logic for the game*/
public class Game implements Runnable{
	public enum Difficulty {BEGINNER, INTERMEDIATE, ADVANCED};
	private Difficulty difficulty;
	private ListIterator<Wave> waveIt;
	
	private long turnTime;
	private GameScreen gameScreen;
	private Player player;
	private Map map;
	private ArrayList<Wave> waves;
	private Wave currWave;
	private ArrayList<Enemy> enemies;
	private ArrayList<Fighter> fighters;
	private boolean gameOver;
	private BufferedImage screen;		//Is the screen of the game minu the overlay 
	
	
	
	Game() {
		//waveIt = waves.listIterator();
		enemies = new ArrayList<Enemy>();
		turnTime = 100; //arbritrary

	}
	
	Game(GameScreen gameScreen) {
		//waveIt = waves.listIterator();
		turnTime = 10; //arbritrary
		this.gameScreen = gameScreen;
		difficulty = gameScreen.getDifficulty();
	}

	
	public long getTurnTime() {
		return turnTime;
	}

	public void setTurnTime(long turnTime) {
		this.turnTime = turnTime;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public ArrayList<Wave> getWaves() {
		return waves;
	}

	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}

	public Wave getCurrWave() {
		return currWave;
	}

	public void setCurrWave(Wave currWave) {
		this.currWave = currWave;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}


	public BufferedImage getScreen() {
		return screen;
	}

	public void setScreen(BufferedImage screen) {
		this.screen = screen;
	}

	//called when game begins
	public void init(){
		if(map == null || player == null) {
			System.out.println("map or player not set");
			System.exit(0);
		}
		gameOver = false;
		waves = map.getWaves();
		waveIt = waves.listIterator();
		currWave = waveIt.next();
		enemies = new ArrayList<Enemy>();
		fighters = new ArrayList<Fighter>();
		gameOver = false;
		
		screen = new BufferedImage(map.getBackground().getWidth(), map.getBackground().getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		//start current wave
		currWave.init();
	}
	
	//acts as a turn of the game; each aspect gets updated in sequence
	public void tick() {
		
		//tick current wave (manages enemy deploy times)
		currWave.tick();
		if(currWave.isNewEnemy()) {
			enemies.add(currWave.deployEnemy());
		}
		if(currWave.isWaveOver()) {
			if(waveIt.hasNext()) {
				currWave = waveIt.next();
				currWave.init();
			}
		}
		
		//move each enemy forward
		for(Enemy temp: enemies) {
			if(!temp.isAtEnd()) {
				temp.moveForward();
			}
		}
		
		//for each fighter, detect enemies and attack them
		for(Fighter tempF: fighters) {
			Enemy target = tempF.detectEnemy(enemies);
			if(target != null && tempF.isAttackReady()) {
				tempF.attack(target);
				if(!target.isAlive()) {
					//Enemy is dead
					player.gainMoney(target.getCost());
					player.addScore(target.getCost());
					enemies.remove(target);
				}
			}

		}
		
		//remove enemies at end of path
		ArrayList<Enemy> tempEnemies = new ArrayList<Enemy>();
		for(Enemy tempE: enemies) {
			tempEnemies.add(tempE);
			if(tempE.isAtEnd()) {
				tempE.attack(player);
				tempE.setHealth(0);
				tempEnemies.remove(tempE);
				if(!player.isAlive()) {
					gameOver = true;
				}
				
			}
		}
		
		enemies = tempEnemies;
		
		//Handle Player Input
		if(gameScreen.getUnprocessedPlayerAction()!=null) {
			switch(gameScreen.getUnprocessedPlayerAction()) {
			case BUY:
				Fighter newFighter = new Fighter(gameScreen.getSelectedFighterClass());
				
				if(player.getMoney() - newFighter.getCost() >= 0) {		
					player.depleteMoney((int)newFighter.getCost());
					newFighter.setSlot(gameScreen.getSelectedSlot());
					fighters.add(newFighter);
					newFighter.levelUp();
				}
				break;
			case SELL:
				Fighter tempFighter = gameScreen.getSelectedFighter();
				if(tempFighter != null) {
					player.gainMoney((int)tempFighter.getValue());
					fighters.remove(tempFighter);
					tempFighter.getSlot().removeFighter();
				}
				gameScreen.setSelectedFighter(null);
				tempFighter = null;
				//this fighter should no longer exist
				break;
			case UPGRADE:
				Fighter tempFighter1 = gameScreen.getSelectedFighter();
				if(player.getMoney() - tempFighter1.getCost() >= 0 && tempFighter1.getLevel() < tempFighter1.getMaxLevel()) {
					player.depleteMoney((int)tempFighter1.getCost());
					tempFighter1.levelUp();
				}
				break;
			}
			gameScreen.setUnprocessedPlayerAction(null);	
		}
		
		if(enemies.isEmpty() && currWave.isWaveOver() && currWave.equals(waves.get(waves.size()-1))) {
			gameOver = true;
		}
	}
	
	
	//manages game time
	public void run() {
		double currTime;
		double prevTime;

		double msPerTick = 50;	
		double unprocessedTicks = 1;
		
		boolean shouldRender = false;
		
		init();
		currTime = System.nanoTime();
		
		while(!isGameOver()) {
			//calculate how many ticks are due since the last time we ticked
			prevTime = currTime;
			currTime = System.nanoTime();
			double timeDiff = currTime - prevTime;
			unprocessedTicks += ((currTime - prevTime) / (1000000*msPerTick));
			

			//only render if the game has ticked
			if(unprocessedTicks >= 1) {
				shouldRender = true;
			}
			
			//tick
			for(int i = 1; i <= unprocessedTicks; ++i) {
				tick();
				unprocessedTicks--;
			}
			
			
			if(shouldRender) {
				render();
				gameScreen.repaint();
				shouldRender = false;
			}
			
			
		}
		
		//hands off to gameScreen, which directs gameGUI to end game screen
		gameScreen.handleEndGame(player);
		
	}

	//writes all individual sprites to a single bufferedimage which will be drawn onto gameScreen
	private void render() {
		Graphics g = screen.getGraphics();
		
		//draw backGround
		g.drawImage(map.getBackground(), 0, 0, null);
		//draw each enemy sprite at that sprite's position
		//draw the enemy's health over its head
		for(Enemy enemy : enemies) {
			g.drawString(enemy.getHealth() + "/" + enemy.getMaxHealth(), (int) enemy.getSprite().getPosition().getX(), (int) enemy.getSprite().getPosition().getY() - 5);
			g.drawImage(enemy.getSprite().getCurrImage(), (int) enemy.getSprite().getPosition().getX(), (int) enemy.getSprite().getPosition().getY(), null);
		}
		//draw each fighter sprite
		for(Fighter fighter : fighters) {	
			g.drawImage(fighter.getSprite().getCurrImage(), (int) fighter.getSprite().getPosition().getX(), (int) fighter.getSprite().getPosition().getY(), null);
		}
		
	}
	
}