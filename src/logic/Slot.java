package logic;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

//Is the space where a fighter can be placed. NOTE: the position of the slot is the lower left corner of what is shown
public class Slot implements Serializable{
	private Point pos;
	private Fighter fighter;
	private Dimension dimension;
	
	public Slot() {
		pos = new Point();
		dimension = new Dimension(74, 47);		//arbitrary
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
	
	//fighter and slot are always mutually set
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
