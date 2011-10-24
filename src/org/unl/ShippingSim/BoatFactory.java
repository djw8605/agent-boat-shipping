package org.unl.ShippingSim;

import uchicago.src.sim.space.Object2DGrid;

public class BoatFactory {

	// Initialization
	public BoatFactory() {
		
		
		
	}
	
	public BoatAgent CreateBoat(int x, int y, OceanSpace space) {
		BoatAgent b = new BoatAgent(x, y, space);
		// Do the initialization stuff
		
		return b;
		
	}
	
	
}
