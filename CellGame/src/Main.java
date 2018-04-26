import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		initGUI();
	}
	
	static void initGUI() {
		int internalWidth  = 800;
		int internalHeight = 600;
		
		JFrame frame = new JFrame("Cell Game 1 (A Sequel will come out with Half Life 3 / Portal 3)");
		GameWindow gameWindow = new GameWindow(internalWidth, internalHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(gameWindow);
		frame.setSize(internalWidth + 17, internalHeight + 40);
		frame.setVisible(true);
	}

}
