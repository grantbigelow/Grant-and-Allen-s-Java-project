import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Cell {
	int     centerX;
	int     centerY;
	int     radius;
	
	Type    cellType;
	Size    cellSize;
	int     numOfTroops;
	int     maxTroops;
	double  regenRate; // troops per second
	
	int msDelay;
	
	public Cell(int centerX, int centerY, Type cellType, Size cellSize, int numOfTroops) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.cellType = cellType;
		this.cellSize = cellSize;
		this.numOfTroops = numOfTroops;
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
		DrawObjects.drawCenteredCircle(g, this.centerX, this.centerY, this.radius);
		g.setColor(Color.BLACK);
		DrawObjects.drawNumber(g, this.numOfTroops, this.centerX, this.centerY, 18);
	}
	
	public int getX() {
		return this.centerX;
	}
	public int getY() {
		return this.centerY;
	}
	
	public Type getType() {
		return this.cellType;
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
			this.numOfTroops /= 2;
		} else if (this.numOfTroops == 1) {
			t.add(new Troop(this, otherCell, this.numOfTroops / 2));
			this.numOfTroops = 0;
		}
		
	}
	
	public boolean isCoordInCell(int x, int y) {
		if (Math.abs(this.centerX - x) <= this.radius && Math.abs(this.centerY - y) <= this.radius) {
			System.out.println("in cell");
			return true;
			
		}
		System.out.println("out cell");
		return false;
		
	}
	
	
	
}
