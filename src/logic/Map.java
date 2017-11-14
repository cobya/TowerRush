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


public class Map implements Serializable {
	private Path path;
	private Point towerPos;
	private ArrayList<Fighter> fighters;
	private ArrayList<Wave> waves;
	private BufferedImage background;
	private String backgroundFileName;
	
	public Map() {
		fighters = new ArrayList<Fighter>();
	}
	
	public String getBackgroundFileName() {
		return backgroundFileName;
	}

	public void setBackgroundFileName(String backgroundFileName) {
		this.backgroundFileName = backgroundFileName;
	}

	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
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
	public void setWaves(ArrayList<Wave> waves) {
		this.waves = waves;
	}
	public BufferedImage getBackground() {
		return background;
	}
	public void setBackground(BufferedImage background) {
		this.background = background;
	}
	public static void saveMap(Map map, String fileName) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;

		
		try 
		{			
			fileOut = new FileOutputStream( fileName );		//the Employee object makes its way to serial data in the file Employee.ser
			objOut = new ObjectOutputStream(fileOut);
			writeMap(objOut, map);
			objOut.close();
			fileOut.close();
	     }	
		
		catch(IOException i)
	    {
			i.printStackTrace();
	    }	
	}
	
	//Writes map by serializing each bufferedimage member seperately
	private static void writeMap(ObjectOutputStream out, Map map) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		
		ImageIO.write(map.getBackground(), "png", buffer);
		out.writeInt(buffer.size());
		buffer.writeTo(out);
		buffer.close();
		
		map.setBackground(null);
		int numWaves = map.getWaves().size();
		out.writeInt(numWaves);
		for(Wave wave : map.getWaves()) {
			out.writeInt(wave.getTroopType().size());
			for(Enemy enemy : wave.getTroopType()) {
				buffer = new ByteArrayOutputStream();
				ImageIO.write(enemy.getSprite(), "png", buffer);
				out.writeInt(buffer.size());
				buffer.writeTo(out);
				
				enemy.setSprite(null);
			}
		}
		
		out.writeObject((Map)map);
        
        
    }
	
	public static Map loadMap(String fileName){
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		Map map = new Map();
			
		try
		{
			fileIn = new FileInputStream(fileName);
			objIn = new ObjectInputStream(fileIn);
			
			do {
				map = (Map) readMap(objIn);
				
			} while(objIn.available() > 0);
			
			objIn.close();
			fileIn.close();
			
			//map.setBackground(ImageIO.read(new File(map.getBackgroundFileName())));
		}
		catch(IOException i)
		{
			i.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return map;
	}
	
	//reads map by serializing each bufferedimage member seperately
	private static Map readMap(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		Map map = new Map();
		BufferedImage background;
		ArrayList<ArrayList<BufferedImage>> troops = new ArrayList<ArrayList<BufferedImage>>();
		ArrayList<BufferedImage> currEnemies = new ArrayList<BufferedImage>();
		int numWaves, numEnemies;
		
		int size;
		byte[] buffer;
		
		
		size = stream.readInt();
		buffer = new byte[size];
		stream.readFully(buffer);
		background = (BufferedImage)ImageIO.read(new ByteArrayInputStream(buffer));
		
		numWaves = (int)stream.readInt();
		for(int i = 0; i < numWaves; ++i) {
			numEnemies = (int) stream.readInt();
			troops.add(new ArrayList<BufferedImage>());
			for(int j = 0; j < numEnemies; ++j) {
				size = stream.readInt();
				buffer = new byte[size];
				stream.readFully(buffer);
				troops.get(i).add( (BufferedImage)ImageIO.read(new ByteArrayInputStream(buffer)));
			}
		}
		map = (Map) stream.readObject();
		
		
		map.setBackground(background);
		Wave wave;
		Enemy enemy;
		for(int i = 0; i < map.getWaves().size(); ++i){
			wave = map.getWaves().get(i);
			for(int j = 0; j < wave.getTroopType().size(); ++j) {
				enemy = wave.getTroopType().get(j);
				enemy.setSprite(troops.get(i).get(j));
			}
		}
		
		return map;
		
	
	}
	
	
}
