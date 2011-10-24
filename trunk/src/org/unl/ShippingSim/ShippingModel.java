package org.unl.ShippingSim;

import uchicago.src.sim.engine.SimpleModel;

public class ShippingModel extends SimpleModel {

	protected int numBoats;
	
	// Baseline values for items
	protected float baselineItem1;
	
	// Proportion of ships that are 'big'
	protected float shipSizeProportion;
	
	// Fuel price
	protected float fuelPrice;
	
	// Impact of long queues
	protected float queueImpact;
	
	// How far in the future the boats will look
	protected int farsight;
	
	
	
	public ShippingModel() {
		this.params = new String [] {"NumberBoats",
									 "BaselineItem1",
									 "ShipProportion",
									 "FuelPrice",
									 "QueueImpact",
									 "FarSight"};
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
	
	// Number of boats
	public void setNumberBoats(int val) {
		this.numBoats = val;
	}
	public int getNumberBoats() {
		return this.numBoats;
	}
	
	// Baseline values
	public void setBaselineItem1(float val) {
		this.baselineItem1 = val;
	}
	public float getBaselineItem1() {
		return this.baselineItem1;
	}
	
	// Proportion of ship sizes
	public void setShipProportion(float val) {
		this.shipSizeProportion = val;
	}
	public float getShipProportion() {
		return this.shipSizeProportion;
	}
	
	// Fuel prices
	public void setFuelPrice(float val) {
		this.fuelPrice = val;
	}
	public float getFuelPrice() {
		return this.fuelPrice;
	}
	
	// Impact of queues
	public void setQueueImpact(float val) {
		this.queueImpact = val;
	}
	public float getQueueImpact() {
		return this.queueImpact;
	}
	
	// How far in the future the boat will look (far sight)
	public void setFarSight(int val) {
		this.farsight = val;
	}
	public int getFarSight() {
		return this.farsight;
	}
	

}
