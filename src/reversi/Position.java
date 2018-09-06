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
public interface Position {
	public int xCenter();

	public int yCenter();

	public int xCordinate();

	public int yCordinate();

	public int relXPosition();

	public int relYPosition();
}
