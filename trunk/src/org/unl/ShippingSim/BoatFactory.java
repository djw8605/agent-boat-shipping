package org.unl.ShippingSim;

import uchicago.src.sim.util.Random;


public class BoatFactory {

	// Initialization
	public BoatFactory() {
		
		
		
	}
	
	public BoatAgent CreateBoat(int x, int y, OceanSpace space) {
		BoatAgent b = new BoatAgent(x, y, space);
		// Do the initialization stuff
		float boat_size = Random.uniform.nextFloatFromTo(100, 300);
		b.setSize(boat_size);
		
		return b;
		
	}
	
	
}
