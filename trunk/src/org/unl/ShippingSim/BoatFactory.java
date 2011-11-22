package org.unl.ShippingSim;

import uchicago.src.sim.util.Random;


public class BoatFactory {

	protected float queueeffect = 0;
	
	// Initialization
	public BoatFactory() {
		
		
		
	}
	
	public BoatAgent CreateBoat(int x, int y, OceanSpace space, HarborAgent init_harbor) {
		BoatAgent b = new BoatAgent(x, y, space);
		// Do the initialization stuff
		float boat_size = Random.uniform.nextFloatFromTo(100, 300);
		b.setSize(boat_size);
		b.SetHarbor(init_harbor);
		b.setQueueEffect(this.queueeffect);
		return b;
		
	}
	
	public void SetQueueEffect(float queueeffect) {
		this.queueeffect = queueeffect;
	}
	
	
}
