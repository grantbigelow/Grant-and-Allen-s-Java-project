import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GameWindow extends JPanel implements ActionListener {
	int width, height, extWidth, extHeight, msDelay, currentLevel;
	double frameRate;
	
	ArrayList<Cell>  cellList  = new ArrayList<Cell>();
	ArrayList<Troop> troopList = new ArrayList<Troop>();
	
	ArrayList<Cell>  clickList = new ArrayList<Cell>();
	
	Levels 	         levels    = new Levels();
	
	Cell cellDown;
	
	public GameWindow(int iWidth, int iHeight) {
		this.width     = iWidth;
		this.height    = iHeight;
		this.extWidth  = iWidth + 17;
		this.extHeight = iHeight + 40;
		this.frameRate = 60;
		this.msDelay   = (int)(1000.0 / frameRate);
		this.currentLevel = 0;
		
		this.cellDown  = null;
		
		
		setSize(this.extWidth, this.extHeight);
		setBackground(Color.WHITE);
		
		Timer timer = new Timer(msDelay, this);
		timer.setRepeats(true);
		timer.start();
		
		MouseListen mouseEL = new MouseListen();
		addMouseListener(mouseEL);
		
		initLevels();
		loadLevel(this.currentLevel);
	}
	
	// creates the layouts for each level
	private void initLevels() {
		levels.addLevel( // Level 1
				new Cell(250, 150, Cell.Type.PLAYER, Cell.Size.MEDIUM, 30),
				new Cell(500, 400, Cell.Type.ENEMY, Cell.Size.SMALL, 10)
		);
		levels.addLevel( // Level 2
				new Cell(100, 100, Cell.Type.PLAYER, Cell.Size.MEDIUM, 10),
				new Cell(100, 200, Cell.Type.ENEMY, Cell.Size.MEDIUM, 15),
				new Cell(100, 400, Cell.Type.NEUTRAL, Cell.Size.LARGE, 4)
				
		);
		levels.addLevel( // Level 3
				new Cell(300, 200, Cell.Type.PLAYER, Cell.Size.MEDIUM, 30),
				new Cell(400, 200, Cell.Type.ENEMY, Cell.Size.SMALL, 10)
		);
	}
	
	// loads a specific level
	private void loadLevel(int levelNumber) {
		try {
			cellList = levels.getLevelData(levelNumber);
		}
		catch(Exception e){}
	}
	
	private void move(int msDelta) {
		for (int i = 0; i < troopList.size(); i++) {
			Troop troop = troopList.get(i);
			troop.move(msDelta);
			
			if (troop.finishedMoving) {
				troopList.remove(troop);
				i--;
			}
		}
	}
	
	private void regen(int msDelay) {
		for(Cell cell : cellList) {
			cell.regen(msDelay);
			//cellList.get(1).sendEnemy(troopList, cellList.get(0), msDelay);
		}
	}
	private void cellAttack(int msDelay) {
		for(Cell cell : cellList) {
			cell.attack(msDelay);
		}
	}
	private void checkWin() {
		for(Cell cell : cellList) {
			if (cell.getType() == Cell.Type.ENEMY) {
				return;
			}
		}
		currentLevel += 1;
		loadLevel(currentLevel);
	}
	
	// responsible for drawing the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Cell cell: cellList) {
			cell.draw(g);
		}
		for(Troop troop:troopList) {
			troop.draw(g);
		}
	}
	
	// responsible for calling functions every msDelay
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		checkWin();
		move(msDelay);
		regen(msDelay);
		cellAttack(msDelay);
		repaint();
		
	}
	
	class MouseListen implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			for(Cell cell:cellList) {
				if (cell.isCoordInCell(e.getX(), e.getY())) {
					if (clickList.size() == 0 && cell.cellType == Cell.Type.PLAYER && cell.getNumOfTroops() != 0) {
						clickList.add(cell);
						cell.highlight();
					}
					else if (clickList.size() ==1){
						clickList.add(cell);
						cell.highlight();
						
					}
				}
				
				if (clickList.size() == 2) {
					if (clickList.get(0) != clickList.get(1)) {
						clickList.get(0).sendTroops(troopList, clickList.get(1));
						
					}
					clickList.get(0).unhighlight();
					clickList.get(1).unhighlight();
				
					clickList.clear();
				}
			}
			
			repaint();
		}
		
	}
}
