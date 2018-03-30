import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameWindow extends JPanel {
	int width, height, extWidth, extHeight, msDelay;
	double frameRate;
	
	ArrayList<Cell>  cellList  = new ArrayList<Cell>();
	ArrayList<Troop> troopList = new ArrayList<Troop>();
	
	ArrayList<Cell>  clickList = new ArrayList<Cell>();
	
	public GameWindow(int iWidth, int iHeight) {
		this.width     = iWidth;
		this.height    = iHeight;
		this.extWidth  = iWidth + 17;
		this.extHeight = iHeight + 40;
		this.frameRate = 30;
		this.msDelay   = (int)(1000.0 / frameRate);
		setSize(this.extWidth, this.extHeight);
		setBackground(Color.WHITE);
		
		Timer timer = new Timer();
		
		MouseListen mouseEL = new MouseListen();
		addMouseListener(mouseEL);
		
		cellList.add(new Cell(100, 200, Cell.Type.PLAYER, Cell.Size.MEDIUM, 30, msDelay));
		cellList.add(new Cell(300, 400, Cell.Type.ENEMY, Cell.Size.SMALL, 10, msDelay));
		
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				move();
				repaint();
				
			}}, msDelay, msDelay);
		
		
	}
	
	public void move() {
		for (Troop troop : troopList) {
			troop.move();
			
			if (troop.finishedMoving) {
				troopList.remove(troop);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Cell cell: cellList) {
			cell.draw(g);
		}
		for(Troop troop:troopList) {
			troop.draw(g);
		}
	}
	
	//https://stackoverflow.com/a/19387172
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
		}
	class MouseListen implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			for(Cell cell:cellList) {
				if (cell.isCoordInCell(e.getX(), e.getY()) &&
					(cell.cellType == Cell.Type.PLAYER || clickList.size() == 1 )) {
					clickList.add(cell);
					break;
				}
				
				if (clickList.size() == 2) {
					cellList.get(0).sendTroops(troopList, cellList.get(1));
					cellList.clear();
				}
			}
			
			repaint();
			
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
			
		}
		
	}

}
