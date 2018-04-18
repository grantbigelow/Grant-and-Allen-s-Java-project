import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Cell {
	int     centerX;
	int     centerY;
	int     radius;
	
	Type    cellType;
	Size    cellSize;
	int     numOfTroops;
	int     maxTroops;
	double  regenRate; // troops per second
	int rCounter;
	int msDelay;
	
	boolean isHighlighted;
	
	public Cell(int centerX, int centerY, Type cellType, Size cellSize, int numOfTroops) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.cellType = cellType;
		this.cellSize = cellSize;
		this.numOfTroops = numOfTroops;
		this.isHighlighted = false;
		switch(cellSize){
			case SMALL:
				this.maxTroops = 15;
				this.regenRate = 1;
				this.radius    = 21;
				break;
			case MEDIUM:
				this.maxTroops = 40;
				this.regenRate = 1.2;
				this.radius    = 36;
				break;
			case LARGE:
				this.maxTroops = 100;
				this.regenRate = 1.5;
				this.radius    = 52;
				break;
		}
	}
	
	public enum Type{
		PLAYER, ENEMY, NEUTRAL
	}
	
	public enum Size{
		SMALL, MEDIUM, LARGE
	}
	
	public void draw(Graphics g) {
		switch(this.cellType) {
			case PLAYER:
				g.setColor(Color.BLUE);
				break;
			
			case ENEMY:
				g.setColor(Color.RED);
				break;
			case NEUTRAL:
				g.setColor(Color.GRAY);
				break;
		}		
		DrawObjects.drawCenteredCircle(g, centerX, centerY, radius);
		g.setColor(Color.WHITE);
		DrawObjects.drawNumber(g, numOfTroops, centerX, centerY, 18);
		if (isHighlighted) {
			g.setColor(Color.BLACK);
			DrawObjects.drawCenteredCircle(g, centerX, centerY, radius + 5, false);
		}
	}
	
	public int getX() {
		return centerX;
	}
	public int getY() {
		return centerY;
	}
	
	public Type getType() {
		return cellType;
	}
	
	public int getNumOfTroops() {
		return numOfTroops;
	}
	
	public void highlight() {
		isHighlighted = true;
	}
	public void unhighlight() {
		isHighlighted = false;
	}
	
	public void addTroops(int numOfTroops, Type troopType) {
		if (this.cellType == troopType) {
			this.numOfTroops += numOfTroops;
		}else if (numOfTroops <= this.numOfTroops) {
			this.numOfTroops -= numOfTroops;
		}else {
			this.numOfTroops = numOfTroops - this.numOfTroops;
			this.cellType = troopType;
		}
	}
	
	public void sendTroops(ArrayList<Troop> t, Cell otherCell) {
		if (this.numOfTroops > 1) {
			t.add(new Troop(this, otherCell, this.numOfTroops / 2));
			this.numOfTroops -= this.numOfTroops /2;
		} else if (this.numOfTroops == 1) {
			t.add(new Troop(this, otherCell, this.numOfTroops / 2));
			this.numOfTroops = 0;
		}
		
	}
	public void regen(int msDelay) {
		this.rCounter+=msDelay;
		if(rCounter>regenRate*1000 && this.numOfTroops<this.maxTroops) {
			this.numOfTroops+=1;
			this.rCounter=0;
		}
	}
	public void sendEnemy(ArrayList<Troop> t, Cell otherCell, int msDelay) {
		this.rCounter+=msDelay;
		if (this.numOfTroops > 1 && rCounter>2000) {
			t.add(new Troop(this, otherCell, this.numOfTroops / 2));
			this.numOfTroops -= this.numOfTroops /2;
		}
	}
	public boolean isCoordInCell(int x, int y) {
		if (Math.abs(this.centerX - x) <= this.radius || Math.abs(this.centerY - y) <= this.radius) {
			return true;
		}
		return false;
	}
	
	
	
}
