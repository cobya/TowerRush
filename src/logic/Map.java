package logic;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.imageio.ImageIO;

import logic.Enemy.EnemyClass;

//Contains information on waves, fighters, slots, and paths
public class Map implements Serializable {
	private ArrayList<Path> paths;
	private Point towerPos;
	private ArrayList<Fighter> fighters;
	private ArrayList<Wave> waves;
	private ArrayList<Slot> slots;
	private BufferedImage background;
	private String backgroundFileName;
	
	public Map() {
		paths = new ArrayList<Path>();
		fighters = new ArrayList<Fighter>();
		waves = new ArrayList<Wave>();
		slots = new ArrayList<Slot>();
	}
	
	public String getBackgroundFileName() {
		return backgroundFileName;
	}

	public void setBackgroundFileName(String backgroundFileName) {
		this.backgroundFileName = backgroundFileName;
	}

	public ArrayList<Path> getPaths() {
		return paths;
	}
	public void addPath(Path path) {
		paths.add(path);
	}
	public Point getTowerPos() {
		return towerPos;
	}
	public void setTowerPos(Point towerPos) {
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
	public void setSlots(ArrayList<Slot> slots) {
		this.slots = slots;
	}
	public ArrayList<Slot> getSlots() {
		return slots;
	}
	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}
	public BufferedImage getBackground() {
		return background;
	}
	public void setBackground(BufferedImage background) {
		this.background = background;
	}

	
}
