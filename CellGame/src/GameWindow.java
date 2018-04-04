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
		
		Timer timer = new Timer(msDelay, this);
		timer.setRepeats(true);
		timer.start();
		
		MouseListen mouseEL = new MouseListen();
		addMouseListener(mouseEL);
		
		cellList.add(new Cell(100, 200, Cell.Type.PLAYER, Cell.Size.MEDIUM, 30));
		cellList.add(new Cell(300, 400, Cell.Type.ENEMY, Cell.Size.SMALL, 10));
		troopList.add(new Troop(cellList.get(0), cellList.get(1), 20));
		
	}
	
	public void move(int msDelta) {
		for (int i = 0; i < troopList.size(); i++) {
			Troop troop = troopList.get(i);
			troop.move(msDelta);
			
			if (troop.finishedMoving) {
				troopList.remove(troop);
				i--;
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("HI");
		move(msDelay);
		repaint();
		
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
