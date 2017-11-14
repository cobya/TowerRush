package game;

import java.awt.FlowLayout;
import java.io.IOException;

import javax.swing.*;

public class GameGUI extends JFrame {
	private GameScreen gameScreen;
	//private Game game;
	
	public GameGUI() throws IOException {
		super();
		initGUI();
	}
	
	private void initGUI() throws IOException {
		//game = new Game();
		gameScreen = new GameScreen();
		
		
		add(gameScreen);
		setTitle("TowerRush");
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void startGame() {
		gameScreen.runGame();
	}
	

}
