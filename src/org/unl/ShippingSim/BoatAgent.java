package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

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
		
		ArrayList<HarborAgent> harbors = space.GetHarbors();
		for (int i = 0; i < harbors.size(); i++) {
			int harborx = harbors.get(i).getX();
			int harbory = harbors.get(i).getY();
		}
	}
	
	
	public void play() {
		
		//first value is harbor destination, second value is current boat location
		
		int horiz = moveDX(50,xpos);
		int vert = moveDY(100,ypos);
		
		// the movement has to be dependent on speed of the boat, therefore the movements are different for each boat
		
		space.putObjectAt(xpos, ypos, null);
		
		if(horiz == 50 &&  vert == 100){
			ypos = ypos;
			xpos = xpos;
		}
		else{
			xpos = Math.max(Math.min(horiz + xpos, max_x-1), 0);
			ypos = Math.max(Math.min(vert + ypos, max_y-1), 0);
		}
		
	    space.putObjectAt(xpos, ypos, this);
		
	}
	
	public int moveDY(int harbory,int boaty){
		if(harbory == boaty)return 0;
		else return harbory-boaty;
	}

	public int moveDX(int harborx,int boatx){
		if(harborx == boatx)return 0;
		else return harborx-boatx;
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
