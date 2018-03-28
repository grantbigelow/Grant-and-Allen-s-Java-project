


public class Cell {
	Type    cellType;
	Size    cellSize;
	int     troops;
	int     maxTroops;
	double  regenRate; // troops per second
	
	public void Cell(Type cellType, Size cellSize, int troopsInCell) {
		this.cellType = cellType;
		this.cellSize = cellSize;
		this.troops = troopsInCell;
		switch(cellSize){
			case SMALL:
				this.maxTroops = 15;
				this.regenRate = 1;
				break;
			case MEDIUM:
				this.maxTroops = 30;
				this.regenRate = 1.2;
			case LARGE:
				this.maxTroops = 50;
				this.regenRate = 1.5;
		}
	}
	
	public enum Type{
		PLAYER, ENEMY, NEUTRAL
	}
	
	public enum Size{
		SMALL, MEDIUM, LARGE
	}
	
	
}
