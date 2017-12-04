package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import game.GameScreen.PlayerAction;
import logic.Fighter.FighterClass;
import logic.Player;

/* GameGUI:
 * Includes all screens of the game. All screens except for GameScreen are nested within GameGUI.
 * Handles all player input that does not involve the game itself
 * 
 */

public class GameGUI extends JFrame {

	private Game.Difficulty difficulty;				//Denotes which map / player stats are used
	private GameScreen gameScreen;					//screen where game takes place
	private MainMenu mainMenu;						
	private HighScoreScreen highScoreScreen;
	private MapSelectScreen mapSelectScreen;
	private EndGameScreen endGameScreen;
	private Game game;
	
	public GameGUI() throws IOException {
		super();
		difficulty = Game.Difficulty.BEGINNER;
		initGUI();
	}
	
	public Game.Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Game.Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	//Goes straight to main menu
	private void initGUI() throws IOException {
		mainMenu = new MainMenu();
		
		add(mainMenu);
		setTitle("TowerRush");
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	//Switches to gameScreen and launches game
	public void startGame() {
		try {
		
		gameScreen = new GameScreen(this);
		add(gameScreen);
		game = new Game();
		gameScreen.runGame();
		remove(mainMenu);
		pack();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Decides how endgame will be handled
	public void handleEndGame(Player player) {
		ArrayList<HighScore> highScoreList;
		//if player health > 0, player won. Check to see if player got a high score
		if(player.getHealth() > 0) {
			highScoreList = loadHighScores();
			if(highScoreList == null) {
				highScoreList = new ArrayList<HighScore>();
			}
			if(highScoreList.size() < 10) {
				endGameScreen = new EndGameScreen(EndGameScreen.HIGHSCORE, player);
				GameGUI.this.remove(gameScreen);
				GameGUI.this.add(endGameScreen);
				pack();
				return;
			}
			for(int i = 0; i < highScoreList.size(); ++i) {
				if(player.getScore() > highScoreList.get(i).score) {
					endGameScreen = new EndGameScreen(EndGameScreen.HIGHSCORE, player);
					GameGUI.this.remove(gameScreen);
					GameGUI.this.add(endGameScreen);
					pack();
					return;
				}
			}
			endGameScreen = new EndGameScreen(EndGameScreen.WON, player);
			GameGUI.this.remove(gameScreen);
			GameGUI.this.add(endGameScreen);
			pack();
			return;
			
		}
		else {
			endGameScreen = new EndGameScreen(EndGameScreen.LOST, player);
			GameGUI.this.remove(gameScreen);
			GameGUI.this.add(endGameScreen);
			pack();
			return;
		}
	}
	
	//Main menu: has buttons to start game, view high scores, and select map
	private class MainMenu extends JPanel {
		private JLabel lblTowerRush;
		private JButton btnStartGame;
		private JButton btnMapSelect;
		private JButton btnHighScores;
		
		
		public MainMenu() {
			setPreferredSize(new Dimension(500,500));
			buildScreen();	
			setVisible(true);
		}
		
		public void buildScreen() {
			setBackground(Color.DARK_GRAY);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			add(Box.createVerticalStrut(30));
			
			lblTowerRush = new JLabel("Tower Rush");
			lblTowerRush.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 48));
			lblTowerRush.setForeground(Color.WHITE);
			lblTowerRush.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(lblTowerRush);
			
			add(Box.createVerticalStrut(80));
			
			btnStartGame = new JButton("Start Game");
			btnStartGame.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255)), new EmptyBorder(0, 5, 0, 5)));
			btnStartGame.setFont(new Font("Tahoma", Font.BOLD, 28));
			btnStartGame.setForeground(Color.WHITE);
			btnStartGame.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnStartGame.setAlignmentY(Component.TOP_ALIGNMENT);
			btnStartGame.setOpaque(false);
			btnStartGame.setFocusPainted(false);
			btnStartGame.setContentAreaFilled(false);
			btnStartGame.addActionListener(new ButtonListener());
			add(btnStartGame);
			
			add(Box.createVerticalStrut(20));
			
			btnMapSelect = new JButton("Map Select");
			btnMapSelect.setOpaque(false);
			btnMapSelect.setForeground(Color.WHITE);
			btnMapSelect.setFont(new Font("Tahoma", Font.BOLD, 28));
			btnMapSelect.setFocusPainted(false);
			btnMapSelect.setContentAreaFilled(false);
			btnMapSelect.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255)), new EmptyBorder(0, 5, 0, 5)));
			btnMapSelect.setAlignmentY(0.0f);
			btnMapSelect.setAlignmentX(0.5f);
			btnMapSelect.addActionListener(new ButtonListener());
			add(btnMapSelect);
			
			add(Box.createVerticalStrut(20));
			
			btnHighScores = new JButton("High Scores");
			btnHighScores.setOpaque(false);
			btnHighScores.setForeground(Color.WHITE);
			btnHighScores.setFont(new Font("Tahoma", Font.BOLD, 28));
			btnHighScores.setFocusPainted(false);
			btnHighScores.setContentAreaFilled(false);
			btnHighScores.setBorder(new CompoundBorder(new LineBorder(new Color(255, 255, 255)), new EmptyBorder(0, 5, 0, 5)));
			btnHighScores.setAlignmentY(0.0f);
			btnHighScores.setAlignmentX(0.5f);
			btnHighScores.addActionListener(new ButtonListener());
			add(btnHighScores);
			
		}
		
		//ButtonListener for MainMenu: takes the gui to a new screen, depending on which button was pressed 
		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) 
			{
				JButton source = (JButton)(e.getSource());
				
				if(source.equals(btnStartGame)) {
					handleStartGame();
				}
				else if(source.equals(btnMapSelect)) {
					handleMapSelectScreen();
				}
				else if(source.equals(btnHighScores)) {
					handleHighScoreScreen();
				}
			}
			
			//Execute GameGUI's startGame method
			public void handleStartGame() {
				startGame();
			}
			
			//go to high score screen
			public void handleHighScoreScreen() {
				highScoreScreen = new HighScoreScreen();
				GameGUI.this.remove(mainMenu);
				GameGUI.this.add(highScoreScreen);
				pack();
			}
			
			//go to mapSelect screen
			public void handleMapSelectScreen() {
				mapSelectScreen = new MapSelectScreen();
				GameGUI.this.remove(mainMenu);
				GameGUI.this.add(mapSelectScreen);
				pack();
			}

		}
	}

	//HighScoreScreen: displays high scores read from serial file
	private class HighScoreScreen extends JPanel {
		ArrayList<HighScore> highScores;			//Format: "NAME   ...   1,024"
		JLabel header;							//Denotes Name and Score column of list
		ArrayList<JLabel> highScoreLabels;
		
		
		public HighScoreScreen() {
			setPreferredSize(new Dimension(500,500));
			buildLabels();
			buildScreen();
			setVisible(true);
			GameGUI.this.repaint();
			
		}
		
		private void buildLabels() {
			header = new JLabel("   NAME         SCORE");
			highScores = loadHighScores();
			highScoreLabels = new ArrayList<JLabel>();
			
			//if no highscores, just write numbers
			if(highScores == null) {
				for(int i = 0; i < 10; ++i) {
					highScoreLabels.add(new JLabel(i + 1 + "."));
				}
				return;
			}
			else {
			
				for(int i = 0; i < 10; ++i) {
					JLabel tempLabel = new JLabel();
					if(!(i+1 > highScores.size())) {
						tempLabel.setText(i+1 + ". " + highScores.get(i).name + "   ...   " + highScores.get(i).score);
					}
					else {
						tempLabel.setText(i+1 + ". ");
					}
					highScoreLabels.add(tempLabel);
				}
			
			}
		
		}
		
		private void buildScreen() {
			setBackground(Color.DARK_GRAY);
			setBorder(new EmptyBorder(5, 5, 5, 5));
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			Component horizontalGlue = Box.createHorizontalGlue();
			add(horizontalGlue);
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			header.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
			header.setForeground(Color.WHITE);
			header.setAlignmentY(Component.TOP_ALIGNMENT);
			panel.add(header);
			
			for(JLabel tempScore : highScoreLabels) {
				tempScore.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
				tempScore.setForeground(Color.WHITE);
				tempScore.setAlignmentY(Component.TOP_ALIGNMENT);
				panel.add(tempScore);
			}
			
			panel.add(Box.createVerticalStrut(50));
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.DARK_GRAY);
			buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			panel.add(buttonPanel);
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			
			buttonPanel.add(Box.createRigidArea(new Dimension(50, 20)));
			
			JButton mainMenuButton = new JButton("Main Menu");
			mainMenuButton.addActionListener(new ButtonListener());
			buttonPanel.add(mainMenuButton);

			add(Box.createHorizontalGlue());
			
			
			
		}
		
		//Button takes user back to main menu
		private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				setMainMenu();
			}
			
			public void setMainMenu() {
				mainMenu = new MainMenu();
				GameGUI.this.remove(highScoreScreen);
				GameGUI.this.add(mainMenu);
				pack();
			}
		}
		
	}
	
	//Map select screen: shows mini picture of map and the corresponding difficulty. Leaving this page sets the difficulty
	private class MapSelectScreen extends JPanel {
		Game.Difficulty currentDifficulty;
		BufferedImage advancedMap;
		BufferedImage intermediateMap;
		BufferedImage beginnerMap;
		BufferedImage displayedMap;
		JLabel titleLabel;
		JLabel difficultyLabel;
		ImagePanel imagePanel;
		JPanel bottomPane;
		JButton leftButton;
		JButton rightButton;
		JButton backButton;
		
		MapSelectScreen() {
			try {
				advancedMap = ImageIO.read(new File("images/advanced.png"));
				intermediateMap = ImageIO.read(new File("images/intermediate.png"));
				beginnerMap = ImageIO.read(new File("images/beginner.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}


			
			setPreferredSize(new Dimension(500,500));
			buildScreen();
			setVisible(true);
			GameGUI.this.repaint();
		}
	
		private void displayMap(Game.Difficulty difficulty) {
			Graphics g;
			switch(difficulty) {
			case BEGINNER:
				displayedMap = new BufferedImage(beginnerMap.getWidth()/5, beginnerMap.getHeight()/5, BufferedImage.TYPE_INT_ARGB);
				g = displayedMap.createGraphics();
				g.drawImage(beginnerMap, 0, 0, beginnerMap.getWidth()/5, beginnerMap.getHeight()/5, null);
				g.dispose();
				break;
			case INTERMEDIATE:
				displayedMap = new BufferedImage(intermediateMap.getWidth()/5, intermediateMap.getHeight()/5, BufferedImage.TYPE_INT_ARGB);
				g = displayedMap.createGraphics();
				g.drawImage(intermediateMap, 0, 0, intermediateMap.getWidth()/5, intermediateMap.getHeight()/5, null);
				g.dispose();
				break;
			case ADVANCED:
				displayedMap = new BufferedImage(advancedMap.getWidth()/5, advancedMap.getHeight()/5, BufferedImage.TYPE_INT_ARGB);
				g = displayedMap.createGraphics();
				g.drawImage(advancedMap, 0, 0, advancedMap.getWidth()/5, advancedMap.getHeight()/5, null);
				g.dispose();
				break;
			}
			
			if(imagePanel != null) {
				imagePanel.repaint();
			}
		}
		
		public void buildScreen() {
			setBackground(Color.DARK_GRAY);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			if(difficulty == null) {
			currentDifficulty = Game.Difficulty.BEGINNER;
			}
			else {
				currentDifficulty = difficulty;
			}
			
			titleLabel = new JLabel("MAP SELECT");
			titleLabel.setForeground(Color.WHITE);
			titleLabel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
			titleLabel.setAlignmentX(CENTER_ALIGNMENT);
			
			difficultyLabel = new JLabel("");
			difficultyLabel.setForeground(Color.WHITE);
			difficultyLabel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
			difficultyLabel.setAlignmentX(CENTER_ALIGNMENT);
			
			displayMap(currentDifficulty);
			imagePanel = new ImagePanel();
			imagePanel.setOpaque(false);
			imagePanel.setPreferredSize(new Dimension(displayedMap.getWidth(), displayedMap.getHeight()));
			imagePanel.setVisible(true);

			bottomPane = new JPanel();
			bottomPane.setOpaque(false);
			leftButton = new JButton();
			//leftButton.setBackground(Color.DARK_GRAY);
			leftButton.addActionListener(new ButtonListener());
			bottomPane.add(leftButton);
			rightButton = new JButton();
			//rightButton.setBackground(Color.DARK_GRAY);
			rightButton.addActionListener(new ButtonListener());
			bottomPane.add(difficultyLabel);
			bottomPane.add(rightButton);
			
			backButton = new JButton("Back");
			backButton.addActionListener(new ButtonListener());
			
			add(Box.createVerticalGlue());
			add(titleLabel);
			add(imagePanel);
			add(bottomPane);
			add(backButton);
			add(Box.createRigidArea(new Dimension(100,180)));
			refresh();
		}
		
		private void refresh() {
			displayMap(currentDifficulty);
			switch(currentDifficulty) {
			case BEGINNER:
				difficultyLabel.setText("BEGINNER");
				break;
			case INTERMEDIATE:
				difficultyLabel.setText("INTERMEDIATE");
				break;
			case ADVANCED:
				difficultyLabel.setText("ADVANCED");
				break;
			}
		}
		
		private class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton)(e.getSource());
				
				if(source.equals(leftButton)) {
					handleLeftButton();
				}
				else if(source.equals(rightButton)) {
					handleRightButton();
				}
				else if (source.equals(backButton)) {
					handleBackButton();
				}
				
			}
			
			private void handleLeftButton() {
				switch(currentDifficulty) {
				case BEGINNER:
					currentDifficulty = Game.Difficulty.ADVANCED;
					break;
				case INTERMEDIATE:
					currentDifficulty = Game.Difficulty.BEGINNER;
					break;
				case ADVANCED:
					currentDifficulty = Game.Difficulty.INTERMEDIATE;
					break;
				}
				
				refresh();
				
			}
			
			private void handleRightButton() {
				switch(currentDifficulty) {
				case BEGINNER:
					currentDifficulty = Game.Difficulty.INTERMEDIATE;
					break;
				case INTERMEDIATE:
					currentDifficulty = Game.Difficulty.ADVANCED;
					break;
				case ADVANCED:
					currentDifficulty = Game.Difficulty.BEGINNER;
					break;
				}
				refresh();
			}
		
			private void handleBackButton() {
				difficulty = currentDifficulty;
				
				mainMenu = new MainMenu();
				GameGUI.this.remove(mapSelectScreen);
				GameGUI.this.add(mainMenu);
				pack();
				
			}
			
		}
		private class ImagePanel extends JPanel {
			
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(displayedMap != null) {
				g.drawImage(displayedMap, (getWidth() - displayedMap.getWidth()) / 2, (getHeight() - displayedMap.getHeight())/2, this);
				}
			}
		}
	}
	
	private class EndGameScreen extends JPanel {
		public final static int LOST = 0;
		public final static int WON = 1;
		public final static int HIGHSCORE = 2;
		
		int result = 0;
		Player player;
		JLabel message1;
		JLabel message2;
		JPanel textFieldPanel;
		JTextField textField;
		JButton backButton;
		
		public EndGameScreen(int result, Player player) {
			this.result = result;
			this.player = player;
			setPreferredSize(new Dimension(500,500));
			setBackground(Color.DARK_GRAY);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setVisible(true);
			
			switch(result) {
			case LOST:
				message1 = new JLabel();
				message1.setText("YOU LOST!");
				
				message1.setForeground(Color.WHITE);
				message1.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
				message1.setAlignmentX(CENTER_ALIGNMENT);
				
				backButton = new JButton ("Main Menu");
				backButton.setAlignmentX(CENTER_ALIGNMENT);
				
				add(Box.createVerticalGlue());
				add(message1);
				add(message2);
				add(backButton);
				add(Box.createVerticalGlue());
				
				break;
			case WON:
				message1 = new JLabel();
				message1.setText("YOU WON!");
				
				message1.setForeground(Color.WHITE);
				message1.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
				message1.setAlignmentX(CENTER_ALIGNMENT);
				
				backButton = new JButton ("Main Menu");
				backButton.setAlignmentX(CENTER_ALIGNMENT);
				
				add(Box.createVerticalGlue());
				add(message1);
				add(message2);
				add(backButton);
				add(Box.createVerticalGlue());
				break;
			case HIGHSCORE:
				message1 = new JLabel();
				message2 = new JLabel();
				message1.setText("YOU WON! HIGH SCORE!");
				message2.setText("Enter your name: ");
				
				message1.setForeground(Color.WHITE);
				message1.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
				message1.setAlignmentX(CENTER_ALIGNMENT);
				
				message2.setForeground(Color.WHITE);
				message2.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 20));
				message2.setAlignmentX(CENTER_ALIGNMENT);
			
				
				textField = new JTextField("NAME");
				
				textFieldPanel = new JPanel();
				textFieldPanel.setPreferredSize(new Dimension(100, 20));
				textFieldPanel.setOpaque(false);
				
				backButton = new JButton ("Main Menu");
				backButton.setAlignmentX(CENTER_ALIGNMENT);
				
				add(Box.createVerticalGlue());
				add(message1);
				add(message2);
				textFieldPanel.add(textField);
				add(textFieldPanel);
				add(backButton);
				add(Box.createVerticalGlue());
				
				break;
			}
			
			backButton.addActionListener(new ButtonListener());
		}
		
		private class ButtonListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean scoreSet = false;
				HighScore userScore = new HighScore();
				if(result == HIGHSCORE) {
					userScore.name = textField.getText();
					userScore.score = player.getScore();
					
					ArrayList<HighScore> highScoreList;
					highScoreList = loadHighScores();
					if(highScoreList == null) {
						highScoreList = new ArrayList<HighScore>();
					}
					for(int i = 0; i < highScoreList.size(); ++i) {
						if(player.getScore() > highScoreList.get(i).score) {
							highScoreList.add(i,userScore);
							scoreSet = true;
						}
					}
					if(!scoreSet) {
						highScoreList.add(userScore);
					}
					while(highScoreList.size() > 10) {
						highScoreList.remove(10);
					}
					
					GameGUI.saveHighScores(highScoreList);
					
					
				}
				mainMenu = new MainMenu();
				GameGUI.this.remove(endGameScreen);
				GameGUI.this.add(mainMenu);
				pack();
				
				
			}
			
		}
		
	}
	
	
	public static ArrayList<HighScore> loadHighScores() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		ArrayList<HighScore> highScores = new ArrayList<HighScore>();
		HighScore tempHighScore;
		int arraySize = 0;	
		String tempName;
		Integer tempScore;
		
		try
		{
			fileIn = new FileInputStream("HighScores.ser");
			objIn = new ObjectInputStream(fileIn);
			
			do {
				/*arraySize = objIn.readInt();
				for(int i = 0; i < arraySize; ++i) {
					tempHighScore = new HighScore();
					tempHighScore.name = (String) objIn.readObject();
					tempHighScore.score = (Integer) objIn.readObject();
					
					highScores = (ArrayList<HighScore>) objIn.readObject();
				}*/
				highScores = (ArrayList<HighScore>) objIn.readObject();
				
			} while(objIn.available() > 0);
			
			objIn.close();
			fileIn.close();
		}
		catch(IOException i)
		{
			//i.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return highScores;
	}
	
	public static void saveHighScores(ArrayList<HighScore> highScores) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;

		try 
		{			
			fileOut = new FileOutputStream( "HighScores.ser" );	
			objOut = new ObjectOutputStream(fileOut);
			
			/*objOut.writeInt(highScores.size());
			
			for(HighScore highScore : highScores){
				objOut.writeObject(highScore.name);
				objOut.writeObject(highScore.score);
			}*/
			objOut.writeObject(highScores);
			
			
			objOut.close();
			fileOut.close();
	     }	
		
		catch(IOException i)
	    {
			i.printStackTrace();
	    }		
	}	

	
}
