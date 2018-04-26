import java.util.ArrayList;

public class Levels {
	ArrayList<Level> levelList = new ArrayList<Level>();
	public Levels() {}
	public Levels(Level...levels) {
		for (Level level : levels) {
			levelList.add(level);
		}
		
	}

	public void addLevels(Level... levels) {
		for(Level level: levels) {
			levelList.add(level);
		}
	}
	public void addLevel(Level level) {
		levelList.add(level);
	}
	public void addLevel(Cell... cells) {
		levelList.add(new Level(cells));
	}
	
	public ArrayList<Cell> getLevelData(int levelNumber){
		Level currentLevel = levelList.get(levelNumber);
		return currentLevel.getCells();
	}
	
	public class Level{
		ArrayList<Cell> levelCells = new ArrayList<Cell>();
		public Level () {}
		
		public Level(Cell... cells)
		{
			for (Cell c : cells) {
				levelCells.add(c);
			}
			
		}
				
		public void addCells(Cell...cells) {
			for (Cell c : cells) {
				levelCells.add(c);
			}
		}
		public ArrayList<Cell> getCells(){
			ArrayList<Cell> rtn = new ArrayList<Cell>();
			for(Cell cell : levelCells) {
				rtn.add(cell.copy());
			}
			return rtn;
		}
	}
}
