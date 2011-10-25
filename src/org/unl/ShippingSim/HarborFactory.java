package org.unl.ShippingSim;

public class HarborFactory {

	// Initialize stuff
	public HarborFactory() {
		
		
	}
	
	
	public HarborAgent createHarbor(int x, int y, OceanSpace space) {
		HarborAgent harbor = new HarborAgent(x, y, space);
		
		// Do harbor initializaation
		
		
		return harbor;
	}
	
}
