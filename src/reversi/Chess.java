/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reversi;

/**
 *
 * @author SimonSun
 */
public class Chess implements Position, ChessSide {
	private int xCenter;
	private int yCenter;
	private int xCordinate;
	private int yCordinate;
	private int relXPosition;
	private int relYPosition;

	private side sd = side.none;
	private status st = status.neither;

	Chess(int xCordniate, int yCordinate, int xCenter, int yCenter) {
		this.xCenter = xCenter;
		this.yCenter = yCenter;

		this.xCordinate = GameWindow.gridSpace_Inqury() * xCordniate + GameWindow.GIRD_X_ORIGIN_Inqury();
		this.yCordinate = GameWindow.gridSpace_Inqury() * yCordinate + GameWindow.GIRD_X_ORIGIN_Inqury();

		relXPosition = xCordniate;
		relYPosition = yCordinate;
	}

	@Override
	public int xCenter() {
		return xCenter;
	}
	public void setX(int x) { 
		xCenter = x; 
	} 
	
	@Override
	public int yCenter() {
		return yCenter;
	}

	@Override
	public int xCordinate() {
		return xCordinate;
	}

	@Override
	public int yCordinate() {
		return yCordinate;
	}

	@Override
	public void setSide(side sd) {
		this.sd = sd;
	}

	@Override
	public void changeSide() {

	}

	@Override
	public int relXPosition() {
		return relXPosition;
	}

	@Override
	public int relYPosition() {
		return relYPosition;
	}

	@Override
	public side getSide() {
		return sd;
	}

	@Override
	public void setStatus(status st) {
		this.st = st;
	}

	@Override
	public status getStatus() {
		return st;
	}

}
