package game;

import java.util.*;
import logic.*;

public class Game implements Runnable{
	
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
	
	
	
	Game() {
		waveIt = waves.listIterator();
		turnTime = 100; //arbritrary

	}
	
	Game(GameScreen gameScreen) {
		waveIt = waves.listIterator();
		turnTime = 100; //arbritrary
		this.gameScreen = gameScreen;
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

	public void gameInit(){
		gameOver = false;
		currWave = waves.get(0);
		enemies = new ArrayList<Enemy>();
		fighters = new ArrayList<Fighter>();
	}
	
	//May need to replace polling with logic-driven events, depending on performance
	public void run() {
		long timeStart, timeDiff;
		boolean gameOver = false;
		
		timeStart = System.currentTimeMillis();
		currWave.run();
		while(!isGameOver()) {
			
			if(currWave.isNewEnemy()) {
				enemies.add(currWave.deployEnemy());
				if(currWave.isWaveOver()) {
					if(waveIt.hasNext()) {
						currWave = waveIt.next();
						currWave.run();
					}
				}
			}
			
			for(Enemy temp: enemies) {
				if(temp.isAtEnd()) {
					temp.moveForward();
				}
			}
			
			for(Fighter tempF: fighters) {
				Enemy target = tempF.detectEnemy(enemies);
				if(target != null && tempF.isAttackReady()) {
					tempF.attack(target);
					if(!target.isAlive()) {
						//Enemy is dead
						player.gainMoney(target.getCost());
						enemies.remove(target);
					}
				}

			}
			
			for(Enemy tempE: enemies) {
				if(tempE.isAtEnd() && tempE.isAttackReady()) {
					tempE.attack(player);
					if(!player.isAlive()) {
						gameOver = true;
					}
				}
			}
			
			if(enemies.isEmpty() && currWave.isWaveOver() && currWave == waves.get(waves.size()-1)) {
				gameOver = true;
			}
			
			gameScreen.updateGraphics();
			
			timeDiff = System.currentTimeMillis() - timeStart;
			Thread.sleep(turnTime - timeDiff);
			timeStart = System.currentTimeMillis();	
		}
		
		//tell gameScreen to do endGame stuff
		
	}
}