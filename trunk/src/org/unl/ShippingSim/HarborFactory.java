package org.unl.ShippingSim;

import java.util.ArrayList;

import uchicago.src.sim.util.Random;

public class HarborFactory {

	
	protected int numItems = 4;
	
	// Initialize stuff
	public HarborFactory() {
		
		
	}
	
	
	public HarborAgent createHarbor(int x, int y, OceanSpace space) {
		
		
		ArrayList<SellableItem> items = createItems(numItems);
		HarborAgent harbor = new HarborAgent(x, y, space, items);
		
		// Do harbor initializaation
		
		
		return harbor;
	}
	
	
	public ArrayList<SellableItem> createItems(int numItems) {
		ArrayList<SellableItem> items = new ArrayList<SellableItem>();
		for (int i = 0; i < numItems; i++) {
			// Probably should do some distribution of item values and whatnot
			double baseline = Random.uniform.nextDoubleFromTo(50.0, 100.0);
			double initial_inv = Random.uniform.nextDoubleFromTo(100.0, 200.0);
			SellableItem item = new SellableItem((float)baseline, (float)initial_inv);
			items.add(item);
			
		}
		return items;
	}
	
}
