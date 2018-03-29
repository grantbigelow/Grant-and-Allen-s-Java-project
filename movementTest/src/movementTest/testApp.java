package movementTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class testApp extends JPanel implements ActionListener{
	
	
	public int startX, startY, endX,endY;
	Timer timer;
	
	boolean dissapear = false;     
	int num = 1;
	 testApp() {
	
	       
	        timer = new Timer(10, this);
	    }
	
	 	public void setCoord(int stx, int sty,int ex,int ey) {
	 		startX = stx;
	 		startY = sty;
	 		endX = ex;
	 		endY = ey;
	 		
	 		if(startX<endX && startY<endY) {
				startX += 1;
				startY+=1;
				dissapear = true;
			}
			else
				dissapear = false;
				
			repaint();
	 	}
	 	
	public void actionPerformed(ActionEvent e) {
			
			setCoord(startX,startY,endX, endY);
		}
	public void drawNumber(Graphics g, int number, int x, int y, int font, boolean isMoving) {
		String sNum = number+"";
		int w = g.getFontMetrics().stringWidth(sNum);
		g.setColor(Color.blue);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, font ));
		if(isMoving)
			g.drawString(sNum, x-(w/3), y+(w/3));
		else
			g.drawString(sNum,x-w,y+w);
	
	}
	public void drawCenteredCircle(Graphics g, int x, int y, int r) {
		  x = x-(r/2);
		  y = y-(r/2);
		  g.fillOval(x,y,r,r);
		}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		drawCenteredCircle(g, 300,300,50);
		if(dissapear == false)
			drawNumber(g, 1 - num, 300, 300, 25,false);
		else
			drawNumber(g, 1, 300, 300, 25,false);
		
		
		g.setColor(Color.black);
		if(dissapear) {
			drawCenteredCircle(g,startX, startY, 50);
			drawNumber(g,1,startX,startY, 10,true);
		
		}
		
	}

	//working on sending troops after click
	
	
}
