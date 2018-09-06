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
public interface ChessSide {
	public void setSide(side sd);

	public void changeSide();

	public enum side {
		black, white, none
	}

	public enum status {
		blackSpot, whiteSpot, both, neither
	}

	public side getSide();

	public status getStatus();

	public void setStatus(status st);
}
