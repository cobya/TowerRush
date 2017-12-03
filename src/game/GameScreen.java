package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import logic.Enemy;
import logic.Fighter;
import logic.Fighter.FighterClass;
import logic.Map;
import logic.Slot;


public class GameScreen extends JPanel{
	private enum ButtonMode {SELL, UPGRADE};
	public enum PlayerAction {BUY, SELL, UPGRADE};
	private PlayerAction unprocessedPlayerAction = null;	//used to tell Game if player did anything
	private Game game;
	private Thread gameThread;
	private BufferedImage background;
	private JMenuBar fighterIconMenu;
	private ArrayList<JMenu> fighterIcons;
	private Fighter.FighterClass selectedFighterClass;
	private Fighter selectedFighter;
	private Slot selectedSlot;
	private Enemy selectedEnemy;
	private JPanel overlay;
	private JLabel playerInfo;
	private JPanel fighterInfoPanel;
	private JPanel playerInfoPanel;
	private JLabel fighterInfo;
	private JLabel enemyInfo;
	private JLabel waveInfo;
	private JButton sellButton;
	private JButton upgradeButton;
	boolean gameStart;
	
	private int paintCount = 0;
	private int maxPaintCount = 100;
	private double avgTime = 0;
	
	public GameScreen() throws IOException {
		gameStart = false;
		fighterIconMenu = new JMenuBar();
		fighterIcons = new ArrayList<JMenu>();
		initGameScreen();
	}

	
	public Fighter getSelectedFighter() {
		return selectedFighter;
	}
	
	public void setSelectedFighter(Fighter selectedFighter) {
		this.selectedFighter = selectedFighter;
	}
	
	public Fighter.FighterClass getSelectedFighterClass() {
		return selectedFighterClass;
	}
	
	public void setSelectedFighterClass(Fighter.FighterClass fighterClass) {
		this.selectedFighterClass = fighterClass;
	}
	
	public PlayerAction getUnprocessedPlayerAction() {
		return unprocessedPlayerAction;
	}
	
	public void setUnprocessedPlayerAction(PlayerAction playerAction) {
		this.unprocessedPlayerAction = playerAction;
	}
	
	
	public Slot getSelectedSlot() {
		return selectedSlot;
	}

