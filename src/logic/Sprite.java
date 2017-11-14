package logic;
import java.awt.Image;
import java.io.Serializable;

public abstract class Sprite implements Serializable{
	public static enum State{
		IDLE, MOVING_LEFT, MOVING_RIGHT, MOVING_DOWN, MOVING_UP
	};
	
	//current state and frame
	protected State state;
	protected int frame;
	
	//Constructor. init at idle
	public Sprite() {
		state = state.IDLE;
		frame = 0;
	}
	
	//getteres and setters
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	//animation
	public boolean isFinished(){
		return false;
	}
	
	//resets animation
	public void reset() {}
	
	//gets image for sprite
	public abstract Image getImage();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	
	
	
}
