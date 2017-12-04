package logic;

import java.io.Serializable;
import java.util.*;

public class Wave implements Serializable{
	private enum WaveState {WAIT_WAVE, WAIT_ENEMY, OVER};
	private WaveState currState;
	private ListIterator<Enemy> troopIt;
	private int waveNumber;
	private long delayWave;
	private long delayEnemy;	//could be an array if different wait times
	private int tickCount;
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

	public void init() {
		currState = WaveState.WAIT_WAVE;
		troopIt = troopType.listIterator();
		
	}
	
	public void tick() {
		WaveState nextState = currState;
		++tickCount;
		switch(currState) {
		case WAIT_WAVE:
			if(tickCount >= delayWave) {
				if(troopIt.hasNext()) {
					//currentEnemy = troopIt.next();
					nextState = WaveState.WAIT_ENEMY;
				}
				else {
					nextState = WaveState.OVER;
				}
				tickCount = 0;
			}
			else {
				nextState = WaveState.WAIT_WAVE;
			}
			
			break;
			
		case WAIT_ENEMY:
			if(tickCount >= delayEnemy) {
				currentEnemy = troopIt.next();
				newEnemy = true;
				if(troopIt.hasNext()) {
					nextState = WaveState.WAIT_ENEMY;
				}
				else {
					nextState = WaveState.OVER;
				}
				tickCount = 0;
			}
			else {
				nextState = WaveState.WAIT_ENEMY;
			}
		break;
			
		case OVER: 
			waveOver = true;
			nextState = WaveState.OVER;
			break;
			
		}
		
		currState = nextState;
	}
	
	
	public Enemy deployEnemy() {
		Enemy temp = currentEnemy;
		newEnemy = false;

		return temp;
	}
	

	
	
	
	
}
