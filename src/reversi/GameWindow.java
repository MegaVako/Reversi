/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author SimonSun
 */
public class GameWindow extends JPanel {
	private static final int GIRD_X_ORIGIN = 63, GIRD_Y_ORIGIN = 63, GRID_X_WIDTH = 488, GRID_Y_HEIGHT = 488,
			GRID_BOX_NUMBER = 8,

			GRID_BORDER_WIDTH = 5;
	private static int gridSpace;
	private Game game;
	
	GameWindow() {
		gridSpace = GRID_X_WIDTH / GRID_BOX_NUMBER;
		game = new Game();
		System.out.println("Game Window Generated");
		System.out.println("Grid Space: " + gridSpace);
	}

	@Override
	public void paint(Graphics g) {
		System.out.println("render called");
		// draw Background
		g.setColor(new Color(190,120,0));
		g.fillRect(GIRD_X_ORIGIN, GIRD_Y_ORIGIN, GRID_X_WIDTH, GRID_Y_HEIGHT);

		// draw border
		g.setColor(Color.BLACK);
		g.fillRect(GIRD_X_ORIGIN - GRID_BORDER_WIDTH, GIRD_Y_ORIGIN - GRID_BORDER_WIDTH, GRID_BORDER_WIDTH,
				GRID_Y_HEIGHT + 2 * GRID_BORDER_WIDTH);
		g.fillRect(GIRD_X_ORIGIN + GRID_X_WIDTH, GIRD_Y_ORIGIN - GRID_BORDER_WIDTH, GRID_BORDER_WIDTH,
				GRID_Y_HEIGHT + 2 * GRID_BORDER_WIDTH);
		g.fillRect(GIRD_X_ORIGIN, GIRD_Y_ORIGIN - GRID_BORDER_WIDTH, GRID_X_WIDTH, GRID_BORDER_WIDTH);
		g.fillRect(GIRD_X_ORIGIN, GIRD_Y_ORIGIN + GRID_Y_HEIGHT, GRID_X_WIDTH, GRID_BORDER_WIDTH);

		int check = (GRID_Y_HEIGHT + GIRD_Y_ORIGIN) - (GIRD_Y_ORIGIN - GRID_BORDER_WIDTH);
		System.out.println(check);
		// draw grid
		for (int i = 0; i < GRID_BOX_NUMBER; i++) {
			int gridSpace = i * GameWindow.gridSpace;
			g.setColor(Color.BLACK);
			// Horizontal
			g.drawLine(GIRD_X_ORIGIN + gridSpace, GIRD_Y_ORIGIN, GIRD_X_ORIGIN + gridSpace,
					GIRD_Y_ORIGIN + GRID_Y_HEIGHT);
			// Vertical
			g.drawLine(GIRD_X_ORIGIN, GIRD_Y_ORIGIN + gridSpace, GIRD_X_ORIGIN + GRID_X_WIDTH,
					GIRD_Y_ORIGIN + gridSpace);
		}
		game.paint(g);
		
	}

	public static int GRID_BOX_NUMBER_Inqury() {
		return GRID_BOX_NUMBER;
	}

	public static int GIRD_X_ORIGIN_Inqury() {
		return GIRD_X_ORIGIN;
	}

	public static int GIRD_Y_ORIGIN_Inqury() {
		return GIRD_Y_ORIGIN;
	}

	public static int GRID_X_WIDTH_Inqury() {
		return GRID_X_WIDTH;
	}

	public static int GRID_Y_HEIGHT_Inqury() {
		return GRID_Y_HEIGHT;
	}

	public static int gridSpace_Inqury() {
		return gridSpace;
	}
}
