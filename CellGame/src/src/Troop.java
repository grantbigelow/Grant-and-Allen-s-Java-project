import java.awt.Color;
import java.awt.Graphics;

public class Troop {
	double currentX;
	double currentY;
	int endX;
	int endY;
	
	int numOfTroops;
	
	double distPerMove;
	
	Cell targetCell;
	
	Cell.Type troopType;
	
	boolean finishedMoving;
	
	double pxPerSec;
	
	public Troop(Cell cell1, Cell cell2, int numOfTroops) {
		this.currentX    = cell1.getX();
		this.currentY    = cell1.getY();
		this.endX        = cell2.getX();
		this.endY        = cell2.getY();
		
		this.numOfTroops = numOfTroops;
		this.troopType   = cell1.getType();
		this.targetCell  = cell2;
		
		this.pxPerSec    = 250.0; // TODO: Change
		
		this.finishedMoving = false;
		
		
	}
	
	public void move(int msDelta) {
		double distToMove  = (msDelta / 1000.0) * this.pxPerSec;
		double[] newPoint;
		if (! finishedMoving) {
			if (getDistance(currentX, currentY, endX, endY) < distToMove) {
				targetCell.addTroops(numOfTroops, troopType);
				finishedMoving = true;
			}else {
				newPoint = getPointAlongLine(currentX, currentY, endX, endY, distToMove);
				currentX = newPoint[0];
				currentY = newPoint[1];
			}
		}
	}
	
	public void draw(Graphics g) {
		if (!this.finishedMoving) {
			g.setColor(Color.BLACK);
			DrawObjects.drawNumber(g, this.numOfTroops,(int) this.currentX, (int)this.currentY, 12);
		}
	}
	
	private double[] getPointAlongLine(double x1, double y1, double x2, double y2, double distanceFromP1){
		//Shift Points so point 1 is at zero
		double yDlta = y2 - y1;
		double xDlta = x2 - x1;
		
		double xPrime = (distanceFromP1 * xDlta)/ getDistance(x1,y1, x2,y2);
		double yPrime = (distanceFromP1 * yDlta)/ getDistance(x1,y1, x2,y2);
		double[] ret = new double[] {x1 + xPrime, y1 + yPrime};
		return ret;
	}
	
	//YUP
	private double getDistance(double x1,double y1,double x2,double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	//YUP
	private double getSlope(double x1,double y1,double x2,double y2) {
		return (y2 - y1) / (x2 - x1);
	}


}
