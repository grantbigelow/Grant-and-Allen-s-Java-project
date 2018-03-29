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
	
	 testApp() {
	
	       
	        timer = new Timer(10, this);
	    }
	
	 	public void setCoord(int stx, int sty,int ex,int ey) {
	 		startX = stx;
	 		startY = sty;
	 		endX = ex;
	 		endY = ey;
	 	}
	 	
	    public void actionPerformed(ActionEvent e) {
			
			
			if(startX<endX && startY<endY) {
				startX += 1;
				startY+=1;
				dissapear = true;
			}
			else
				dissapear = false;
				
			repaint();
			
				
			
		}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillOval(300, 300, 50, 50);
		g.setColor(Color.blue);
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 25 ));
		g.drawString("15", 310, 330);
		g.setColor(Color.black);
		if(dissapear)
			g.fillOval(startX, startY, 10, 10);
		
		
		
	}

	
	
	
}
