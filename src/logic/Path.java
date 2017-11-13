package logic;

import java.awt.Point;
import java.util.*;

public class Path {
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
	
	public Point findPos(int step) {
		if(step < path.size() && step >= 0) {
			return path.get(step);
		}
		else {
			return null;
		}
	}

	
}

