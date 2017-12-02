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
	private JLabel playerInfo;
	private JPanel fighterInfoPanel;
	private JLabel fighterInfo;
	private JLabel enemyInfo;
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
		game = new Game(this);
		
		

		
		game.setMap(Map.loadMap("Level1.ser"));
		background = game.getMap().getBackground();	
		buildFighterIconMenu();
		buildInfoBoxes();
		
		
		addMouseListener(new GameMouseListener());
		setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
		gameStart = false;
		

	}
	
	private void buildInfoBoxes() {
		playerInfo = new JLabel();
		playerInfo.setOpaque(true);
		playerInfo.setBackground(Color.DARK_GRAY);
		playerInfo.setForeground(Color.WHITE);
		
		fighterInfoPanel = new JPanel();
		fighterInfoPanel.setLayout(new BorderLayout());
		fighterInfoPanel.setOpaque(true);
		fighterInfoPanel.setBackground(Color.DARK_GRAY);
		
		fighterInfo = new JLabel();
		fighterInfo.setForeground(Color.WHITE);
		
		sellButton = new JButton("SELL");
		upgradeButton = new JButton("UPGRADE");
		sellButton.addActionListener(new FighterButtonListener(ButtonMode.SELL));
		upgradeButton.addActionListener(new FighterButtonListener(ButtonMode.UPGRADE));
		
		fighterInfoPanel.add(fighterInfo);
		JPanel buttonPane = new JPanel();
		buttonPane.add(sellButton);
		buttonPane.add(upgradeButton);
		fighterInfoPanel.add(buttonPane, BorderLayout.SOUTH);
		
		enemyInfo = new JLabel();
		enemyInfo.setOpaque(true);
		enemyInfo.setBackground(Color.DARK_GRAY);
		enemyInfo.setForeground(Color.WHITE);
		enemyInfo.setText("<html><br><br><br><br><br><br><br><br></html>");
		enemyInfo.setSize(300,300);
		
		add(playerInfo);
		add(fighterInfoPanel);
		add(enemyInfo);
	}
	
	private void buildFighterIconMenu() {
		BufferedImage image;
		JMenu fighterIcon;
		
		
		
		
		try {
			//for each class
			fighterIcon = new JMenu();
			image = ImageIO.read(new File("images/fighter1.png"));
			fighterIcon.setIcon(new ImageIcon(image));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);
			
			fighterIcon = new JMenu();
			image = ImageIO.read(new File("images/fighter1.png"));
			fighterIcon.setIcon(new ImageIcon(image));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);
			
			fighterIcon = new JMenu();
			image = ImageIO.read(new File("images/fighter1.png"));
			fighterIcon.setIcon(new ImageIcon(image));
			fighterIcon.addMenuListener(new FighterMenuListener());
			fighterIcons.add(fighterIcon);
			
			for(JMenu tempIcon : fighterIcons) {
				//tempIcon.setPreferredSize(new Dimension(70,100));
				fighterIconMenu.add(tempIcon);
			}
			add(fighterIconMenu);
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
				selectedFighterClass = FighterClass.RANGE;
			}
			else if(source.equals(fighterIcons.get(1))) {	
				selectedFighterClass = FighterClass.STRENGTH;
			}
			else if(source.equals(fighterIcons.get(2))) {	
				selectedFighterClass = FighterClass.SPEED;
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
					&& e.getY() > slot.getPos().getY() &&e.getY() < slot.getPos().getY() + slot.getDimension().getHeight()){
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
				if(e.getX() > enemy.getPosition().getX() &&e.getX() < enemy.getPosition().getX() + enemy.getSprite().getWidth()
						&& e.getY() > enemy.getPosition().getY() &&e.getY() < enemy.getPosition().getY() + enemy.getSprite().getHeight()){
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
		int centerX = (int) enemy.getPosition().getX() - enemy.getSprite().getWidth()/2;
		int centerY = (int) enemy.getPosition().getY() - enemy.getSprite().getHeight()/2;
		g.drawImage(enemy.getSprite(), centerX, centerY, this);
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
		
		/*playerInfo.setText("<html>PLAYER:<br>Health: " + game.getPlayer().getHealth() + "/" + game.getPlayer().getMaxHealth()
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
					"<br>RNG: " + tempFighter.getRange() +  "<br>COOLDOWN: " + tempFighter.getCooldownTime() + "</html>");
		}*/
		
		
		
		
		
		
		
	}
	
	
}
