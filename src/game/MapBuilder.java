package game;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.util.Random;

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

		// Advanced mode extra paths
		Path path2 = new Path();
		ArrayList<Point> subPath2 = new ArrayList<Point>();
		Path path3 = new Path();
		ArrayList<Point> subPath3 = new ArrayList<Point>();

		switch(difficulty) {
		case BEGINNER:
			try {
			
				//build path; create subpath and add that to the path object. Each for loop is just a line. You can find
				//the coordinates by opening the map png in paint.net or something
					
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
				
				//still playing with waves to make the game kind of balanced
				
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
				for(int i = 0; i < 10; ++i) {
					
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
				
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			return map;
			
		case INTERMEDIATE:
			
			try {
				
				// 5 tower slots, making waves harder on this mode (to do)
					
				for(int i = 0; i < 198; ++i) {
					subPath.add(new Point(i, 311));
				}
				for(int i = 311; i < 480; ++i) {
					subPath.add(new Point(198, i));
				}
				for(int i = 198; i < 649; ++i) {
					subPath.add(new Point(i,480));
				}
				for(int i = 480; i > 156; --i) {
					subPath.add(new Point(649, i));
				}
				for(int i = 649; i < 1195; ++i) {
					subPath.add(new Point(i, 156));
				}
				for(int i = 156; i < 646; ++i) {
					subPath.add(new Point(1195, i)); // set
				}
				path.setPath(subPath);
				
				map.addPath(path);
				
				//create and balance waves still to do
				
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
				for(int i = 0; i < 10; ++i) {
					
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
				Slot slot4 = new Slot();
				Slot slot5 = new Slot();
				slot1.setPos(new Point(40, 523));
				slot2.setPos(new Point(380, 604));
				slot3.setPos(new Point(612, 102));
				slot4.setPos(new Point(1168, 103));
				slot5.setPos(new Point(1064, 606));
				map.getSlots().add(slot1);
				map.getSlots().add(slot2);
				map.getSlots().add(slot3);
				map.getSlots().add(slot4);
				map.getSlots().add(slot5);
				
				map.setWaves(waves);
				map.setTowerPos(new Point(1194, 646));
				map.setBackground(ImageIO.read(new File("images/intermediate.png")));
				map.setBackgroundFileName("images/intermediate.png");
			
			} catch(IOException e) {
				e.printStackTrace();
			}

			return map;
			
		case ADVANCED:
			
			//Note: this map has three forks. Make a separate path for each fork. Randomly choose which path to give to each enemy.
			
			try {
				
					// 3 tower slots, very difficult waves on this mode (to do)
				
				// straight across path
				for(int i = 0; i < 1300; ++i) {
					subPath.add(new Point(i, 320));
				}
				path.setPath(subPath);

				// bottom left to end
				for(int i = 649; i > 320; --i) {
					subPath2.add(new Point(139, i));
				}
				for(int i = 139; i < 1300; i++) {
					subPath2.add(new Point(i, 320));
				}
				path2.setPath(subPath2);

				// top right to end
				for(int i = 0; i < 320; ++i) {
					subPath2.add(new Point(1126, i));
				}
				for(int i = 1126; i < 1300; i++) {
					subPath3.add(new Point(i, 320));
				}
				path3.setPath(subPath3);
				
				map.addPath(path);
				map.addPath(path2);
				map.addPath(path3);

				//create and balance waves still to do
				
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
				for(int i = 0; i < 10; ++i) {
					Enemy enemy1 = new Enemy(Enemy.EnemyClass.STRENGTH);
					Enemy enemy2 = new Enemy(Enemy.EnemyClass.SPEED);
					Enemy enemy3 = new Enemy(Enemy.EnemyClass.HEALTH);

					// Choosing a random path from the available 3
					Random rand = new Random();
					int randomPath = rand.nextInt(3) + 1;

					if(randomPath == 1) {
						enemy1.setPath(path);
						enemy2.setPath(path);
						enemy3.setPath(path);
					} else if(randomPath == 2) {
						enemy1.setPath(path2);
						enemy2.setPath(path2);
						enemy3.setPath(path2);
					} else {
						enemy1.setPath(path3);
						enemy2.setPath(path3);
						enemy3.setPath(path3);						
					}


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
				slot1.setPos(new Point(206, 613));
				slot2.setPos(new Point(638, 253));
				slot3.setPos(new Point(1190, 60));
				map.getSlots().add(slot1);
				map.getSlots().add(slot2);
				map.getSlots().add(slot3);
				
				map.setWaves(waves);
				map.setTowerPos(new Point(1299, 320)); // maybe wrong
				map.setBackground(ImageIO.read(new File("images/advanced.png")));
				map.setBackgroundFileName("images/advanced.png");
				
			}catch(IOException e) {
				e.printStackTrace();
			}

			return map;
			
		default: 
			return new Map();
		}
		
		
	}
}
