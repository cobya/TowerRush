package game;

import java.util.*;
import logic.*;
import logic.Map;

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
		//waveIt = waves.listIterator();
		enemies = new ArrayList<Enemy>();
		turnTime = 100; //arbritrary

	}
	
	Game(GameScreen gameScreen) {
		//waveIt = waves.listIterator();
		turnTime = 10; //arbritrary
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
		waves = map.getWaves();
		waveIt = waves.listIterator();
		currWave = waveIt.next();
		enemies = new ArrayList<Enemy>();
		fighters = new ArrayList<Fighter>();
		player = new Player();
	}
	
	//May need to replace polling with logic-driven events, depending on performance
	public void run() {
		long timeStart, timeDiff;
		boolean gameOver = false;
		Thread waveThread = new Thread(currWave);

		timeStart = System.currentTimeMillis();
		waveThread.start();
		while(!isGameOver()) {
			
			//enemies.get(0).moveForward();
			
			if(currWave.isNewEnemy()) {
				enemies.add(currWave.deployEnemy());
				
				if(currWave.isWaveOver()) {
					if(waveIt.hasNext()) {
						currWave = waveIt.next();
						waveThread = new Thread(currWave);
						waveThread.start();
					}
				}
			}
			
			for(Enemy temp: enemies) {
				if(!temp.isAtEnd()) {
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
			
			if(enemies.isEmpty() && currWave.isWaveOver() && currWave == waves.get(waves.size()-1)) {
				gameOver = true;
			}
			
			gameScreen.repaint();
			
			timeDiff = System.currentTimeMillis() - timeStart;
			try {
				if(turnTime-timeDiff > 0) {
				Thread.sleep(turnTime - timeDiff);
				}
				else {
				}
				if(timeDiff > 0) {
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timeStart = System.currentTimeMillis();	
		}
		
		//tell gameScreen to do endGame stuff
		
	}
}