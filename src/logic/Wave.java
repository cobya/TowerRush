package logic;

import java.io.Serializable;
import java.util.*;

public class Wave implements Runnable, Serializable{
	private ListIterator<Enemy> troopIt;
	private int waveNumber;
	private long delayWave;
	private long delayEnemy;	//could be an array if different wait times
	private long timeStart, timeDiff;
	private ArrayList<Enemy> troopType;
	private Enemy currentEnemy;
	private boolean newEnemy, waveOver;
	
	public Wave() {
		troopType = new ArrayList<Enemy>();
		//troopIt = troopType.listIterator();
	}
	
	public ListIterator<Enemy> getTroopIt() {
		return troopIt;
	}

	public void setTroopIt(ListIterator<Enemy> troopIt) {
		this.troopIt = troopIt;
	}

	public void setWaveNumber(int waveNumber){
		this.waveNumber = waveNumber;
	}
	
	public int getWaveNumber() {
		return waveNumber;
	}
	
	public ArrayList<Enemy> getTroopType() {
		return troopType;
	}

	public void setTroopType(ArrayList<Enemy> troopType) {
		currentEnemy = troopType.get(0);
		this.troopType = troopType;
	}

	public void setDelayWave(long delayWave) {
		this.delayWave = delayWave;
	}
	
	public long getDelayWave() {
		return delayWave;
	}
	
	public long getDelayEnemy() {
		return delayEnemy;
	}

	public void setDelayEnemy(long delayEnemy) {
		this.delayEnemy = delayEnemy;
	}

	public long getTotalTime(){
		return delayWave + troopType.size()*delayEnemy;
	}
	
	public void setCurrentEnemy(Enemy enemy) {
		this.currentEnemy = enemy;
	}
	
	public Enemy getCurrentEnemy() {
		return currentEnemy;
	}
	
	public void setNewEnemy(boolean newEnemy) {
		this.newEnemy = newEnemy;
	}
	
	public boolean isNewEnemy() {
		return newEnemy;
	}
	
	public void setWaveOver(boolean over) {
		waveOver = over;
	}
	
	public boolean isWaveOver() {
		return waveOver;
	}

	
	public void run() {
		timeStart = System.currentTimeMillis();
		troopIt = troopType.listIterator();
		currentEnemy = troopIt.next();
		
		try {
			Thread.sleep(delayWave);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		timeStart = System.currentTimeMillis();
		while(!isWaveOver()) {
			newEnemy = true;
			
			if(troopIt.hasNext()) {
			timeDiff = System.currentTimeMillis() - timeStart;
			try {
				if(delayEnemy - timeDiff > 0) {
					Thread.sleep(delayEnemy - timeDiff);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			timeStart = System.currentTimeMillis();
			//enemy should have been deployed by now
			
				currentEnemy = troopIt.next();
			}
			else {
				waveOver = true;
			}
		}
		
	}
	
	public Enemy deployEnemy() {
		Enemy temp = currentEnemy;
		newEnemy = false;

		return temp;
	}
	

	
	
	
	
}
