/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Side;

/**
 *
 * @author SimonSun
 */

//IMPORTANT, rel / REL stands for Relative --> Relative Position on the Chess Board
//relX --> 0-7, relY --> 0-7

public class Game implements Runnable{
	private ArrayList<Chess> chessArrayList;
	private static final int POINT_RADIUS = 6;
	private static final int CHESS_WIDTH = 20;
	private String currentInput;
	private int openSpace[][];
	private int boxIndex;
	private Thread runGame;
	private enum gameStatus {
		pLayerWin, playerLost, tie, notFinished
	}

	Game() {
		chessArrayList = new ArrayList<Chess>();
		boxIndex = GameWindow.GRID_BOX_NUMBER_Inqury();
		initSpots();
		setInitialChess();
		printAllChessSide();
		fillOpenSpace();
		startGame();
	}

	public void paint(Graphics g) {
		System.out.println("paint game called");
		g.setColor(Color.BLACK);
		// Center check
		for (Chess chess : chessArrayList) {
			Position p = chess;
			// g.fillOval(p.xCenter()-POINT_RADIUS,p.yCenter()-POINT_RADIUS, POINT_RADIUS,
			// POINT_RADIUS);
			ChessSide s = chess;
			if (s.getSide() != ChessSide.side.none) {
				int x = p.xCenter() - CHESS_WIDTH;
				int y = p.yCenter() - CHESS_WIDTH;
				int r = CHESS_WIDTH * 2 + 1;
				if (s.getSide() == ChessSide.side.white) {
					g.setColor(Color.WHITE);
					g.fillOval(x, y, r, r);
					g.drawOval(x, y, r, r);
				} else if (s.getSide() == ChessSide.side.black) {
					g.setColor(Color.BLACK);
					g.fillOval(x, y, r, r);
				}
			} if(s.getStatus() != ChessSide.status.neither) {
				switch(s.getStatus()) {
				case whiteSpot:
					g.setColor(Color.BLACK);
					g.drawOval(p.xCenter()-POINT_RADIUS,p.yCenter()-POINT_RADIUS, POINT_RADIUS,
					POINT_RADIUS);
					break;
				case blackSpot:
					g.setColor(Color.BLACK);
					g.fillOval(p.xCenter()-POINT_RADIUS,p.yCenter()-POINT_RADIUS, POINT_RADIUS,
					POINT_RADIUS);
					break;
				case both:
					g.setColor(Color.RED);
					g.fillOval(p.xCenter()-POINT_RADIUS,p.yCenter()-POINT_RADIUS, POINT_RADIUS,
					POINT_RADIUS);
					break;
				}
			}
		}
	}

	private void initSpots() {
		for (int i = 0; i < GameWindow.GRID_BOX_NUMBER_Inqury(); i++) {
			for (int k = 0; k < GameWindow.GRID_BOX_NUMBER_Inqury(); k++) {
				assignSpots(i, k);
				System.out.println("i= " + i + " ; " + "k= " + k);
			}
		}
	}

	private void assignSpots(int i, int k) {
		int gridSpace = GameWindow.gridSpace_Inqury();
		int xOrigin = GameWindow.GIRD_X_ORIGIN_Inqury();
		int yOrigin = GameWindow.GIRD_X_ORIGIN_Inqury();

		int xCenter = xOrigin + i * gridSpace + gridSpace / 2 + 1;
		int yCenter = yOrigin + k * gridSpace + gridSpace / 2 + 1;

		Chess chess = new Chess(i, k, xCenter, yCenter);
		chessArrayList.add(chess);
	}

	private void setInitialChess() {
		for (Chess chess : chessArrayList) {
			Position p = chess;
			ChessSide s = chess;
			if (p.relXPosition() == 3) {
				if (p.relYPosition() == 3) {
					s.setSide(ChessSide.side.black);
				} else if (p.relYPosition() == 4) {
					s.setSide(ChessSide.side.white);
				}
			} else if (p.relXPosition() == 4) {
				if (p.relYPosition() == 3) {
					s.setSide(ChessSide.side.white);
				} else if (p.relYPosition() == 4) {
					s.setSide(ChessSide.side.black);
				}
			}
		}
	}

