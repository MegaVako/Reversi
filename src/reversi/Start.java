/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author SimonSun
 */
public class Start implements Keys {
	private static JFrame object;
	private static final int INITIAL_X_POSITION = 300;
	private static final int INITIAL_Y_POSITION = 10;
	static final int INITIAL_X_LENGTH = 600;
	static final int INITIAL_Y_LENGTH = 800;
	private static Menu menu;

	Start() {
		object = new JFrame("Reversi");
		menu = new Menu();
		//GameWindow gamewindow = new GameWindow();
		object.setBounds(INITIAL_X_POSITION, INITIAL_Y_POSITION, INITIAL_X_LENGTH, INITIAL_Y_LENGTH);
		object.setTitle("Reversi");
		object.setResizable(false);
		object.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		object.add(menu);
		object.setFocusable(true);
		object.setFocusTraversalKeysEnabled(false);
		object.setVisible(true);
	}

	@Override
	public void selectedMenuItem(int item) {
		switch (item) {
		case 1:
			object.remove(menu);
			menu = null;
			object.revalidate();
			GameWindow gamewindow = new GameWindow();
			object.add(gamewindow);
			object.revalidate();
			System.out.println("b1 pressed");
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}
}
