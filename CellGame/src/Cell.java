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
	int enemyDelay = 5000;
	int attackDelay = 0;
	int attackInit = 3000;
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
				this.regenRate = 1.5;
				this.radius    = 36;
				break;
			case LARGE:
				this.maxTroops = 100;
				this.regenRate = 2;
				this.radius    = 52;
				break;
		}
	}
	
	
	public Cell copy() {
		return new Cell(centerX, centerY, cellType, cellSize, numOfTroops);
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
		if (this.numOfTroops > this.maxTroops) {
			this.numOfTroops = this.maxTroops;
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
		if(this.cellType == Type.NEUTRAL) {
			this.rCounter = 0;
		}
		else if(rCounter> 1000.0 * (1.0/regenRate) && this.numOfTroops<this.maxTroops) {
			this.numOfTroops+=1;
			this.rCounter=0;
		}
	}
	public void attack(int msDelay) {
		int leftover;
		if(this.cellType == Type.ENEMY) {
			if(enemyDelay == 0) {
				leftover = msDelay;
				while(leftover > 0)	{
					if (attackDelay == 0) {
						System.out.println("FIRST ATTACK");
						attackDelay = attackInit;
					}
					else if (attackDelay - leftover <=0) {
						System.out.println("ATTACK");
						leftover -= attackDelay;
						attackDelay = attackInit;
						
					}
					else {
						attackDelay -= leftover;
						leftover = 0;
					}
				}
			}
			else if(enemyDelay - msDelay <= 0) {
				leftover = msDelay;
				while(leftover > 0)	{
					if (attackDelay == 0) {
						System.out.println("DoubleU ATTACK");
						attackDelay = attackInit;
					}
					else if (attackDelay - leftover <=0) {
						System.out.println("ATTACK");
						leftover -= attackDelay;
						attackDelay = attackInit;
						
					}
					else {
						attackDelay -= leftover;
						leftover = 0;
					}
				}
			}
			else
				enemyDelay-=msDelay;
		}
	}
	public boolean isCoordInCell(int x, int y) {
		if (Math.pow((x-this.centerX),2) + Math.pow((y-this.centerY),2) <= Math.pow(this.radius, 2)) {
			return true;
		}
		return false;
	}
	
	
	
}
