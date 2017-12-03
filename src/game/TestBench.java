package game;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.*;

public class TestBench extends JFrame {
	ContentPanel contentPane = new ContentPanel();
	Enemy enemy;
	EnemySprite sprite;
	public TestBench() {
		try {
			enemy = new Enemy(Enemy.EnemyClass.STRENGTH);
			enemy.setPosition(new Point(50,50));
			enemy.setSprite(new EnemySprite(enemy));
			add(contentPane);
			setSize(500,500);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {	
		TestBench testBench = new TestBench();
		testBench.enemy.getSprite().setDirection(EnemySprite.Direction.LEFT);
		
		
		while(true) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testBench.enemy.getSprite().toggleStep();
			testBench.repaint();
		}
	}
	
	private class ContentPanel extends JPanel {
		public ContentPanel() {
			
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(enemy.getSprite().getCurrImage(), 0, 0, this);
			
		}
	}
	
	
	
}