	public void setSelectedSlot(Slot selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	
	private void initGameScreen() throws IOException  {
		MapBuilder beginnerMapBuilder = new MapBuilder();
		game = new Game(this);
		
		game.setMap(beginnerMapBuilder.buildBeginnerMap());
		
		background = game.getMap().getBackground();	
		
		
		addMouseListener(new GameMouseListener());
		setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
		buildOverlay();
		gameStart = false;
		setVisible(true);
	}
	
	public void buildOverlay() {
		JPanel topPane = new JPanel();
		JPanel bottomPane = new JPanel();
		//overlay = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		buildFighterIconMenu();
		buildInfoBoxes();
		
		topPane.setOpaque(false);
		topPane.add(fighterIconMenu);
		topPane.add(fighterInfoPanel);
		topPane.add(playerInfoPanel);
		
		//topPane.add(playerInfoPanel);
		
		bottomPane.setOpaque(false);
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.Y_AXIS));
		bottomPane.add(Box.createVerticalGlue());
		bottomPane.add(waveInfo);
		add(topPane);
		add(Box.createVerticalGlue());
		add(bottomPane);
		
		
		
	}
	
	private void buildInfoBoxes() {
		playerInfoPanel = new JPanel();
		playerInfoPanel.setPreferredSize(new Dimension(200, 125));
		playerInfoPanel.setLayout(new BorderLayout());
		playerInfoPanel.setOpaque(true);
		playerInfoPanel.setBackground(Color.DARK_GRAY);
		playerInfo = new JLabel();
		playerInfo.setOpaque(true);
		playerInfo.setBackground(Color.DARK_GRAY);
		playerInfo.setForeground(Color.WHITE);
		playerInfoPanel. add(playerInfo, BorderLayout.NORTH);
		
		fighterInfoPanel = new JPanel();
		fighterInfoPanel.setLayout(new BorderLayout());
		fighterInfoPanel.setOpaque(true);
		fighterInfoPanel.setBackground(Color.DARK_GRAY);
		
		fighterInfo = new JLabel();
		fighterInfo.setForeground(Color.WHITE);
		
		sellButton = new JButton("SELL");
		upgradeButton = new JButton("UPGRADE");
		
		sellButton.setOpaque(true);
		sellButton.setForeground(Color.WHITE);
		sellButton.setBackground(Color.BLACK);
		sellButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sellButton.setBorder(null);
		sellButton.setFocusPainted(false);
		sellButton.setContentAreaFilled(true);
		
		upgradeButton.setOpaque(true);
		upgradeButton.setForeground(Color.WHITE);
		upgradeButton.setBackground(Color.BLACK);
		upgradeButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		upgradeButton.setBorder(null);
		upgradeButton.setFocusPainted(false);
		upgradeButton.setContentAreaFilled(true);
		
		sellButton.addActionListener(new FighterButtonListener(ButtonMode.SELL));
		upgradeButton.addActionListener(new FighterButtonListener(ButtonMode.UPGRADE));
		
		fighterInfoPanel.add(fighterInfo, BorderLayout.NORTH);
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		
		buttonPane.add(sellButton);
		buttonPane.add(upgradeButton);
		fighterInfoPanel.add(buttonPane, BorderLayout.SOUTH);
		fighterInfoPanel.setPreferredSize(new Dimension(200,125));
		
		enemyInfo = new JLabel();
		enemyInfo.setOpaque(true);
		enemyInfo.setBackground(Color.DARK_GRAY);
		enemyInfo.setForeground(Color.WHITE);
		enemyInfo.setText("<html><br><br><br><br><br><br><br><br></html>");
		enemyInfo.setSize(300,300);
		
		waveInfo = new JLabel();
		waveInfo.setOpaque(false);
		waveInfo.setBackground(Color.DARK_GRAY);
		waveInfo.setForeground(Color.WHITE);
		//waveInfo.setPreferredSize(new Dimension(20,10));
		waveInfo.setText("Wave 1 / 10");
		
		
		//add(playerInfo);
		//add(fighterInfoPanel);
		//add(enemyInfo);
	}
	
	private void buildFighterIconMenu() {
		BufferedImage spriteSheet;
		BufferedImage iconImage;
		JMenu fighterIcon;
		
		try { 
			spriteSheet = ImageIO.read(new File("images/spritesheet.png"));
		
		
			//for each class
			fighterIcon = new JMenu();
			iconImage = spriteSheet.getSubimage(186, 274, 27, 50);
			fighterIcon.setIcon(new ImageIcon(iconImage));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);
			
			fighterIcon = new JMenu();
			iconImage = spriteSheet.getSubimage(214, 274, 26, 48);
			fighterIcon.setIcon(new ImageIcon(iconImage));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);
			
			fighterIcon = new JMenu();
			iconImage = spriteSheet.getSubimage(241, 271, 33, 51);
			fighterIcon.setIcon(new ImageIcon(iconImage));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);

			for(JMenu tempIcon : fighterIcons) {
				fighterIconMenu.add(tempIcon);
			}
			//add(fighterIconMenu);
		
		
		} catch (IOException e) {

		}
		
	}
	
	private class FighterButtonListener implements ActionListener {
		private ButtonMode mode;	//SELL or UPGRADE
		
		
		public FighterButtonListener(ButtonMode mode) {
			this.mode = mode;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(mode) {
			case SELL:
				unprocessedPlayerAction = PlayerAction.SELL;
				//selectedFighterClass already set
				break;
				
			case UPGRADE:
				unprocessedPlayerAction = PlayerAction.UPGRADE;
				//selectedFighterClass already set
				break;
			}
			
		}
		
	}
	
	private class FighterMenuListener implements MenuListener {

		@Override
		public void menuCanceled(MenuEvent arg0) {
			
		}

		@Override
		public void menuDeselected(MenuEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void menuSelected(MenuEvent e) {
			JMenu source = (JMenu)(e.getSource());
			
			if(source.equals(fighterIcons.get(0))) {	
				selectedFighterClass = FighterClass.STRENGTH;
			}
			else if(source.equals(fighterIcons.get(1))) {	
				selectedFighterClass = FighterClass.SPEED;
			}
			else if(source.equals(fighterIcons.get(2))) {	
				selectedFighterClass = FighterClass.RANGE;
			}
			selectedFighter = null;
			
		}
	}
	
	private class GameMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			handleMouseClicked(e);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public void handleMouseClicked(MouseEvent e) {
			for(Slot slot : game.getMap().getSlots()) {
				if(e.getX() > slot.getPos().getX() &&e.getX() < slot.getPos().getX() + slot.getDimension().getWidth()
					&& e.getY() > slot.getPos().getY() - slot.getDimension().getHeight() &&e.getY() < slot.getPos().getY()){
					if(slot.isAvailable() && selectedFighterClass != null) {
						//need to check player money
						//need to add this to end of game tick; not asynchronous
						selectedSlot = slot;
						unprocessedPlayerAction = PlayerAction.BUY;
						return;
					}
					else {
						selectedFighterClass = null;
						selectedFighter = slot.getFighter();
					}
				}
			}
			for(Enemy enemy : game.getEnemies()) {
				if(e.getX() > enemy.getPosition().getX() &&e.getX() < enemy.getSprite().getPosition().getX()
						&& e.getY() > enemy.getPosition().getY() &&e.getY() < enemy.getSprite().getPosition().getY()){
				selectedEnemy = enemy;		
				}
			}
		}
		
		
	}
	
	public void runGame() {
		gameStart = true;
		
		gameThread = new Thread(game);
		gameThread.start();
	}
	
	
	private void drawEnemy(Enemy enemy, Graphics g) {
		//int centerX = (int) enemy.getPosition().getX() - enemy.getSprite().getWidth()/2;
		//int centerY = (int) enemy.getPosition().getY() - enemy.getSprite().getHeight()/2;
		//g.drawImage(enemy.getSprite().getCurrImage(), centerX, centerY, this);
	}
	
	private void drawFighter(Fighter fighter, Graphics g) {
		int x = (int) fighter.getPosition().getX(); //render image such that bottom right corner matches with slot
		int y = (int) (fighter.getPosition().getY() - fighter.getSprite().getHeight(this) + fighter.getSlot().getDimension().getHeight());
		g.drawImage(fighter.getSprite(), x, y, this);
	}
	
	//paint background
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*double before = System.nanoTime();
		g.drawImage(background, 0, 0, this);
		double after = System.nanoTime();
		if((after-before)/1000000 > 1) {
			//System.out.println((after-before) / 1000000 );
		}
		if(game.getEnemies() != null) {
			for(Enemy enemy : game.getEnemies()) {
				drawEnemy(enemy, g);
			}
		}
		if(game.getFighters() != null) {
			for(Fighter fighter : game.getFighters()) {
				drawFighter(fighter, g);
			}
		}*/
		
		
		double then = System.nanoTime();
		//g.drawImage(logic.image, 0, 0, this);
		g.drawImage(game.getScreen(), 0, 0, this);
		
		avgTime += (System.nanoTime() - then) / 1000.0;
		paintCount++;
		if(paintCount >= maxPaintCount) {
			System.out.println(avgTime / (maxPaintCount * 1000.0));
			avgTime = 0;
			paintCount = 0;
		}
		
		playerInfo.setText("<html>PLAYER:<br>Health: " + game.getPlayer().getHealth() + "/" + game.getPlayer().getMaxHealth()
							+ "<br>Money: " + game.getPlayer().getMoney() + "</html>");
		if(selectedEnemy != null) {
			enemyInfo.setText("<html>ENEMY:<br>Class: " + selectedEnemy.geteClass() + "<br>Health: " + selectedEnemy.getHealth()
								+ "/" + selectedEnemy.getMaxHealth() + "<br>DMG: " + selectedEnemy.getAttackDamage() + "<br>SPD: "
								+ selectedEnemy.getTravelSpeed() + "</html>");
		}
		if(selectedFighter != null) {
			fighterInfo.setText("<html>FIGHTER:<br>Class: " + selectedFighter.getfClass() + "<br>DMG: " + selectedFighter.getAttackDamage() + 
					"<br>RNG: " + selectedFighter.getRange() +  "<br>COOLDOWN: " + selectedFighter.getCooldownTime() + "</html>");
		}
		if(selectedFighterClass != null) {
			Fighter tempFighter = new Fighter(selectedFighterClass);
			fighterInfo.setText("<html>FIGHTER:<br>Class: " + tempFighter.getfClass() + "<br>DMG: " + tempFighter.getAttackDamage() + 
					"<br>RNG: " + tempFighter.getRange() +  "<br>COOLDOWN: " + tempFighter.getCooldownTime() + "<br>COST: " + 
					tempFighter.getCost() + "</html>");
		}
		waveInfo.setText("<html>WAVE: " + game.getCurrWave().getWaveNumber() + "/" + game.getWaves().size() + "</html>");
		
		
		
		
		
		
	}
	
	
}
