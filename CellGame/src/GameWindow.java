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
	}

}
