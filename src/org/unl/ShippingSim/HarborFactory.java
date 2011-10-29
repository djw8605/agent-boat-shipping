package org.unl.ShippingSim;

import java.util.ArrayList;

import uchicago.src.sim.util.Random;

public class HarborFactory {

	protected int length, height;
	//arguments for each item
	protected int num_items = 4;
	protected double base_line[] = {10000,10000,20000,20000};
	protected double inventory[] = {10000,10000,10000,10000};
	protected double production_rate_best[] = {5,5,5,5};
	protected double production_rate_normal[] = {1,1,1,1};
	protected double consumption_rate_best[] = {2,2,2,2};
	protected double consumption_rate_normal[] = {2,2,2,2};
	
	protected double x[] = {0,0.5,0.5,1};
	protected double y[] = {0.5,0,1,0.5};
	
	// Initialize HarborFactory
	public HarborFactory(int length, int height) {
		this.length = length;
		this.height = height;
	}
	public ArrayList<HarborAgent> CreateHarbors(){
		ArrayList<HarborAgent> harbors = new ArrayList<HarborAgent>();
		for(int i=0; i < num_items; i++){
			ArrayList<SellableItem> item_list = CreateItemList(i);
			HarborAgent harbor = CreateHarbor((int)(length*x[i]),(int)(height*y[i]),item_list);
			harbors.add(harbor);
		}
		return harbors;
	}
	protected ArrayList<SellableItem> CreateItemList(int n)
	{
		ArrayList<SellableItem> items= new ArrayList<SellableItem>();
		for(int i = 0; i < num_items; i++){
			SellableItem item;
			if(i==n) item = new SellableItem(base_line[i], inventory[i], production_rate_best[i], consumption_rate_best[i]);
			else item = new SellableItem(base_line[i], inventory[i], production_rate_normal[i], consumption_rate_normal[i]);
			items.add(item);
		}
		return items;
	}
	protected HarborAgent CreateHarbor(int x, int y, ArrayList<SellableItem> items) {
		HarborAgent harbor = new HarborAgent(x, y, items);
		return harbor;
	}
	
//	public HarborAgent createHarbor(int x, int y, OceanSpace space) {
//		ArrayList<SellableItem> items = createItems(numItems);
//		HarborAgent harbor = new HarborAgent(x, y, space, items);
//		
//		// Do harbor initializaation
//		return harbor;
//	}
//	
//	
//	public ArrayList<SellableItem> createItems(int numItems) {
//		ArrayList<SellableItem> items = new ArrayList<SellableItem>();
//		for (int i = 0; i < numItems; i++) {
//			// Probably should do some distribution of item values and whatnot
//			double baseline = Random.uniform.nextDoubleFromTo(50.0, 100.0);
//			double initial_inv = Random.uniform.nextDoubleFromTo(100.0, 200.0);
//			SellableItem item = new SellableItem((float)baseline, (float)initial_inv, 0, 0);
//			items.add(item);
//			
//		}
//		return items;
//	}
	
}
