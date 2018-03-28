import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameWindow extends JPanel {
	int width, height, extWidth, extHeight;
	
	public GameWindow(int iWidth, int iHeight) {
		this.width     = iWidth;
		this.height    = iHeight;
		this.extWidth  = iWidth + 17;
		this.extHeight = iHeight + 40;
		setSize(this.extWidth, this.extHeight);
		setBackground(Color.WHITE);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawCenteredCircle(g, 50, 50, 25);
	}
	
	//https://stackoverflow.com/a/19387172
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
		}
	

}
