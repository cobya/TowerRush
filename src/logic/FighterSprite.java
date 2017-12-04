package logic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FighterSprite {
	
	//current state and frame
	private Fighter fighter;
	private Point position;
	private Dimension dimension;
	private BufferedImage spriteSheet;
	private BufferedImage currImage;		
	private BufferedImage towerImage;
	
	public FighterSprite (Fighter fighter) {
		Fighter.FighterClass fClass = fighter.getfClass();
		this.fighter = fighter;
		
		
		try {
			spriteSheet = ImageIO.read(new File("images/spritesheet.png"));
			
			
			
			
			switch(fClass) {
				case STRENGTH:			
					towerImage = spriteSheet.getSubimage(0, 223, 53, 99);
					break;
				case SPEED:
					towerImage = spriteSheet.getSubimage(55, 227, 52, 95);
					break;
				case RANGE:
					towerImage = spriteSheet.getSubimage(111, 224, 65, 98);
					break;
				
			
			} 
			
			currImage = new BufferedImage(towerImage.getWidth(), towerImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = currImage.getGraphics();
			g.drawImage(towerImage, 0, 0, null);
			
			updatePositionDimension();
			
		
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void drawAim(Enemy enemy) {
		Graphics g = currImage.getGraphics();
		g.drawImage(towerImage, 0, 0, null);
		
		Graphics2D g2d = currImage.createGraphics();
		double dx =  (enemy.getPosition().getX() - (position.x + dimension.getWidth()/2));
		double dy =  (enemy.getPosition().getY() - (position.y + dimension.getHeight()/2));
		double dr = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		double r = 10;
		int x = (int) (r * dx / dr);
		int y = (int) (r * dy / dr);
		
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.black);
        g2d.draw(new Line2D.Float((int) (dimension.getWidth()/2), (int) (dimension.getHeight()/2), 
				(int) (dimension.getWidth()/2 + x), (int) (dimension.getHeight()/2 + y)));
	    g2d.dispose();
	}
	
	public void updatePositionDimension() {
		dimension = new Dimension(towerImage.getWidth(), towerImage.getHeight());
		
		if(fighter.getPosition() != null) {
			Point tempPoint = (Point) fighter.getPosition().clone();
			tempPoint.y -= dimension.getHeight();
			position = tempPoint;
		}
		else {
			position = new Point();
		}
	}

	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public BufferedImage getCurrImage() {
		return currImage;
	}

	public void setCurrImage(BufferedImage currImage) {
		this.currImage = currImage;
	}

	
}
