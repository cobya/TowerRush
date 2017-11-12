package logic;

import java.util.*;

public class Path {
	private ArrayList<int[][]> path;
	
	public Path(){
		path = new ArrayList<int[][]>();
	}

	public ArrayList<int[][]> getPath() {
		return path;
	}

	public void setPath(ArrayList<int[][]> coordinates) {
		this.path = coordinates;
	}
	
	public int[][] findPos(int step) {
		if(step < path.size() && step >= 0) {
			return path.get(step);
		}
		else {
			return null;
		}
	}

	
}
