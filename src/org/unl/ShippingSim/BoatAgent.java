package org.unl.ShippingSim;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import uchicago.src.sim.engine.SimpleModel;
import uchicago.src.sim.gui.Drawable;
import uchicago.src.sim.gui.SimGraphics;
import uchicago.src.sim.space.Object2DGrid;
import uchicago.src.sim.util.Random;

public class BoatAgent extends SimpleModel implements Drawable, AbstractAgent {

	// The position of the boat
	protected float xpos;
	protected float ypos;
	protected int max_x;
	protected int max_y;
	
	//  Speed of the boat
	protected double speed;
	
	// The global space
	protected Object2DGrid space;
	
	
	
	public BoatAgent(int x, int y, OceanSpace space) {
		this.xpos = x;
		this.ypos = y;
		this.space = space;
		Dimension space_dimension = space.getSize();
		max_y = space_dimension.height;
		max_x = space_dimension.width;
		speed = (float)0.2;
		
		ArrayList<HarborAgent> harbors = space.GetHarbors();
		for (int i = 0; i < harbors.size(); i++) {
			int harborx = harbors.get(i).getX();
			int harbory = harbors.get(i).getY();
		}
	}
	
	
	public void step() {
		
		int harborx = 50;
		int harbory = 50;
		
		double a = (double) harborx - xpos;
		double b = (double) harbory - ypos;
		double distance = Math.sqrt((Math.pow(a, 2) +  Math.pow(b, 2)));
		
		double degrad = Math.PI/180.0;
		double new_xpos = this.speed * (a/distance) + xpos;
		double new_ypos = this.speed * (b/distance) + ypos;
		//first value is harbor destination, second value is current boat location
		
		// First, find the slope of the line between where we are, and where we want to go
		//float horiz = moveDX(50,xpos);
		//float vert = moveDY(100,ypos);
		
		
		
		// the movement has to be dependent on speed of the boat, therefore the movements are different for each boat
		
		space.putObjectAt((int)xpos, (int)ypos, null);
		/*
		if(horiz == 50 &&  vert == 100){
			ypos = ypos;
			xpos = xpos;
		}
		else{
			xpos = Math.max(Math.min(horiz + xpos, max_x-1), 0);
			ypos = Math.max(Math.min(vert + ypos, max_y-1), 0);
		}
		*/
	    space.putObjectAt((int)new_xpos, (int)new_ypos, this);
	    xpos = (float)new_xpos;
	    ypos = (float)new_ypos;
		
	}
	
	public float moveDY(int harbory,float boaty){
		if(harbory == boaty)return 0;
		else return harbory-boaty;
	}

	public float moveDX(int harborx,float boatx){
		if(harborx == boatx)return 0;
		else return harborx-boatx;
	}
	
	public void draw(SimGraphics g) {
		g.drawFastRoundRect(Color.green);
	}


	@Override
	public int getX() {
		return (int)this.xpos;
	}


	@Override
	public int getY() {
		return (int)this.ypos;
	}

}