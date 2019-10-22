package fr.fjdhj.PacMan.gameLogic;

public enum Direction{
    UP    ("up", 90), 
    DOWN  ("down", 270), 
    LEFT  ("left", 180), 
    RIGHT ("right", 0);
	
	private final String direction;
	private final int rotate;
	
	Direction(String direction, int rotate){
		this.direction = direction;
		this.rotate = rotate;
	}
	
	public String toString() {
		return direction;
	}
	
	public int getRotate() {
		return rotate;
	}
}

