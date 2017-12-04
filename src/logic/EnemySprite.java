package logic;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.text.Position;

//contains multiple images for each state of the enemy's animation
public class EnemySprite implements Serializable{
	public static enum Direction{
		FRONT, BACK, RIGHT, LEFT
	};
	
	//current state and frame
	private Enemy enemy;
	private Direction direction;
	private Point position;
	private Dimension dimension;
	
	
	BufferedImage spriteSheet;	//single spritesheet for all sprites
	BufferedImage currImage;//is a copy of the reference image so it can be drawn on
	BufferedImage currImageRef;	//refers to which image is being used; must not be drawn on
	BufferedImage front1;
	BufferedImage front2;
	BufferedImage back1;
	BufferedImage back2;
	BufferedImage right1;
	BufferedImage right2;
	BufferedImage right3;
	BufferedImage right4;
	BufferedImage left1;
	BufferedImage left2;
	BufferedImage left3;
	BufferedImage left4;
	
	//Constructor. init at idle
	public EnemySprite(Enemy enemy) {
		Enemy.EnemyClass eClass = enemy.geteClass();
		this.enemy = enemy;
		
		
		//crop sprites depending on which class the enemy is
		try {
			spriteSheet = ImageIO.read(new File("images/spritesheet.png"));
			
			
			
			
			switch(eClass) {
				case STRENGTH:			//crops the demon looking monster	
					front1 = spriteSheet.getSubimage(0, 149, 23, 55);
					front2 = spriteSheet.getSubimage(25, 149, 23, 55);
					back1 = spriteSheet.getSubimage(50, 149, 23, 55);
					back2 = spriteSheet.getSubimage(75, 149, 23, 55);
					right1 = spriteSheet.getSubimage(100, 149, 13, 55);
					right2 = spriteSheet.getSubimage(115, 149, 10, 55);
					right3 = spriteSheet.getSubimage(127, 149, 13, 55);
					right4 = spriteSheet.getSubimage(115, 149, 10, 55);
					left1 = spriteSheet.getSubimage(142, 149, 13, 55);
					left2 = spriteSheet.getSubimage(157, 149, 10, 55);
					left3 = spriteSheet.getSubimage(169, 149, 13, 55);
					left4 = spriteSheet.getSubimage(157, 149, 10, 55);
					break;
				case SPEED:				//crops the goblin looking monster
					front1 = spriteSheet.getSubimage(0, 80, 25, 58);
					front2 = spriteSheet.getSubimage(27, 80, 25, 58);
					back1 = spriteSheet.getSubimage(54, 80, 25, 58);
					back2 = spriteSheet.getSubimage(81, 80, 25, 58);
					right1 = spriteSheet.getSubimage(108, 80, 15, 58);
					right2 = spriteSheet.getSubimage(125, 80, 13, 58);
					right3 = spriteSheet.getSubimage(140, 80, 15, 58);
					right4 = spriteSheet.getSubimage(125, 80, 13, 58);
					left1 = spriteSheet.getSubimage(157, 80, 15, 58);
					left2 = spriteSheet.getSubimage(174, 80, 13, 58);
					left3 = spriteSheet.getSubimage(189, 80, 15, 58);
					left4 = spriteSheet.getSubimage(174, 80, 13, 58);
					break;
				case HEALTH:			//crops the golem looking monster
					front1 = spriteSheet.getSubimage(0, 0, 37, 70);
					front2 = spriteSheet.getSubimage(40, 0, 37, 70);
					back1 = spriteSheet.getSubimage(80, 0, 40, 70);
					back2 = spriteSheet.getSubimage(120, 0, 39, 70);
					right1 = spriteSheet.getSubimage(159, 0, 23, 71);
					right2 = spriteSheet.getSubimage(183, 0, 20, 71);
					right3 = spriteSheet.getSubimage(205, 0, 22, 71);
					right4 = spriteSheet.getSubimage(183, 0, 20, 71);
					left1 = spriteSheet.getSubimage(229, 0, 22, 71);
					left2 = spriteSheet.getSubimage(253, 0, 20, 71);
					left3 = spriteSheet.getSubimage(275, 0, 22, 71);
					left4 = spriteSheet.getSubimage(253, 0, 20, 71);
					break;
				
			
			} 
			
			currImageRef = front1;
			
			//draw reference image to current image
			currImage = new BufferedImage(currImageRef.getWidth(), currImageRef.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = currImage.getGraphics();
			g.drawImage(currImageRef, 0, 0, null);
			
			//start with front facing image
			direction = Direction.FRONT;
			updatePositionDimension();
		
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//getteres and setters
	public Point getPosition() {
		return position;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public BufferedImage getCurrImage() {
		return currImage;
	}
	
	public void setCurrImage(BufferedImage currImage) {
		this.currImage = currImage;
	}
	
	
	//sets direction and if the direction is new, changes the reference image, starting a new cycle
	public void setDirection(Direction direction) {
		if(this.direction == direction) {
			return;
		}
		
		switch(direction) {
		case FRONT:
			currImageRef = front1;
			break;
		case BACK:
			currImageRef = back1;
			break;
		case RIGHT:
			currImageRef = right1;
			break;
		case LEFT:
			currImageRef = left1;
			break;
		}
		
		currImage = new BufferedImage(currImageRef.getWidth(), currImageRef.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = currImage.getGraphics();
		g.drawImage(currImageRef, 0, 0, null);
		updatePositionDimension();
		
		this.direction = direction;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	//continues sprite through animation cycle for a given direction
	public void toggleStep() {
		switch(direction) {
		case FRONT:
			if(currImageRef.equals(front1)) {
				currImageRef = front2;
			}
			else if(currImageRef.equals(front2)) {
				currImageRef = front1;
			}
			break;
		case BACK:
			if(currImageRef.equals(back1)) {
				currImageRef = back2;
			}
			else if(currImageRef.equals(back2)) {
				currImageRef = back1;
			}
			break;
		case RIGHT:
			if(currImageRef.equals(right1)) {
				currImageRef = right2;
			}
			else if(currImageRef.equals(right2)) {
				currImageRef = right3;
			}
			else if(currImageRef.equals(right3)) {
				currImageRef = right4;
			}
			else if(currImageRef.equals(right4)) {
				currImageRef = right1;
			}
			break;
		case LEFT:
			if(currImageRef.equals(left1)) {
				currImageRef = left2;
			}
			else if(currImageRef.equals(left2)) {
				currImageRef = left3;
			}
			else if(currImageRef.equals(left3)) {
				currImageRef = left4;
			}
			else if(currImageRef.equals(left4)) {
				currImageRef = left1;
			}
			break;
		}
		
		currImage = new BufferedImage(currImageRef.getWidth(), currImageRef.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = currImage.getGraphics();
		g.drawImage(currImageRef, 0, 0, null);
		
		
		updatePositionDimension();
	}
	
	//updates sprite image position and dimension. The image is positioned so that the enemy position is in the middle of it
	public void updatePositionDimension() {
		
		//update dimension
		dimension = new Dimension(currImage.getWidth(), currImage.getHeight());
		
		Point tempPos = (Point) enemy.getPosition().clone();
		tempPos.x -= (int) dimension.width/2;
		tempPos.y -= (int) dimension.height/2;
		position = tempPos;
	}
	
	
	
	
	
}
