package movementTest;

import javax.swing.JFrame;

public class testMain {

	public static void main(String[] args) {
		createAndShowGUI();
		

	}
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		testApp board = new testApp();
		frame.add(board);
		
		frame.setSize(622, 722);
		
		frame.setVisible(true);
		board.setCoord(0, 0, 305, 305);
		board.timer.start();
		

	}

}
