package org.unl.ShippingSim;

import uchicago.src.sim.util.Random;


public class BoatFactory {

	protected float queueeffect = 0;
	
	public enum Distributions { NORMAL, UNIFORM, BINOMIAL, LOG, POISSON };
	
	public enum BoatSizes { SMALL, MEDIUM, LARGE };
	
	public static final double SMALL_BOAT = 50.0;
	public static final double MEDIUM_BOAT = 150.0;
	public static final double LARGE_BOAT = 300.0;
	
	protected Distributions uncertainty_distrubtion;
	
	protected int farsight = 1;
	
	// Initialization
	public BoatFactory() {
		
		
		
	}
	
	
	/**
	 * Create a boat
	 * @param x - int x location
	 * @param y - int y location
	 * @param space - Global space for a global view.  Only contains a limited view.
	 * @param init_harbor - Initial harbor
	 * @param boat_size - starting size of the boat.  Should be one of the constants defined above.
	 * @return
	 */
	public BoatAgent CreateBoat(int x, int y, OceanSpace space, HarborAgent init_harbor, double boat_size) {
		BoatAgent b = new BoatAgent(x, y, space);
		// Do the initialization stuff
		float calculated_boat_size = Random.uniform.nextFloatFromTo((float)boat_size - 20, (float)boat_size + 20);
		b.setSize(calculated_boat_size);
		b.SetHarbor(init_harbor);
		b.setQueueEffect(this.queueeffect);
		b.setFarsight(this.farsight);
		if (boat_size == SMALL_BOAT)
			b.setSizeEnum(BoatSizes.SMALL);
		else if (boat_size == MEDIUM_BOAT)
			b.setSizeEnum(BoatSizes.MEDIUM);
		else if (boat_size == LARGE_BOAT)
			b.setSizeEnum(BoatSizes.LARGE);
		return b;
		
	}
	
	/**
	 * Set the queue effect
	 * @param queueeffect
	 */
	public void SetQueueEffect(float queueeffect) {
		this.queueeffect = queueeffect;
	}
	
	/**
	 * Set the farsight of the boats
	 * @param farsight
	 */
	public void setFarSight(int farsight) {
		this.farsight = farsight;
	}
	
}