	public void startKeyboardGameMode() {
		gameStatus gs = gameStatus.notFinished;
		Scanner scanner = new Scanner(System.in);
		A: do {
			String inputChessXPosition;
			String inputChessYPosition;
			do {
				drawLine();
				System.out.println("Input White Chess Relative X Position");

				inputChessXPosition = scanner.nextLine();
				if (breakGame(inputChessXPosition)) {
					break A;
				}

				drawLine();
				System.out.println("Input White Chess Relative Y Position");
				inputChessYPosition = scanner.nextLine();
				if (breakGame(inputChessYPosition)) {
					break A;
				}
			} while (!sentChessXYString(inputChessXPosition, inputChessYPosition));

		} while (gs == gameStatus.notFinished);
		System.out.println("End Game");
	}

	private boolean breakGame(String input) {
		boolean exitConfirmation = false;
		exitConfirmation = input.equals("exit");
		return exitConfirmation;
	}

	private void drawLine() {
		System.out.println("===========================");
	}

	private boolean sentChessXYString(String x, String y) {
		boolean xChecked = false;
		boolean yChecked = false;
		boolean allowProcess = false;
		int relXPosition = 0;
		int relYPosition = 0;

		relXPosition = changeInputToInteger(x);
		relYPosition = changeInputToInteger(y);
		xChecked = checkInputInt(relXPosition);
		yChecked = checkInputInt(relYPosition);

		if (!xChecked || !yChecked) {
			allowProcess = false;
			System.out.println("Invalide input");
		} else {
			checkChessStatus(relXPosition, relYPosition);
			allowProcess = true;
		}
		return allowProcess;
	}

	private int changeInputToInteger(String inputStr) {
		int inputInt = 10;
		try {
			inputInt = Integer.parseInt(inputStr);
		} catch (NumberFormatException e) {
			System.out.println("not a number");
			System.out.println("Try switch");
			inputInt = changeInputWordToInteger(inputStr);
		}
		return inputInt;
	}

	private int changeInputWordToInteger(String inputStr) {
		int inputInt = 10;
		switch (inputStr.toLowerCase()) {
		case "one":
			inputInt = 1;
			break;
		case "two":
			inputInt = 2;
			break;
		case "three":
			inputInt = 3;
			break;
		case "four":
			inputInt = 4;
			break;
		case "five":
			inputInt = 5;
			break;
		case "six":
			inputInt = 6;
			break;
		case "seven":
			inputInt = 7;
			break;
		default:
			inputInt = 10;
			break;
		}
		return inputInt;
	}

	private boolean checkInputInt(int inputInt) {
		boolean checkInputInt = false;
		if (inputInt != 10) {
			checkInputInt = true;
		}
		return checkInputInt;
	}

	private boolean checkChessStatus(int x, int y) {
    	boolean avaliable = false;
    	for(Chess c : chessArrayList) {
    		Position p = c;
    		if(x == p.relXPosition() && y == p.relYPosition()) {
    			ChessSide s = c;
    			if(s.getSide() == ChessSide.side.none) {
    				if(checkSpotAvalibility(s)) {
    					avaliable = true;
    					s.setSide(ChessSide.side.white);
    					break;
    				} else {
    					avaliable = false;
    					s = null;
    					break;
    				}
    			} else {
    				avaliable = false;
    				s = null;
    				break;
    			}
    		} else {
    			p = null;
    		}
    	}
        return avaliable;
    }

	private boolean checkSpotAvalibility(ChessSide s) {
		boolean avalible = false;
		if (s.getStatus() == ChessSide.status.whiteSpot) {
			avalible = true;
		}
		return avalible;
	}

