package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class GameGUI extends JFrame {
	private GameScreen gameScreen;
	private MainMenu mainMenu;
	private HighScoreScreen highScoreScreen;
	private Game game;
	
	public GameGUI() throws IOException {
		super();
		initGUI();
	}
	
	private void initGUI() throws IOException {
		//game = new Game();
		//gameScreen = new GameScreen();
		mainMenu = new MainMenu();
		
		add(mainMenu);
		setTitle("TowerRush");
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void startGame() {
		try {
		
		gameScreen = new GameScreen();
		add(gameScreen);
		game = new Game();
		gameScreen.runGame();
		remove(mainMenu);
		pack();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private class MainMenu extends JPanel {
		private JLabel lblTowerRush;
		private JButton btnStartGame;
		private JButton btnMapSelect;
		private JButton btnHighScores;
		private GridBagConstraints gbc;
		
		
		public MainMenu() {
			setPreferredSize(new Dimension(500,500));
			
			buildScreen();
			
			setVisible(true);
		}
		
		public void buildScreen() {
			setBackground(Color.DARK_GRAY);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			Component verticalStrut_3 = Box.createVerticalStrut(30);
			add(verticalStrut_3);
			
			lblTowerRush = new JLabel("Tower Rush");
			lblTowerRush.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 48));
			lblTowerRush.setForeground(Color.WHITE);
			lblTowerRush.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(lblTowerRush);
			
			Component verticalStrut_2 = Box.createVerticalStrut(80);
			add(verticalStrut_2);
			
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
			
			Component verticalStrut_1 = Box.createVerticalStrut(20);
			add(verticalStrut_1);
			
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
			
			Component verticalStrut = Box.createVerticalStrut(20);
			add(verticalStrut);
			
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

		private class ButtonListener implements ActionListener {
			public void actionPerformed(ActionEvent e) //this is the method MenuListener must implement, as it comes from the ActionListener interface.
			{
				JButton source = (JButton)(e.getSource());
				
				if(source.equals(btnStartGame)) {
					handleStartGame();
				}
				else if(source.equals(btnMapSelect)) {
					
				}
				else if(source.equals(btnHighScores)) {
					handleHighScoreScreen();
				}
			}
			
			public void handleStartGame() {
				JLabel loading = new JLabel("Loading ...");

				add(Box.createVerticalStrut(40));
				
				loading.setOpaque(false);
				loading.setForeground(Color.WHITE);
				loading.setFont(new Font("Tahoma", Font.BOLD, 20));
				loading.setAlignmentY(0.0f);
				loading.setAlignmentX(0.5f);

				add(loading);
				pack();
				
				setVisible(true);
				
				startGame();
			}
			
			public void handleHighScoreScreen() {
				highScoreScreen = new HighScoreScreen();
				GameGUI.this.remove(mainMenu);
				GameGUI.this.add(highScoreScreen);
				pack();
			}

		}
	}

	private class HighScoreScreen extends JPanel {
		ArrayList<String> highScores;			//Format: "NAME   ...   1,024"
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
						tempLabel.setText(i+1 + ". " + highScores.get(i));
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
	
	public static ArrayList<String> loadHighScores() {
		FileInputStream fileIn = null;
		ObjectInputStream objIn = null;
		ArrayList<String> highScores = new ArrayList<String>();
			
		try
		{
			fileIn = new FileInputStream("HighScores.ser");
			objIn = new ObjectInputStream(fileIn);
			
			do {
				highScores = (ArrayList<String>) objIn.readObject();
				
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
	
	public static void saveHighScores(ArrayList<String> highScores) {
		FileOutputStream fileOut = null;
		ObjectOutputStream objOut= null;

		try 
		{			
			fileOut = new FileOutputStream( "HighScores.ser" );		//the Employee object makes its way to serial data in the file Employee.ser
			objOut = new ObjectOutputStream(fileOut);
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
