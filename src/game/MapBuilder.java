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
	public Map buildBeginnerMap() throws IOException {
		Map beginner = new Map();
		Path path = new Path();
		ArrayList<Point> subPath = new ArrayList<Point>();
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
		
		beginner.setPath(path);
		
		Wave wave1 = new Wave();
		Wave wave2 = new Wave();
		Wave wave3 = new Wave();
		
		wave1.setWaveNumber(1);
		wave2.setWaveNumber(2);
		wave3.setWaveNumber(3);
		
		wave1.setDelayWave(50);
		wave2.setDelayWave(50);
		wave3.setDelayWave(50);
		
		wave1.setDelayEnemy(100);
		wave2.setDelayEnemy(100);
		wave3.setDelayEnemy(100);
		
		ArrayList<Enemy> enemyWave1 = new ArrayList<Enemy>();
		ArrayList<Enemy> enemyWave2 = new ArrayList<Enemy>();
		ArrayList<Enemy> enemyWave3 = new ArrayList<Enemy>();
		for(int i = 0; i < 10; ++i) {
			Enemy enemy1 = new Enemy(Enemy.EnemyClass.STRENGTH);
			enemy1.setPath(path);
			Enemy enemy2 = new Enemy(Enemy.EnemyClass.SPEED);
			enemy2.setPath(path);
			Enemy enemy3 = new Enemy(Enemy.EnemyClass.STRENGTH);
			enemy3.setPath(path);
			
			
			
			enemyWave1.add(enemy1);
			enemyWave2.add(enemy2);
			enemyWave3.add(enemy3);
		}
		
		wave1.setTroopType(enemyWave1);
		wave2.setTroopType(enemyWave2);
		wave3.setTroopType(enemyWave3);
		
		ArrayList<Wave> waves = new ArrayList<Wave>();
		waves.add(wave1);
		waves.add(wave2);
		waves.add(wave3);
		
		Slot slot = new Slot();
		slot.setPos(new Point(241,312));
		
		beginner.getSlots().add(slot);
		beginner.setWaves(waves);
		beginner.setTowerPos(new Point(776, 646));
		beginner.setBackground(ImageIO.read(new File("images/beginner.png")));
		beginner.setBackgroundFileName("images/beginner.png");
		
		//Map.saveMap(beginner, "Level1.ser");
		
		return beginner;
	}
}
