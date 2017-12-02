package logic;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

public class Slot implements Serializable{
	private Point pos;
	private Fighter fighter;
	private Dimension dimension;
	
	public Slot() {
		pos = new Point();
		dimension = new Dimension(74, 47);
	}
	
	public Slot(Point pos) {
		this.pos = pos;
	}
	
	public Point getPos() {
		return pos;
	}
	public void setPos(Point pos) {
		this.pos = pos;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}
	
	public Fighter getFighter() {
		return fighter;
	}
	public void setFighter(Fighter fighter) {
		this.fighter = fighter;
		if(!this.equals(fighter.getSlot())) {
			fighter.setSlot(this);
		}
	}
	
	public void removeFighter() {
		this.fighter = null;
	}
	
	public boolean isAvailable() {
		if(fighter == null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
