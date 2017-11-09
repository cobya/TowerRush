package game;

import java.util.ArrayList;

import logic.Map;
import logic.Player;
import logic.Wave;

public class Game {
	private Player player;
	private Map map;
	private Wave wave;
	private ArrayList<int[][]> enemyPos;
	
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
	public Wave getWave() {
		return wave;
	}
	public void setWave(Wave wave) {
		this.wave = wave;
	}
	public ArrayList<int[][]> getEnemyPos() {
		return enemyPos;
	}
	public void setEnemyPos(ArrayList<int[][]> enemyPos) {
		this.enemyPos = enemyPos;
	}
}
