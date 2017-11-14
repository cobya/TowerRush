package game;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import logic.Enemy;
import logic.Map;


public class GameScreen extends JPanel{
	private Game game;
	private Thread gameThread;
	private BufferedImage background;
	boolean gameStart;
	
	public GameScreen() throws IOException {
		gameStart = false;
		initGameScreen();
	}
	
	private void initGameScreen() throws IOException  {
		game = new Game(this);
		
		game.setMap(Map.loadMap("Level1.ser"));
		background = game.getMap().getBackground();	
	
		setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
		gameStart = false;

	}
	
	public void runGame() {
		gameStart = true;
		game.gameInit();
		
		gameThread = new Thread(game);
		gameThread.start();
	}
	
	
	private void drawEnemy(Enemy enemy, Graphics g) {
		int centerX = (int) enemy.getPosition().getX() - enemy.getSprite().getWidth()/2;
		int centerY = (int) enemy.getPosition().getY() - enemy.getSprite().getHeight()/2;
		g.drawImage(enemy.getSprite(), centerX, centerY, this);
	}
	
	//paint background
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		if(gameStart) {
			for(Enemy enemy : game.getEnemies()) {
				drawEnemy(enemy, g);
			}
		}
		
	}
	
}
