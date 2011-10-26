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
	protected OceanSpace space;
	
	// Whether we are at a harbor or not
	protected boolean loading = false;
	
	// The harbor we are traveling to
	protected HarborAgent target_harbor;
	
	// The size of the boat, affects unload time
	protected double size;
	
	
	
	public BoatAgent(int x, int y, OceanSpace space) {
		this.xpos = x;
		this.ypos = y;
		this.space = space;
		Dimension space_dimension = space.getSize();
		max_y = space_dimension.height - 1;
		max_x = space_dimension.width - 1;
		speed = (float)0.2;
		

	}
	
	
	public void step() {
		
		// If we're loading, don't do anything
		if (loading == true) 
			return;
		
		// First iteration, the target_harbor will be null
		if (target_harbor == null) {
			target_harbor = CalculateNextHarbor();
		}
		
		int harborx = target_harbor.getX();
		int harbory = target_harbor.getY();
		
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
	    xpos = Math.max(Math.min((float)new_xpos, max_x), 0);
	    ypos = Math.max(Math.min((float)new_ypos, max_y), 0);
	    
	    space.putObjectAt((int)xpos, (int)ypos, this);

	    
	    // Check if we've arrived at the harbor.
	    if ((harborx == (int)xpos) && (harbory == (int)ypos)) {
	    	this.target_harbor.enqueueBoat(this);
	    	this.loading = true;
	    }
		
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
	
	/**
	 * The Harbor will call this when the boat is done loading, and should
	 * loading, and should calculate the next harbor to go to.
	 */
	public void doneLoading() {
		// First, set us as done loading
		loading = false;
		
		// Calculate the next harbor to go to
		this.target_harbor = CalculateNextHarbor();
	}
	

	/**
	 * Method to calcualte the next harbor to travel to.
	 */
	protected HarborAgent CalculateNextHarbor() {
		int harbor_id = Random.uniform.nextIntFromTo(0, this.space.GetHarbors().size()-1);
		return this.space.GetHarbors().get(harbor_id);
	}

	
	/**
	 * Method to get the unload time of this boat
	 */
	public int getUnloadTime() {
		return (int)this.size;
	}
	
	
	public void setSize(double size) {
		this.size = size;
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