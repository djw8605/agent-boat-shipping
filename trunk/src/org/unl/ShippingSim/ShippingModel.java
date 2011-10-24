package org.unl.ShippingSim;

import uchicago.src.sim.engine.SimpleModel;

public class ShippingModel extends SimpleModel {

	protected int numBoats;
	
	public ShippingModel() {
		this.params = new String [] {"NumberBoats"};
		this.name = "Shipping Simulation";
		
	}
	
	public void setup() {
		super.setup();
		
		// Initialize the boat & Harbor factories
		
	}
	
	public void buildModel() {
		// Add new boats & harbors to the agentlist
		
	}

	
	public void step() {
		int size = agentList.size();
		for (int i = 0; i < size; i++) {
			//Player p = (Player) agentList.get(i);
			//p.play();
		}
	}
	
	
	/* Parameter functions */
	
	
	public void setNumberBoats(int val) {
		this.numBoats = val;
	}
	public int getNumberBoats() {
		return this.numBoats;
	}
	

}