	private void fillOpenSpace() {
		for (Chess c : chessArrayList) {
			ChessSide s = c;
			if (s.getSide() == ChessSide.side.none) {
				determineOpenSpot(c);
			} else if (s.getSide() == ChessSide.side.black || s.getSide() == ChessSide.side.white) {
				s.setStatus(ChessSide.status.neither);
			}
		}
	}

	private void determineOpenSpot(Chess c) {
		int relX = 0;
		int relY = 0;
		Position p = c;
		relX = p.relXPosition();
		relY = p.relYPosition();

		setNewChessStatus(getSurroundingSpots(relX, relY, checkRelXY(relX), checkRelXY(relY)), c, relX, relY);
	}

	private String checkRelXY(int input) {
		String checked = "unchecked";
		if (input == 0) {
			checked = "Left Bound";
		} else if (input == 7) {
			checked = "Right Bound";
		} else {
			checked = "normal";
		}
		return checked;
	}

	private ChessSide.status getSurroundingSpots(int relX, int relY, String xC, String yC) {
		// relN --> the relative position of a chess in the "chessArrayList"
		int relN = relX * (boxIndex) + relY;
		ChessSide.status st = ChessSide.status.neither;
		ChessSide.side topSide = ChessSide.side.none;
		ChessSide.side botSide = ChessSide.side.none;
		ChessSide.side topLeftSide = ChessSide.side.none;
		ChessSide.side botLeftSide = ChessSide.side.none;
		ChessSide.side topRightSide = ChessSide.side.none;
		ChessSide.side botRightSide = ChessSide.side.none;
		ChessSide.side leftSide = ChessSide.side.none;
		ChessSide.side rightSide = ChessSide.side.none;

		if (xC == "Left Bound") {
			if (yC == "Left Bound") {
				// right,bot,botRight
				rightSide = getSideRelN("right", relN);
				botSide = getSideRelN("bot", relN);
				botRightSide = getSideRelN("botRight", relN);
			} else if (yC == "Right Bound") {
				// right,top,topRight
				rightSide = getSideRelN("right", relN);
				topSide = getSideRelN("top", relN);
				topRightSide = getSideRelN("topRight", relN);
			} else {
				// right,top,bot,TR,BR
				rightSide = getSideRelN("right", relN);
				topSide = getSideRelN("top", relN);
				topRightSide = getSideRelN("topRight", relN);
				botSide = getSideRelN("bot", relN);
				botRightSide = getSideRelN("botRight", relN);
			}
		} else if (xC == "Right Bound") {
			if (yC == "Left Bound") {
				// left,bot,botLeft
				leftSide = getSideRelN("left", relN);
				botSide = getSideRelN("bot", relN);
				botLeftSide = getSideRelN("botLeft", relN);
			} else if (yC == "Right Bound") {
				// left,top,topLeft
				leftSide = getSideRelN("left", relN);
				topSide = getSideRelN("top", relN);
				topLeftSide = getSideRelN("topLeft", relN);
			} else {
				// left,top,bot,TL,BL
				leftSide = getSideRelN("left", relN);
				botSide = getSideRelN("bot", relN);
				botLeftSide = getSideRelN("botLeft", relN);
				topSide = getSideRelN("top", relN);
				topLeftSide = getSideRelN("topLeft", relN);
			}
		} else {
			if (yC == "Left Bound") {
				// left,right,bot,botRight,botLeft
				rightSide = getSideRelN("right", relN);
				leftSide = getSideRelN("left", relN);
				botSide = getSideRelN("bot", relN);
				botRightSide = getSideRelN("botRight", relN);
				botLeftSide = getSideRelN("botLeft", relN);
			} else if (yC == "Right Bound") {
				// right,top,Left,topRight,topLeft
				rightSide = getSideRelN("right", relN);
				leftSide = getSideRelN("left", relN);
				topSide = getSideRelN("top", relN);
				topRightSide = getSideRelN("topRight", relN);
				topLeftSide = getSideRelN("topLeft", relN);
			} else {
				leftSide = getSideRelN("left", relN);
				botLeftSide = getSideRelN("botLeft", relN);
				topLeftSide = getSideRelN("topLeft", relN);

				topSide = getSideRelN("top", relN);
				botSide = getSideRelN("bot", relN);

				rightSide = getSideRelN("right", relN);
				topRightSide = getSideRelN("topRight", relN);
				botRightSide = getSideRelN("botRight", relN);
			}
		}
		System.out.println(xC + yC);
		// do the test
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				st = testChessRows(botRightSide, topLeftSide, relN, relX, relY, testCondition.topLeftToBotRight, st);
				break;
			case 1:
				st = testChessRows(botSide, topSide, relN, relX, relY, testCondition.vertical, st);
				break;
			case 2:
				st = testChessRows(rightSide, leftSide, relN, relX, relY, testCondition.horizontal, st);
				break;
			case 3:
				st = testChessRows(topRightSide, botLeftSide, relN, relX, relY, testCondition.botLeftToTopRight, st);
				break;
			case 4:
			default:
				st = ChessSide.status.neither;
				System.out.println("test loop test Index error");
			}
			if (st == ChessSide.status.both) {
				break;
			}
			System.out.println("testChessRows check " +i);
		}
		return st;
	}

	private ChessSide.side getSideRelN(String sideStr, int relN) {
		ChessSide.side sd = ChessSide.side.none;
		switch (sideStr) {
		case "top":
			sd = getSide(relN - 1);
			break;
		case "bot":
			sd = getSide(relN + 1);
			break;
		case "topLeft":
			sd = getSide(relN - 9);
			break;
		case "botLeft":
			sd = getSide(relN - 7);
			break;
		case "topRight":
			sd = getSide(relN + 7);
			break;
		case "botRight":
			sd = getSide(relN + 9);
			break;
		case "left":
			sd = getSide(relN - 8);
			break;
		case "right":
			sd = getSide(relN + 8);
			break;
		}
		return sd;
	}

	private ChessSide.side getSide(int relN) {
		ChessSide.side sd = ChessSide.side.none;
		ChessSide s = chessArrayList.get(relN);
		sd = s.getSide();
		return sd;
	}

	private ChessSide.status testChessRows(ChessSide.side sd1, ChessSide.side sd2, int relN, int relX, int relY, testCondition tc, ChessSide.status st) {

		// sd1 --> L to R, Top to Bot in general (bot first)
		// sd2 --> inverse of sd1

		// relN + boxIndex * X
		// relN + Y
		// relN + boxIndex * X + Y --> TL to BR
		// relN + boxIndex * X - Y --> BL to TR
		
		ChessSide.side thisSide;
		boolean finished = false;
		System.out.println("sd1: " + sd1 + "/" + "sd2: " + sd2);
		A: do {
			if (sd1 != ChessSide.side.none) {
				B: for (int K = 2; checkKBoundPhaseOne(relX, relY, K, tc); K++) {
					thisSide = getTestSide(relN, K, tc);
					System.out.println("thisSide = " + thisSide);
					if (thisSide == ChessSide.side.none) {
						break B;
					} else {
						st = setSpotStatusPhaseOne(thisSide, sd1);
						if (st != ChessSide.status.neither) {
							break B;
						}
					}
					System.out.println("B run check " + K);
				}
			}
			if (sd2 != ChessSide.side.none) {
				C: for (int K = -2; checkKBoundPhaseTwo(relX, relY , K, tc); K--) {
					thisSide = getTestSide(relN, K, tc);
					if (thisSide == ChessSide.side.none) {
						break C;
					} else {
						st = setSpotStatusPhaseTwo(thisSide, sd2, st);
						if (st == ChessSide.status.both) {
							break A;
						}
					}
				}
			}
			finished = true;
		} while (!finished);
		return st;
	}

	private ChessSide.status setSpotStatusPhaseOne(ChessSide.side thisSide, ChessSide.side testedSide) {
		ChessSide.status st = ChessSide.status.neither;
		System.out.println("P1 thisSide = " +  thisSide);
		if (thisSide != ChessSide.side.none && thisSide != testedSide) {
			if (testedSide == ChessSide.side.black) {
				st = ChessSide.status.whiteSpot;
			} else {
				st = ChessSide.status.blackSpot;
			}
		}
		System.out.println(st);
		return st;
	}

	private ChessSide.status setSpotStatusPhaseTwo(ChessSide.side thisSide, ChessSide.side testedSide, ChessSide.status st) {
		if (thisSide != ChessSide.side.none && thisSide != testedSide) {
			if (testedSide == ChessSide.side.black) {
				if (st == ChessSide.status.blackSpot) {
					st = ChessSide.status.both;
				} else {
					st = ChessSide.status.whiteSpot;
				}
			} else {
				if (st == ChessSide.status.whiteSpot) {
					st = ChessSide.status.both;
				} else {
					st = ChessSide.status.blackSpot;
				}
			}
		}
		System.out.println(st);
		return st;
	}

	private enum testCondition {
		vertical, horizontal, topLeftToBotRight, botLeftToTopRight, none
	}

	private ChessSide.side getTestSide(int relN, int K, testCondition tc) {
		ChessSide.side thisSide;
		switch (tc) {
		case botLeftToTopRight:
			thisSide = getSide(relN + boxIndex * K - K);
			break;
		case vertical:
			thisSide = getSide(relN + K);
			break;
		case horizontal:
			thisSide = getSide(relN + boxIndex * K);
			break;
		case topLeftToBotRight:
			thisSide = getSide(relN + boxIndex * K + K);
			break;
		case none:
		default:
			thisSide = ChessSide.side.none;
			System.out.println("test P1 default error");
			break;
		}
		return thisSide;
	}

	private void setNewChessStatus(ChessSide.status st, Chess c, int relX, int relY) {
		ChessSide s = c;
		s.setStatus(st);
		printStatus(relX, relY, st);
	}
	private void printStatus(int relX, int relY, ChessSide.status st) {
		System.out.println(st);
		System.out.println("X: " + relX + " " + "Y: " + relY);
		drawLine();
	}
	private void printAllChessSide() {

		for(int i = 0; i<63;i++) {
			Position p = chessArrayList.get(i);
			ChessSide s = chessArrayList.get(i);
			System.out.println("X: "+ p.relXPosition()+ "Y: "+ p.relYPosition() + " = " + s.getSide());
			System.out.println(i);
			drawLine();
		}
	}
	@Override
	public void run() {
		startKeyboardGameMode();
	}
	private boolean checkKBoundPhaseOne(int relX, int relY, int K, testCondition tc) {
		boolean inBound = false;
		switch(tc) {
			case vertical:
				if(relY + K <=7 && relY - K>=0) {
					inBound = true;
				}break;
			case horizontal:
				if(relX + K <=7 && relX - K>=0) {
					inBound = true;
				}break;
			case topLeftToBotRight:
			case botLeftToTopRight:
				if(relX+K<=7 && relX-K>=0 && relY + K <=7 && relY - K>=0) {
					inBound = true;
				}break;
			default:
				inBound = false;
		}
		return inBound;
	}
	private boolean checkKBoundPhaseTwo(int relX, int relY, int K, testCondition tc) {
		boolean inBound = false;
		switch(tc) {
		case vertical:
			if(relY + K >= 0 && relY - K <= 7) {
				inBound = true;
			}break;
		case horizontal:
			if(relX + K >= 0 && relX - K <= 7) {
				inBound = true;
			}break;
		case topLeftToBotRight:
		case botLeftToTopRight:
			if(relY + K >= 0 && relY - K <= 7 && relX + K >= 0 && relX - K <= 7) {
				inBound = true;
			}break;
		default:
			inBound = false;

		}
		return inBound;
	}
	private void startGame() {
		runGame = new Thread();
		runGame.run();
		System.out.println("Start Game Called");
	}
}

