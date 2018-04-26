import java.awt.Color;
import java.awt.Font;
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
	boolean winner = false;
	boolean instruc = false;
	boolean levelComp = false;
	boolean lost = false;
	boolean redo = false;
	public GameWindow(int iWidth, int iHeight) {
		this.width     = iWidth;
		this.height    = iHeight;
		this.extWidth  = iWidth + 17;
		this.extHeight = iHeight + 40;
		this.frameRate = 60;
		this.msDelay   = (int)(1000.0 / frameRate);
		this.currentLevel = 0;
		this.lost = false;
		this.cellDown  = null;
		this.winner = false;
		this.instruc = false;
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
				new Cell(100, 100, Cell.Type.PLAYER, Cell.Size.MEDIUM, 15),
				new Cell(100, 200, Cell.Type.ENEMY, Cell.Size.MEDIUM, 15),
				new Cell(100, 400, Cell.Type.NEUTRAL, Cell.Size.LARGE, 4)
				
		);
		levels.addLevel( // Level 3
				new Cell(300, 200, Cell.Type.PLAYER, Cell.Size.MEDIUM, 30),
				new Cell(100, 500, Cell.Type.NEUTRAL, Cell.Size.SMALL, 15),
				new Cell(400, 500, Cell.Type.ENEMY, Cell.Size.MEDIUM, 15),
				new Cell(400, 200, Cell.Type.ENEMY, Cell.Size.SMALL, 10)
				
		);
		
	}
	
	// loads a specific level
	private void loadLevel(int levelNumber) {
		if(currentLevel == 0) {
			instruc = true;
		}
		try {
			cellList = levels.getLevelData(levelNumber);
			
		}
		catch(Exception e){winner = true; }
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
			cell.attack(msDelay, troopList, cellList);
		}
	}
	private void checkWin() {
		for(Cell cell : cellList) {
			if (cell.getType() == Cell.Type.ENEMY || troopList.size() != 0) {
				return;
			}
		}
		currentLevel += 1;
		levelComp = true;
	
		loadLevel(currentLevel);
	}
	private void checkLost() {
		for(Cell cell : cellList) {
			if (cell.getType() == Cell.Type.PLAYER || troopList.size() != 0) {
				return;
			}
		}
		
		lost = true;
		
	}

	// responsible for drawing the screen
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(winner) {
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 48 ));
			g.setColor(Color.black);
			g.drawString("You Win!", 300, 200);
			
		}else if (levelComp) {
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 28 ));
			g.setColor(Color.black);
			g.drawString("You beat the level, I guess we didn't make it hard enough!", 20, 200);
			g.drawString("Click the Screen to Continue...", 190, 400);
		}
		else if (instruc) {
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 20 ));
			g.setColor(Color.black);
			g.drawString("Cell Game", 20, 25);
			g.drawString("By: Allen Retzler and Grant Bigelow", 20, 50);

			g.drawString("Instructions:", 20, 95);
			g.drawString("Your goal is to help a body recover by using nanobots to cure all infected cells", 20, 120);
			g.drawString("Blue cells have nanobots in them.", 20, 145);
			g.drawString("Red cells are infected and will attack a random cell every 3 seconds.", 20, 170);
			g.drawString("Grey Cells are regular cells. They have neither nanobots nor viruses in them.", 20, 195);

			g.drawString("Blue and Red, cells will regenerate troops over time, depending on how large of a", 20, 220);
			g.drawString("cell they are in.", 20, 245);
			g.drawString("Small cells will regenerate 1 nanobot or virus per second.", 20, 270);
			g.drawString("Medium cells will regenerate 1.5 nanobots or viruses per second.", 20, 295);
			g.drawString("Large cells will regenerate 2 nanobots or viruses per second.", 20, 320);

			g.drawString("You can send nanobots from a blue cell to any other cell by clicking on a blue cell", 20, 370);
			g.drawString("and then clicking on another cell.", 20, 395);
			g.drawString("Click the Screen to Continue...", 190, 500);
		}
		else if(lost) {
			g.setFont(new Font("Comic Sans MS", Font.PLAIN, 48 ));
			g.setColor(Color.black);
			g.drawString("You Lost!", 300, 200);
			
		}
		else 
		
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
		checkLost();
		
		if(levelComp==false && ! lost && ! instruc) {
			move(msDelay);
			regen(msDelay);
			
			cellAttack(msDelay);
			
		}
		
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
				

				if(levelComp)
					levelComp = false;
				if(instruc)
					instruc = false;
			
				
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
