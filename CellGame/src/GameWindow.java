import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GameWindow extends JPanel {
	int width, height, extWidth, extHeight;
	
	ArrayList<Cell>  cellList  = new ArrayList<Cell>();
	ArrayList<Troop> troopList = new ArrayList<Troop>();
	
	public GameWindow(int iWidth, int iHeight) {
		this.width     = iWidth;
		this.height    = iHeight;
		this.extWidth  = iWidth + 17;
		this.extHeight = iHeight + 40;
		setSize(this.extWidth, this.extHeight);
		setBackground(Color.WHITE);
		
		cellList.add(new Cell(100, 200, Cell.Type.PLAYER, Cell.Size.MEDIUM, 100));
		cellList.add(new Cell(300, 400, Cell.Type.ENEMY, Cell.Size.SMALL, 10));
		cellList.add(new Cell(100, 400, Cell.Type.NEUTRAL, Cell.Size.LARGE, 5));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Cell cell: cellList) {
			cell.draw(g);
		}
	}
	
	//https://stackoverflow.com/a/19387172
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
		}
	

}
