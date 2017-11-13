package logic;

import java.awt.Point;

public class Slot {
	private Point pos;
	private Fighter fighter;
	
	public Slot() {
		pos = new Point();
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
	
	public Fighter getFighter() {
		return fighter;
	}
	public void setFighter(Fighter fighter) {
		if(!this.equals(fighter.getSlot())) {
			fighter.setSlot(this);
		}
		this.fighter = fighter;
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
