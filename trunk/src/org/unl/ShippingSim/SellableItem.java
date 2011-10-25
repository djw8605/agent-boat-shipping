package org.unl.ShippingSim;

public class SellableItem {

	// Selling stuff
	protected double sellValue = 0.0;
	protected double buyValue = 0.0;
	
	protected double inventory = 0.0;
	protected double baseline = 0.0;
	
	public SellableItem(float baseline, float starting_inventory) {
		this.baseline = baseline;
		this.inventory = starting_inventory;
	}
	
	
	
	
	
}
