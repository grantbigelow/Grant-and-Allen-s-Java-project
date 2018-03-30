import java.awt.Graphics;

public class Troop {
	int currentX;
	int currentY;
	int endX;
	int endY;
	
	int numOfTroops;
	
	double distPerMove;
	
	Cell targetCell;
	
	Cell.Type troopType;
	
	boolean finishedMoving;
	
	public Troop(Cell cell1, Cell cell2, int numOfTroops, int msDelay) {
		this.currentX    = cell1.getX();
		this.currentY    = cell1.getY();
		this.endX        = cell2.getX();
		this.endY        = cell2.getY();
		
		this.numOfTroops = numOfTroops;
		this.troopType   = cell1.getType();
		
		this.distPerMove = 1000 * 15.0 / msDelay; // (pixels / second) / (move call frequency)
		
		this.finishedMoving = false;
		
		
	}
	
	public void move() {
		int[] delta;
		if (!this.finishedMoving) {
			if (getDistance(this.currentX, this.currentY, this.endX, this.endY) < this.distPerMove) {
				targetCell.addTroops(this.numOfTroops, this.troopType);
				this.finishedMoving = true;
			} else {
				delta = getXYDelta(getDistance(this.currentX, this.currentY, this.endX, this.endY),
						           getSlope   (this.currentX, this.currentY, this.endX, this.endY));
				this.currentX += delta[0];
				this.currentY += delta[1];
				
			}
		}
		
	}
	
	public void draw(Graphics g) {
		if (!this.finishedMoving) {
			DrawObjects.drawNumber(g, this.numOfTroops, this.currentX, this.currentY, 12);
		}
	}
	
	private int[] getXYDelta(double distance, double slope){
		
		double xDelta = Math.sqrt( Math.pow(distance, 2) / (Math.pow(slope, 2) + 1) );
		double yDelta = xDelta * slope;
		int[] ret = {(int) Math.round(xDelta),(int) Math.round(yDelta)};
		return ret;
	}
	
	private double getDistance(double x1,double y1,double x2,double y2) {
		return Math.sqrt(Math.pow(x1 + x2, 2) + Math.pow(y1 + y2, 2));
	}
	
	private double getSlope(double x1,double y1,double x2,double y2) {
		return (y2 - y1) / (x2 - x1);
	}
	
	private double getDelta(double a, double b) {
		return b - a;
	}

}
