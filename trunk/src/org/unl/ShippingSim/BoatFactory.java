package org.unl.ShippingSim;


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
