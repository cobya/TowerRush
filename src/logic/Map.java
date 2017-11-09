package logic;

import java.awt.Image;
import java.util.ArrayList;

public class Map {
	private Path path;
	private int[][] towerPos;
	private ArrayList<Fighter> fighters;
	private ArrayList<Wave> waves;
	private Image background;
	
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public int[][] getTowerPos() {
		return towerPos;
	}
	public void setTowerPos(int[][] towerPos) {
		this.towerPos = towerPos;
	}
	public ArrayList<Fighter> getFighters() {
		return fighters;
	}
	public void setFighters(ArrayList<Fighter> fighters) {
		this.fighters = fighters;
	}
	public ArrayList<Wave> getWaves() {
		return waves;
	}
	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}
	public Image getBackground() {
		return background;
	}
	public void setBackground(Image background) {
		this.background = background;
	}
	
}
