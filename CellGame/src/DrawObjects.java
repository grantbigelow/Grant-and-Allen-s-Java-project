import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public abstract class DrawObjects {
	public static void drawCenteredCircle(Graphics g, int centerX, int centerY, int radius) {
		drawCenteredCircle(g, centerX, centerY, radius, true);
	}
	public static void drawCenteredCircle(Graphics g, int centerX, int centerY, int radius, boolean isFilled) {
		int x = centerX - radius;
		int y = centerY - radius;
		if (isFilled)
			g.fillOval(x,y, 2 * radius, 2 * radius);
		else
			g.drawOval(x,y, 2 * radius, 2 * radius);
		
	}
	public static void drawNumber(Graphics g, int number, int x, int y, int fontSize) {
		String sNum = number+"";
		int w;
		int h;
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, fontSize ));
		w = g.getFontMetrics().stringWidth(sNum);
		h = g.getFontMetrics().getAscent();
		
		
		//g.setColor(Color.blue);

		g.drawString(sNum,x-(w/2),y+(h/2));
	}

}
