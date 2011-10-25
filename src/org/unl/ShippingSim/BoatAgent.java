package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Dimension;

import uchicago.src.sim.engine.SimpleModel;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;
import uchicago.src.sim.util.Random;

public class BoatAgent extends SimpleModel implements Drawable {

	// The position of the boat
	protected int xpos;
	protected int ypos;
	protected int max_x;
	protected int max_y;
	
	// The global space
	protected Object2DGrid space;
	
	
	
	public BoatAgent(int x, int y, OceanSpace space) {
		this.xpos = x;
		this.ypos = y;
		this.space = space;
		Dimension space_dimension = space.getSize();
		max_y = space_dimension.height;
		max_x = space_dimension.width;
		//harborX = space.GetHarbors();
		//harborY = space.GetHarbors();
	}
	
	
	public void play() {
		// Move randomly :)
		// since harbors stationed in middle of window, we want fastest route-->straight lines/diagonals for initial 
		// movement, in case harbor goal changes also almost straight line
		
		int horiz = Random.uniform.nextIntFromTo(-1, 1);
		int vert = Random.uniform.nextIntFromTo(-1, 1);
		
		// the movement has to be dependent on speed of the boat, therefore the movements are different for each boat
		int diagonalUpRight = Random.uniform.nextIntFromTo(-1, 1);
		int diagonalDownRight = Random.uniform.nextIntFromTo(-1, 1);
		int diagonalUpLeft = Random.uniform.nextIntFromTo(-1, 1);
		int diagonalDownLeft = Random.uniform.nextIntFromTo(-1, 1);
		
		space.putObjectAt(xpos, ypos, null);
		xpos = Math.max(Math.min(horiz + xpos, max_x-1), 0);
		ypos = Math.max(Math.min(vert + ypos, max_y-1), 0);
		space.putObjectAt(xpos, ypos, this);
		
	}
	

	public void draw(SimGraphics g) {
		g.drawFastRoundRect(Color.green);
	}


	@Override
	public int getX() {
		return this.xpos;
	}


	@Override
	public int getY() {
		return this.ypos;
	}

}
