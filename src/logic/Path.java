package logic;

import java.awt.Point;
import java.io.Serializable;
import java.util.*;

//Path contains an array of parametric points (the "path"), and allows enemy to see where it should be given its "step" (how far along it is)
public class Path implements Serializable{
	private ArrayList<Point> path;
	
	public Path(){
		path = new ArrayList<Point>();
	}

	public ArrayList<Point> getPath() {
		return path;
	}

	public void setPath(ArrayList<Point> coordinates) {
		this.path = coordinates;
	}
	
	//step corresponds to index of path array
	public Point findPos(int step) {
		if(step < path.size() && step >= 0) {
			return path.get(step);
		}
		else {
			return null;
		}
	}

	
}

