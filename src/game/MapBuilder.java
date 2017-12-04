package game;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import logic.Enemy;
import logic.Map;
import logic.Path;
import logic.Slot;
import logic.Wave;

public class MapBuilder {
	public static Map buildMap(Game.Difficulty difficulty) {
		Map map = new Map();
		Path path = new Path();
		ArrayList<Point> subPath = new ArrayList<Point>();
		switch(difficulty) {
		case BEGINNER:
			try {
			for(int i = 646; i > 552; --i) {
				subPath.add(new Point(545, i));
			}
			for(int i = 544; i > 184; --i) {
				subPath.add(new Point(i, 552));
			}
			for(int i = 552; i > 252; --i) {
				subPath.add(new Point(184, i));
			}
			for(int i = 184; i < 1110; ++i) {
				subPath.add(new Point(i,252 ));
			}
			for(int i = 254; i < 552; ++i) {
				subPath.add(new Point(1110, i));
			}
			for(int i = 1110; i > 776; --i) {
				subPath.add(new Point(i, 552));
			}
			for(int i = 552; i < 646; ++i) {
				subPath.add(new Point(776, i));
			}
			path.setPath(subPath);
			
			map.addPath(path);
			
			Wave wave1 = new Wave();
			//Wave wave2 = new Wave();
			//Wave wave3 = new Wave();
			
			wave1.setWaveNumber(1);
			//wave2.setWaveNumber(2);
			//wave3.setWaveNumber(3);
			
			wave1.setDelayWave(1);
			//wave2.setDelayWave(50);
			//wave3.setDelayWave(50);
			
			wave1.setDelayEnemy(30);
			//wave2.setDelayEnemy(100);
			//wave3.setDelayEnemy(100);
			
			ArrayList<Enemy> enemyWave1 = new ArrayList<Enemy>();
			//ArrayList<Enemy> enemyWave2 = new ArrayList<Enemy>();
			//ArrayList<Enemy> enemyWave3 = new ArrayList<Enemy>();
			for(int i = 0; i < 1; ++i) {
				
				Enemy enemy1 = new Enemy(Enemy.EnemyClass.STRENGTH);
				enemy1.setPath(path);
				Enemy enemy2 = new Enemy(Enemy.EnemyClass.SPEED);
				enemy2.setPath(path);
				Enemy enemy3 = new Enemy(Enemy.EnemyClass.HEALTH);
				enemy3.setPath(path);
				
				
				
				enemyWave1.add(enemy1);
				enemyWave1.add(enemy2);
				enemyWave1.add(enemy3);
			}
			
			wave1.setTroopType(enemyWave1);
			//wave2.setTroopType(enemyWave2);
			//wave3.setTroopType(enemyWave3);
			
			ArrayList<Wave> waves = new ArrayList<Wave>();
			waves.add(wave1);
			//waves.add(wave2);
			//waves.add(wave3);
			
			//NOTE: slot point is the lower left corner of what is drawn (inside the border)
			Slot slot1 = new Slot();
			Slot slot2 = new Slot();
			Slot slot3 = new Slot();
			slot1.setPos(new Point(241,312));
			slot2.setPos(new Point(992, 475));
			slot3.setPos(new Point(628, 621));
			map.getSlots().add(slot1);
			map.getSlots().add(slot2);
			map.getSlots().add(slot3);
			
			map.setWaves(waves);
			map.setTowerPos(new Point(776, 646));
			map.setBackground(ImageIO.read(new File("images/beginner.png")));
			map.setBackgroundFileName("images/beginner.png");
			
			//Map.saveMap(beginner, "Level1.ser");
			}catch(IOException e) {
				e.printStackTrace();
			}
			
			return map;
			
		case INTERMEDIATE:
			
			return map;
			
		case ADVANCED:
			
			return map;
			
			default: 
				
				return new Map();
		}
		
		
	}
}
