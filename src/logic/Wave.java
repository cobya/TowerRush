package logic;

import java.util.ArrayList;

public class Wave {
	private ArrayList<Enemy> troopTypes;
	private double delayTime;
	private double totalTime;
	private int waveNumber;
	
	public ArrayList<Enemy> getTroopTypes() {
		return troopTypes;
	}
	public void setTroopTypes(ArrayList<Enemy> troopTypes) {
		this.troopTypes = troopTypes;
	}
	public double getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(double delayTime) {
		this.delayTime = delayTime;
	}
	public double getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
	public int getWaveNumber() {
		return waveNumber;
	}
	public void setWaveNumber(int waveNumber) {
		this.waveNumber = waveNumber;
	}
	
}
